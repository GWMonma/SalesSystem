package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	//出荷予定日の更新
	public String shipmentDueDateUpdateJdbc(int client_order_no,String shipment_due_date) {
		try {
			this.jdbcTemplate.update("update clientorder set shipment_due_date= ? where client_order_no= ?",shipment_due_date,client_order_no);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "出荷予定日の更新が完了しました。";
	}

	//出荷日の更新
		public String shipmentDateUpdateJdbc(int client_order_no) {
			try {
				this.jdbcTemplate.update("update clientorder set shipment_date = current_timestamp where client_order_no= ?",client_order_no);
			}catch(Exception ex) {
				return "エラーが発生しました。";
			}
			return "出荷が完了しました。";
		}


	//商品名から受注履歴を取得
		public ArrayList<ClientOrderModel> getClientOrderLog(String searchWord){
			ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
			try {
				List<Map<String, Object>> itemDataList;
				if(searchWord.equals("出荷予定日未入力の商品")) {
					//出荷予定日未記入の情報を取得
					String sql = "select * from clientorder where shipment_due_date is null && shipment_date is null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}else if(searchWord.equals("出荷前の商品")) {
					//出荷予定日記入済みかつ未出荷の情報を取得
					String sql = " select * from clientorder where shipment_due_date is not null && shipment_date is null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}else if(searchWord.equals("出荷済みの商品")){
					//出荷日記入済み(出荷済み)の情報を取得
					String sql = "select * from clientorder where shipment_date is not null";
					itemDataList = jdbcTemplate.queryForList(sql);
				}else {
					String sql = "SELECT * FROM clientorder WHERE item_name LIKE ?";
					itemDataList = jdbcTemplate.queryForList(sql, '%'+searchWord+'%');
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
					returnData.setShipment_date((Date)mapData.get("shipment_date"));
					returnList.add(returnData);
				}
			}catch(Exception ex) {

			}
			return returnList;
		}


		//受注情報を全件取得
		public  ArrayList<ClientOrderModel> getClientOrderDataList(){
			ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
			try {
			String sql = "SELECT * FROM clientorder";
			List <Map<String, Object>> clientOrderDataList = this.jdbcTemplate.queryForList(sql);
			//格納する
			for(Map<String, Object> clientOrderData : clientOrderDataList) {
				ClientOrderModel returnData = new ClientOrderModel();
				returnData.setClient_order_no((int)clientOrderData.get("client_order_no"));
				returnData.setItem_name((String)clientOrderData.get("item_name"));
				returnData.setItem_product_no((String)clientOrderData.get("item_product_no"));
				returnData.setItem_buy_count((int)clientOrderData.get("item_buy_count"));
				returnData.setTotal_price((int)clientOrderData.get("total_price"));
				returnData.setItem_buy_date((Date)clientOrderData.get("item_buy_date"));
				returnData.setShipment_due_date((Date)clientOrderData.get("shipment_due_date"));
				returnData.setShipment_date((Date)clientOrderData.get("shipment_date"));
				returnList.add(returnData);
			}
			}catch(Exception ex) {
				return null;
			}
			return returnList;
		}

		//出荷確定済みか確認する処理
		public String CheckShipmentDue(int client_order_no) {
			String returnText = null;
			try {
				String sql = "SELECT shipment_date FROM clientorder WHERE client_order_no = ?";
				Map<String, Object> data = this.jdbcTemplate.queryForMap(sql, client_order_no);
				if(data.get("shipment_date")==null) {
					returnText = null;
				}else{
					returnText = "出荷済み";
				}

			}catch(Exception ex) {
				return "エラーが発生しました。";
			}
			return returnText;
		}


		//見積情報を全件取得
			public  ArrayList<ClientOrderModel> getQuotationDataList(int userNo){
				ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
				try {
				String sql = "select * from quotation join item on quotation.item_no = item.item_no where user_no=?";
				List <Map<String, Object>> quotationDataList = this.jdbcTemplate.queryForList(sql,userNo);
			//格納する
				for(Map<String, Object> quotationData : quotationDataList) {
					ClientOrderModel returnData = new ClientOrderModel();
					returnData.setQuotation_no((int)quotationData.get("quotation_no"));
					returnData.setItem_name((String)quotationData.get("item_name"));
					returnData.setItem_buy_count((int)quotationData.get("item_buy_count"));
					returnData.setItem_price((int)quotationData.get("item_price"));
					returnList.add(returnData);
				}
				}catch(Exception ex) {
					return null;
				}
				return returnList;
			}

	//受注確定
		public String clientOrderFixingJdbc(int userNo,ArrayList<ClientOrderModel> list) {
			try {
				for(ClientOrderModel li:list) {
					//item_product_no取得
					Map<String, Object> map = jdbcTemplate.queryForMap(" select item_product_no from item where item_name=?", li.getItem_name());
					String item_product_no = (String) map.get("item_product_no");
					System.out.println("item_product_no = "+item_product_no);
					//total_price取得
					map = jdbcTemplate.queryForMap("select * from quotation join item on quotation.item_no=item.item_no where user_no=? && quotation_no=?",userNo,li.getQuotation_no());
					int item_price = (int) map.get("item_price");
					int item_buy_count = (int) map.get("item_buy_count");
					int total_price =item_price * item_buy_count;
					System.out.println("total_price = "+total_price);

					//見積データを受注データに変更し追加
					this.jdbcTemplate.update("insert into clientorder(user_no,item_name,item_product_no,item_buy_count,total_price,item_buy_date,completed_delivery) "
											+ "values(?,?,?,?,?,current_timestamp,0)",userNo,li.getItem_name(),item_product_no,li.getItem_buy_count(),total_price);

				}
			}catch(Exception ex) {
				return "エラーが発生しました。";
			}
			return "受注が完了しました。";
		}


	//見積情報の削除
		public String quotationDataDelete(int userNo) {
			try {
				//見積データを削除
				this.jdbcTemplate.update("delete from quotation where user_no=?",userNo);
				//オートインクリメントのリセット
				this.jdbcTemplate.update("alter table quotation auto_increment = 1;");
			}catch(Exception ex) {
				return "エラーが発生しました。";
			}
			return "受注が完了しました。";
		}


			//売上履歴を取得
			public ArrayList<ClientOrderModel> getSalesSearch(String SearchWord){
				ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
				try {
					String sql;
					List<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();

						sql = "SELECT * FROM clientorder WHERE item_name LIKE ?";
						itemDataList = jdbcTemplate.queryForList(sql, '%'+SearchWord+'%');
					//格納する
					for(Map<String, Object> mapData : itemDataList) {
						ClientOrderModel returnData = new ClientOrderModel();
						System.out.println(mapData);
						returnData.setClient_order_no((int)mapData.get("client_order_no"));
						returnData.setItem_name((String)mapData.get("item_name"));
						returnData.setItem_buy_count((int)mapData.get("item_buy_count"));
						returnData.setTotal_price((int)mapData.get("total_price"));
						returnData.setItem_buy_date((Date)mapData.get("item_buy_date"));
						returnList.add(returnData);
						System.out.println(returnData);
					}
				}catch(Exception ex) {

				}
				return returnList;
			}

	//新しいの
			//売上出力
			public ArrayList<ClientOrderModel> getSalesOutput(String SearchWord){
				ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
				try {
					String sql;
					List<Map<String, Object>> itemDataList = new ArrayList<Map<String, Object>>();

					sql = "SELECT *   FROM  clientorder WHERE item_buy_date LIKE ?;";
					itemDataList = jdbcTemplate.queryForList(sql, '%'+SearchWord+'%');
					//格納する
					for(Map<String, Object> mapData : itemDataList) {
						ClientOrderModel returnData = new ClientOrderModel();
						returnData.setClient_order_no((int)mapData.get("client_order_no"));
						returnData.setItem_name((String)mapData.get("item_name"));
						returnData.setItem_buy_count((int)mapData.get("item_buy_count"));
						returnData.setTotal_price((int)mapData.get("total_price"));
						returnData.setItem_buy_date((Date)mapData.get("item_buy_date"));
						returnList.add(returnData);
					}
				}catch(Exception ex) {

				}
				return returnList;
			}

			//売上合計
			public Map<String, Object>  getSalesTotalPrice(String SearchWord){
				Map<String, Object> returnList = new HashMap<String, Object>();

				try {
					String sql;
					Map<String, Object> itemDataList = new HashMap<String, Object>();
						sql = "SELECT  SUM(total_price) AS month_total  FROM  clientorder WHERE item_buy_date LIKE ? GROUP BY DATE_FORMAT(item_buy_date, '%m');";
						itemDataList = jdbcTemplate.queryForMap(sql, '%'+SearchWord+'%');

						System.out.println(itemDataList);

						returnList = (Map<String, Object>) itemDataList;

				}catch(Exception ex) {
					System.out.println("エラーが発生しました。");
				}
				return returnList;
			}



}