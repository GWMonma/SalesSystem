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
class InventoryManagementControllerTest {

	/*Mock*/
	private MockMvc mockMvc;

	 @Autowired
	 InventoryManagementController InventoryManagementController;

	 @BeforeEach
	  public void setup() {
		 mockMvc = MockMvcBuilders.standaloneSetup(InventoryManagementController).build();
	  }

	/*在庫検索結果機能*/
	@Test
	void testInventoryadjustmentsearch()  throws Exception {
		 String postname = "/InventrySearch";
		 String name = "html/InventoryAdjustment";

		 /*ユーザーIDとパスワード*/
		 String searchWord =  "ソファ";

		mockMvc.perform(post(postname)
				.param("item_name", searchWord))
		 		.andExpect(view().name(name));
	}

	/*在庫更新機能のテスト*/
	@Test
	void testInventoryadjustmentupdate()  throws Exception {
		 String postname = "/InventryUpdate";
		 String name = "html/InventoryAdjustment";

		 /*商品番号と個数*/
		 String searchWord =  "1";
		 String nuｍ ="100";

		mockMvc.perform(post(postname)
				.param("item_no",searchWord)
				.queryParam("item_stock", nuｍ))
		 		.andExpect(view().name(name));
	}

}
