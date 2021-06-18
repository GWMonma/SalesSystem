package com.example.demo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuotationJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//見積情報を保存
	public String quotationSave(int userNo,int itemBuyCount, int itemNo) {
		try {
			this.jdbcTemplate.update("INSERT INTO quotation"
				+ "(user_no, item_buy_count, item_no)"
				+"VALUES(?, ?, ?);",
				userNo, itemBuyCount, itemNo);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "見積情報の保存が完了しました。";
	}
	
}
