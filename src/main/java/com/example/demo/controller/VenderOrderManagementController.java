package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.logic.VenderOrderLogic;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.VenderOrderModel;

@Controller
public class VenderOrderManagementController {
	@Autowired
	private VenderOrderLogic venderOrderLogic;
	
	@Autowired
	private ItemJdbc itemJdbc;
	
	//ページを表示
	@RequestMapping("VenderOrderInput")
	public String venderOrderInputPage(Model model) {
		ArrayList<InventoryModel> list = itemJdbc.getItemDataList();
		model.addAttribute("list", list);
		return "html/VenderOrderInput";
	}

	//計算を行い、合計金額を表示する
	@RequestMapping("VenderOrderCheck")
	public String orderCheck(@RequestParam("itemNo") String itemNo, @RequestParam("itemBuyCount") String itemBuyCount, Model model) {
		int no = -1;
		int buyCount = -1;
		ArrayList<InventoryModel> list = itemJdbc.getItemDataList();
		model.addAttribute("list", list);
		try {
			no = Integer.parseInt(itemNo);
			buyCount = Integer.parseInt(itemBuyCount);
		}catch(Exception ex){
			model.addAttribute("resultText","数値を入力してください。");
	        return "html/VenderOrderInput";
		}
		
		int totalItemNo = itemJdbc.getItemDataList().size();
    	if(totalItemNo<no) {
    		model.addAttribute("resultText", "入力された番号は存在しません。");
    		return "html/VenderOrderInput";
    	}
		
		int totalPrice = venderOrderLogic.getVenderOrderTotalPrice(no, buyCount);
		model.addAttribute("list", list);
		if(totalPrice==-1) {
			model.addAttribute("resultText", "エラーが発生しました。");
		}else{
			int itemPrice = itemJdbc.getItemData(no).getItemPrice();
			model.addAttribute("itemNo", itemNo);
			model.addAttribute("itemBuyCount", itemBuyCount);
			model.addAttribute("totalPrice", itemPrice * buyCount);
			model.addAttribute("resultText", "発注内容をご確認ください。");
		}
		return "html/VenderOrderInput";
	}
	
	//発注情報を保存する
	@RequestMapping("VenderOrderSave")
	public String venderOrderSave(@RequestParam("itemNo") int itemNo, @RequestParam("itemBuyCount") int itemBuyCount, @RequestParam("totalPrice") int totalPrice,Model model) {
		
		//保存するためのLogic
		String resultText = venderOrderLogic.venderOrderSaveLogic(itemNo, itemBuyCount, totalPrice);
		int checkItemTotalPrice = -10;
		ArrayList<InventoryModel> list = itemJdbc.getItemDataList();
		model.addAttribute("list", list);
		//金額が変更された場合
		if(resultText.equals("金額が変更されました。申し訳ございませんが、もう一度ご確認ください。")) {
			checkItemTotalPrice = venderOrderLogic.getVenderOrderTotalPrice(itemNo, itemBuyCount);
		}
		//金額変更の際にエラーが発生した場合
		if(checkItemTotalPrice==-1) {
			resultText = "エラーが発生しました。";
		}else if(checkItemTotalPrice>0){
			model.addAttribute("itemNo", itemNo);
			model.addAttribute("itemBuyCount", itemBuyCount);
			model.addAttribute("totalPrice", checkItemTotalPrice);
		}
		//resultTextを渡して画面遷移
		model.addAttribute("resultText", resultText);
		return "html/VenderOrderInput";
	}
	
	
	//ページを表示
	@RequestMapping("VenderOrderSearch")
	public String venderOrderSearchPage(Model model) {
		return "html/VenderOrderSearch";
	}
	
	@RequestMapping("VenderOrderSearchResult")
	public String venderOrderSearch(@RequestParam("searchWord") String searchWord, Model model) {
		ArrayList<VenderOrderModel> returnList = venderOrderLogic.getVenderOrderLog(searchWord);
		//検索結果を渡す
		if(returnList.size()==0){
			model.addAttribute("searchList", null);
		}else{
			model.addAttribute("searchList", returnList);
		}	
		model.addAttribute("resultText", "検索結果"+returnList.size()+"件");
		return "html/VenderOrderSearch";
	}
	
}


