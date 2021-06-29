package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.LoginModel;

/*販売管理関係のコントローラー*/
@Controller
public class  MarketingManagementController {
	String message;
	 @Autowired
	 HttpSession session;

	  /*販売管理画面へ遷移*/
	 @RequestMapping("MarketingManagement")
	 public String marketingmanagement(Model model) {
		 if(session.getAttribute("data") == null) {
				message ="IDとパスワードを入力してください";
			    model.addAttribute("indexForm", new LoginModel());
			    model.addAttribute("message" , message);
			    return "html/Login";
			}

	     return "html/MarketingManagement";
	 }


	 /*売上集計画面へ遷移*/
	 @RequestMapping("SalesTotal")
	 public String SalesTotal(Model model) {
		 if(session.getAttribute("data") == null) {
				message ="IDとパスワードを入力してください";
			    model.addAttribute("indexForm", new LoginModel());
			    model.addAttribute("message" , message);
			    return "html/Login";
			}

	     return "html/SalesTotal";
	 }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}