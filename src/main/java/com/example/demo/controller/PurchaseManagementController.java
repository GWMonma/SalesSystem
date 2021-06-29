package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.LoginModel;

/*購買管理関係のコントローラー*/
@Controller
public class  PurchaseManagementController  {
	String message;
	 @Autowired
	 HttpSession session;

	 /*購買管理画面へ遷移*/
	    @RequestMapping("PurchaseManagement")
	    public String purchase(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "html/Login";
	    	}

	        return "html/PurchaseManagement";
	    }

	    /*発注管理画面へ遷移*/
	    @RequestMapping("VenderOrderManagement")
	    public String venderorder(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "html/Login";
	    	}

	        return "html/VenderOrderManagement";
	    }

	    /*仕入れ管理画面へ遷移*/
	    @RequestMapping("VenderStockManagement")
	    public String venderstock(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "html/Login";
	    	}

	        return "html/VenderStockManagement";
	    }



    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}

