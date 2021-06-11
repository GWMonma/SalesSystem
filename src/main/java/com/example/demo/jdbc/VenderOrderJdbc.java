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
	
	
//仕入管理　仕入れ情報検索
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
	
	
//仕入れ管理　仕入日予定日の更新
	public String arrivalDueDateUpdate(int vender_order_no) {
		try {
			this.jdbcTemplate.update("update venderorder set arrival_due_date=current_timestamp where vender_order_no=?",vender_order_no);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "更新が完了しました";
	}
	
	
}
