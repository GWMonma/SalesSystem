package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*見積管理関係のコントローラー*/
@Controller
public class QuotationManagementController  {
	String message;
	 @Autowired
	 HttpSession session;

	 /*見積管理画面へ遷移*/
	    @RequestMapping("QuotationManagement")
	    public String quotation(Model model) {

	        return "QuotationManagement";
	    }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}


