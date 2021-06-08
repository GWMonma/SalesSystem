package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

/*発注管理関係のコントローラー*/
@Controller
public class  VenderOrderManagementController  {
	String message;
	 @Autowired
	 HttpSession session;



    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;


}