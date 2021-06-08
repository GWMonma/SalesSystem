package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*販売管理関係のコントローラー*/
@Controller
public class  MarketingManagementController {
	String message;
	 @Autowired
	 HttpSession session;

	  /*販売管理画面へ遷移*/
	 @RequestMapping("MarketingManagement")
	 public String marketingmanagement(Model model) {

	     return "MarketingManagement";
	 }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}