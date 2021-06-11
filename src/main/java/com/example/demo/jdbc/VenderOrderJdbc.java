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
	
	
//仕入管理　検索機能
		public List<VenderOrderModel> getVenderOrder(String item_name) {
	
	String sql = "SELECT * from venderorder where item_name like ?";
	List<Map<String, Object>>venderOrderList = jdbcTemplate.queryForList(sql,'%'+item_name+'%');
	List<VenderOrderModel> list = new ArrayList<>();
	for(Map<String,Object> str1 : venderOrderList) {
		VenderOrderModel vom = new VenderOrderModel();
		vom.setVender_order_no((int)str1.get("vender_order_no"));
		vom.setItem_name((String)str1.get("item_name"));
		vom.setItem_product_no((String)str1.get("item_product_no"));
		vom.setItem_buy_count((int)str1.get("item_buy_count"));
		vom.setTotal_price((int)str1.get("total_price"));
		vom.setArrival_due_date((Date)str1.get("arrival_due_date"));
		list.add(vom);
		}
	return list;
	}
	
}
