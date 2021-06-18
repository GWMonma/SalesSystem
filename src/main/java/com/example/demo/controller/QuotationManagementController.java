package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.logic.ItemLogic;
import com.example.demo.logic.QuotationLogic;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.LoginModel;

/*見積管理関係のコントローラー*/
@Controller
public class QuotationManagementController  {
	String message;
	 @Autowired
	 HttpSession session;
	 
	 @Autowired
	 JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	 ItemJdbc itemJdbc;
	 
	 @Autowired
	 ItemLogic itemLogic;
	 
	 @Autowired
	 QuotationLogic quotationLogic;

	 /*見積管理画面へ遷移*/
	    @RequestMapping("QuotationManagement")
	    public String quotation(Model model) {
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}

	        return "QuotationManagement";
	    }
	
	 //見積入力画面
	 @RequestMapping("QuotationInput")
	 public String quotationInput(Model model) {
 		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
		 ArrayList<InventoryModel> itemList = itemJdbc.getItemDataList();;
		 model.addAttribute("list", itemList);
		 return "html/QuotationInput";
	 }
	 
	 //入力情報を確認し、合計金額を表示
	 @RequestMapping("Quotationcheck")
	 public String quotationcheck(@RequestParam("itemNo") String itemNo, @RequestParam("itemBuyCount") String itemBuyCount, Model model) {
 		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
		 int no = -1;
		 int buyCount = -1;
		 //入力可能な数値であるか確認
		 ArrayList<InventoryModel> itemList = itemJdbc.getItemDataList();;
		 model.addAttribute("list", itemList);
		 String returnText  = itemLogic.inputConfirmation(itemNo, itemBuyCount);
		 if(returnText.equals("true")) {
			 no = Integer.parseInt(itemNo);
			 buyCount = Integer.parseInt(itemBuyCount);
		 }else {
			 model.addAttribute("resultText", returnText);
			 return "html/QuotationInput";
		 }
		 
		int totalItemNo =itemJdbc.getItemDataList().size();
		if(totalItemNo<no) {
			model.addAttribute("resultText", "入力された番号は存在しません。");
			return "html/QuotationInput";
		}
		
		int itemStock = itemJdbc.getItemData(no).getItemStock();
		if(itemStock<buyCount) {
			model.addAttribute("resultText", "購入希望数が在庫数を超えています。");
			return "html/QuotationInput";
		}
		
		int totalPrice = quotationLogic.getQuotationTotalPrice(no, buyCount);
		if(totalPrice==-1) {
			model.addAttribute("resultText", "エラーが発生しました。");
		}else {
			int itemPrice = itemJdbc.getItemData(no).getItemPrice();
			model.addAttribute("itemNo", itemNo);
			model.addAttribute("itemBuyCount", itemBuyCount);
			model.addAttribute("totalPrice", itemPrice);
			model.addAttribute("resultText", "見積内容をご確認ください。");
		}
		 
		 return "html/QuotationInput";
	 }
	 
	 //入力情報を確認し、合計金額を表示
	 @RequestMapping("quotationSave")
	 public String quotationSave(@RequestParam("itemNo") String itemNo, @RequestParam("itemBuyCount") String itemBuyCount, Model model) {
 		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
		 int no = Integer.parseInt(itemNo);
		 int buyCount = Integer.parseInt(itemBuyCount);
		 String resultText = quotationLogic.quotationSaveLogic(no, buyCount);
		 model.addAttribute("resultText", resultText);
		 return "html/QuotationInput";
	 }

}


