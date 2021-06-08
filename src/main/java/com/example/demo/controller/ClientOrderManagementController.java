package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*受注管理関係のコントローラー*/
@Controller
public class ClientOrderManagementController {
	String message;
	 @Autowired
	 HttpSession session;

 /*受注管理画面へ遷移*/
    @RequestMapping("ClientOrderManagement")
    public String clientorder(Model model) {

        return "ClientOrderManagement";
    }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}
