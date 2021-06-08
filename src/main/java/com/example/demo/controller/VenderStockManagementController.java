package com.example.demo.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

/*仕入れ管理関係のコントローラー*/
@Controller
public class  VenderStockManagementController  {
	String message;
	 @Autowired
	 HttpSession session;



    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}