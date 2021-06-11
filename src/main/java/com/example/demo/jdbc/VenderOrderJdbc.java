package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.InventoryModel;
import com.example.demo.model.VenderOrderModel;

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
	
	
	//発注履歴を取得
	public ArrayList<VenderOrderModel> getVenderOrderLog(String searchWord){
		ArrayList<VenderOrderModel> returnList = new ArrayList<VenderOrderModel>();
		try {
			String sql = "SELECT * FROM venderorder WHERE item_name LIKE ?";
			List<Map<String, Object>> itemDataList = jdbcTemplate.queryForList(sql, '%'+searchWord+'%');
			//格納する
			for(Map<String, Object> mapData : itemDataList) {
				VenderOrderModel returnData = new VenderOrderModel();
				returnData.setVender_order_no((int)mapData.get("vender_order_no"));
				returnData.setItem_name((String)mapData.get("item_name"));
				returnData.setItem_product_no((String)mapData.get("item_product_no"));
				returnData.setItem_buy_count((int)mapData.get("item_buy_count"));
				returnData.setTotal_price((int)mapData.get("total_price"));
				returnData.setItem_buy_date((Date)mapData.get("item_buy_date"));
				returnData.setArrival_due_date((Date)mapData.get("arrival_due_date"));
				returnList.add(returnData);
			}
		}catch(Exception ex) {
		
		}
		return returnList;
	}
	
	
	//仕入れ管理　仕入日予定日の更新
	public String arrivalDueDateUpdate(int vender_order_no) {
		try {
			this.jdbcTemplate.update("update venderorder set arrival_due_date=current_timestamp where vender_order_no=?",vender_order_no);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "更新が完了しました。";
	}

}
