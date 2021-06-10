package com.example.demo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.InventoryModel;

@Component
public class VenderOrderJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//発注情報を保存
	public String venderOrderSave(InventoryModel itemData,int itemBuyCount, int totalPrice, String itemBuyDate) {
		try {
			this.jdbcTemplate.update("INSERT INTO venderorder"
				+ "(item_name, item_product_no, item_buy_count, total_price, item_buy_date)"
				+"VALUES(?, ?, ?, ?, ?);",
				itemData.getItemName(), itemData.getItemProductNo(), itemBuyCount, totalPrice, itemBuyDate);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "発注が完了しました。";
	}
	
}
