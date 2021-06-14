package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.ItemLogic;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.LoginModel;

/*在庫管理関係のコントローラー*/
@Controller
public class InventoryManagementController {
	String message;
	 @Autowired
	 HttpSession session;

	 /*入荷管理画面へ遷移*/
	    @RequestMapping("ArrivalManagement")
	    public String arrival(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "Login";
	    	}

	        return "html/ArrivalManagement";
	    }

	    /*出荷管理画面へ遷移*/
	    @RequestMapping("ShipmentManagement")
	    public String shipment(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "Login";
	    	}

	        return "html/ShipmentManagement";
	    }

	    /*在庫管理画面へ遷移*/
	    @RequestMapping("InventoryManagement")
	    public String inventory(Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
		    model.addAttribute("indexForm", new LoginModel());
		    model.addAttribute("message" , message);
		    return "Login";
		}
	        return "html/InventoryManagement";
	    }

	    /*棚卸・在庫調整画面へ遷移*/
	    @RequestMapping("InventoryAdjustment")
	    public String  	inventoryadjustment(Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
		    model.addAttribute("indexForm", new LoginModel());
		    model.addAttribute("message" , message);
		    return "Login";
		}
	        return "html/InventoryAdjustment";
	    }

	    @Autowired
		private ItemLogic ItemLogic;

	    @RequestMapping("InventrySearch")
		public String inventoryadjustmentsearch(@RequestParam("item_name") String searchWord, Model model) {
			ArrayList<InventoryModel> returnList = ItemLogic.getInventoryLog(searchWord);
			//検索結果を渡す
			if(returnList.size()==0){
				model.addAttribute("searchList", null);
			}else{
				model.addAttribute("searchList", returnList);
			}
			model.addAttribute("resultText", "検索結果"+returnList.size()+"件");
			return "html/InventoryAdjustment";
		}


}