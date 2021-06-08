package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*購買管理関係のコントローラー*/
@Controller
public class  PurchaseManagementController  {
	String message;
	 @Autowired
	 HttpSession session;

	 /*購買管理画面へ遷移*/
	    @RequestMapping("PurchaseManagement")
	    public String purchase(Model model) {
	    	System.out.println( session.getAttribute("data"));
	        return "PurchaseManagement";
	    }

	    /*発注管理画面へ遷移*/
	    @RequestMapping("VenderOrderManagement")
	    public String venderorder(Model model) {
	    	System.out.println( session.getAttribute("data"));
	        return "VenderOrderManagement";
	    }

	    /*仕入れ管理画面へ遷移*/
	    @RequestMapping("VenderStockManagement")
	    public String venderstock(Model model) {
	    	System.out.println( session.getAttribute("data"));
	        return "VenderStockManagement";
	    }



    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}

