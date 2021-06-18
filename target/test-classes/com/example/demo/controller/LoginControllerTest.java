package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class LoginControllerTest {
	/*Mock*/
	private MockMvc mockMvc;

	 @Autowired
	 LoginController LoginController;

	 @BeforeEach
	  public void setup() {
		 mockMvc = MockMvcBuilders.standaloneSetup(LoginController).build();
	  }

	 @Test
	 public void testIndex2() throws Exception {
		 String getname = "/Login";
		 String name = "html/Login";

		mockMvc.perform(get(getname))
		   .andExpect(status().isOk())
		   .andExpect(view().name(name));
	 }

	/*ログイン機能のテスト*/
	 @Test
	 public void TestpostResult() throws Exception {
		 String postname = "/ManagerMenu";
		 String name = "html/managermenu";

		 /*ユーザーIDとパスワード*/
		 String username =  "nishiyama";
		 String pass ="1234";

		mockMvc.perform(post(postname)
				.param("username",username)
				.param("password", pass))
		 		.andExpect(view().name(name));
	 }

	 /*ログイン機能のテスト*/
	 @Test
	 public void Testload() throws Exception {
		 String postname = "/logout";
		 String name = "html/Login";



		mockMvc.perform(post(postname))
		 		.andExpect(view().name(name));
	 }




}
