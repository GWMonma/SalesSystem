package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.ClientOrderLogic;
import com.example.demo.model.ClientOrderModel;
import com.example.demo.model.LoginModel;

/*売上関係のコントローラー*/
@Controller
public class SalesManagementController  {
	String message;
	 @Autowired
	 HttpSession session;

	 @Autowired
	 ClientOrderLogic  Clientlogic;

	 /*売上集計画面へ遷移*/
	    @RequestMapping("SalesManagement")
	    public String sales(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "Login";
	    	}

	        return "html/SalesManagement";
	    }

	    /*売上の検索*/
	    @RequestMapping("SalesSearch")
	    public String salesSearch(@RequestParam("item_name") String SearchWord,Model model) {
	    	ArrayList<ClientOrderModel> returnList = Clientlogic.getSalesSearch(SearchWord);
	    	System.out.println(returnList);

	    	//検索結果を渡す
			if(returnList.size()==0){
				model.addAttribute("searchList", null);
			}else{
				model.addAttribute("searchList", returnList);
			}
			model.addAttribute("resultText", "検索結果："+returnList.size()+"件");
			model.addAttribute("searchWord", SearchWord);
	        return "html/SalesTotal";
	    }
}


