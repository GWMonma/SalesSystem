package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ClientOrderJdbc;
import com.example.demo.model.ClientOrderModel;
import com.example.demo.model.LoginModel;

/*受注管理関係のコントローラー*/
@Controller
public class ClientOrderManagementController {
	String message;
	 @Autowired
	 HttpSession session;
	 


 /*受注管理画面へ遷移*/
    @RequestMapping("ClientOrderManagement")
    public String clientOrderManagement(Model model) {
    	if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "/html/Login";
		}

        return "html/ClientOrderManagement";
    }
    
    /*受注入力画面へ遷移*/
    @RequestMapping("ClientOrderInput")
    public String clientOrderInput(Model model) {
    	//セッションからuser_noを取得
    	Map<String, Object> sessionlist = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) sessionlist.get("user_no");
    	
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "/html/Login";
    	}
    	
    	ArrayList<ClientOrderModel> list = clientOrderJdbc.getQuotationDataList(userNo);
		if(list.size()>0) {
			model.addAttribute("QuotationList",list);
    	}
		model.addAttribute("resultText", "見積件数："+list.size()+"件");
        return "html/ClientOrderInput";
    }
    
    
    /*受注検索画面へ遷移*/
    @RequestMapping("ClientOrderSearch")
    public String clientOrderSearch(Model model) {
    	if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "/html/Login";
		}

        return "html/ClientOrderSearch";
    }
    
    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ClientOrderJdbc clientOrderJdbc;
	
	
	//商品名をデータベースと照合して受注情報を表示(フォーム)
		@RequestMapping("COSearch")
		public String clientOrderSearch(@RequestParam("searchWord") String searchWord, Model model){
			ArrayList<ClientOrderModel> list = clientOrderJdbc.getClientOrderLog(searchWord);
			if(list.size()>0) {
				model.addAttribute("clientOrderList",list);
	    	}
			model.addAttribute("item_name", searchWord);
			model.addAttribute("resultText", "検索結果："+list.size()+"件");
		  	return "html/ClientOrderSearch";
		}
		

}
