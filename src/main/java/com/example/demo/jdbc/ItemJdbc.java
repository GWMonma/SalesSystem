package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.InventoryModel;

@Component
public class ItemJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//商品番号を利用して、商品の情報を取得
	public InventoryModel getItemData(int itemNo){
		InventoryModel returnData = new InventoryModel();
		try {
		String sql = "SELECT * FROM item WHERE item_no = ?";
		Map<String, Object> itemData = this.jdbcTemplate.queryForMap(sql, itemNo);
		//格納する
		returnData.setItemName((String)itemData.get("item_name"));
		returnData.setItemNo((int)itemData.get("item_no"));
		returnData.setItemPrice((int)itemData.get("item_price"));
		returnData.setItemProductNo((String)itemData.get("item_product_no"));
		returnData.setItemStock((int)itemData.get("item_stock"));
		}catch(Exception ex) {
			return null;
		}
		return returnData;
	}

	//商品の情報を取得
	public  ArrayList<InventoryModel> getItemDataList(){
		ArrayList<InventoryModel> returnList = new ArrayList<InventoryModel>();
		try {
		String sql = "SELECT * FROM item";
		List <Map<String, Object>> itemDataList = this.jdbcTemplate.queryForList(sql);
		//格納する
		for(Map<String, Object> itemData : itemDataList) {
			InventoryModel returnData = new InventoryModel();
			returnData.setItemName((String)itemData.get("item_name"));
			returnData.setItemNo((int)itemData.get("item_no"));
			returnData.setItemPrice((int)itemData.get("item_price"));
			returnData.setItemProductNo((String)itemData.get("item_product_no"));
			returnData.setItemStock((int)itemData.get("item_stock"));
			returnList.add(returnData);
		}
		}catch(Exception ex) {
			return null;
		}
		return returnList;
	}

	//任意の商品の在庫履歴を取得
		public ArrayList<InventoryModel> getInventoryLog(String searchWord){
			ArrayList<InventoryModel> returnList = new ArrayList<InventoryModel>();
			try {
				String sql = "SELECT * FROM item WHERE item_name LIKE ?";
				List<Map<String, Object>> itemDataList = jdbcTemplate.queryForList(sql, '%'+searchWord+'%');
				//格納する
				for(Map<String, Object> mapData : itemDataList) {
					InventoryModel returnData = new InventoryModel();
					returnData.setItemNo((int)mapData.get("item_no"));
					returnData.setItemName((String)mapData.get("item_name"));
					returnData.setItemPrice((int)mapData.get("item_price"));
					returnData.setItemStock((int)mapData.get("item_stock"));


					returnList.add(returnData);
				}
			}catch(Exception ex) {

			}
			return returnList;
		}

}