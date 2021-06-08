package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*在庫管理関係のコントローラー*/
@Controller
public class InventoryManagementController {
	String message;
	 @Autowired
	 HttpSession session;

 /*在庫管理画面へ遷移*/
    @RequestMapping("InventoryManagement")
    public String inventory(Model model) {

        return "InventoryManagement";
    }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}