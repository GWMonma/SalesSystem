package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.ClientOrderModel;
@Component
public class ClientOrderJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//仕入れ管理　仕入日予定日の更新
	public String shipmentDateUpdate(int client_order_no,String shipment_due_date) {
		try {
			this.jdbcTemplate.update("update clientorder set shipment_due_date= ? where client_order_no= ?",shipment_due_date,client_order_no);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "更新が完了しました。";
	}
	
	
	//受注履歴を取得
		public ArrayList<ClientOrderModel> getClientOrderLog(String searchWord){
			ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
			try {
				String sql = "SELECT * FROM clientorder WHERE item_name LIKE ?";
				List<Map<String, Object>> itemDataList = jdbcTemplate.queryForList(sql, '%'+searchWord+'%');
				//格納する
				for(Map<String, Object> mapData : itemDataList) {
					ClientOrderModel returnData = new ClientOrderModel();
					returnData.setClient_order_no((int)mapData.get("client_order_no"));
					returnData.setItem_name((String)mapData.get("item_name"));
					returnData.setItem_product_no((String)mapData.get("item_product_no"));
					returnData.setItem_buy_count((int)mapData.get("item_buy_count"));
					returnData.setTotal_price((int)mapData.get("total_price"));
					returnData.setItem_buy_date((Date)mapData.get("item_buy_date"));
					returnData.setShipment_due_date((Date)mapData.get("shipment_due_date"));
					returnList.add(returnData);
				}
			}catch(Exception ex) {
			
			}
			return returnList;
		}
		
		public ArrayList<ClientOrderModel> getClientOrderLog2(String entered,String unentered,String shipped){
			ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
			try {
				List<Map<String, Object>> itemDataList;
				if(entered!=null) {
					String sql = "select * from clientorder where shipment_due_date is null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}else if(unentered!=null) {
					String sql = "select * from clientorder where shipment_due_date is not null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}else {
					String sql = "select * from clientorder where shipment_due_date is not null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}
				
				//格納する
				for(Map<String, Object> mapData : itemDataList) {
					ClientOrderModel returnData = new ClientOrderModel();
					returnData.setClient_order_no((int)mapData.get("client_order_no"));
					returnData.setItem_name((String)mapData.get("item_name"));
					returnData.setItem_product_no((String)mapData.get("item_product_no"));
					returnData.setItem_buy_count((int)mapData.get("item_buy_count"));
					returnData.setTotal_price((int)mapData.get("total_price"));
					returnData.setItem_buy_date((Date)mapData.get("item_buy_date"));
					returnData.setShipment_due_date((Date)mapData.get("shipment_due_date"));
					returnList.add(returnData);
				}
			}catch(Exception ex) {
			
			}
			return returnList;
		}

}
