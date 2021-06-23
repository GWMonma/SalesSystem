package com.example.demo.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.model.InventoryModel;
import com.example.demo.model.QuotationModel;

@Component
public class QuotationJdbc {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//見積情報を保存
	public String quotationSave(int userNo,int itemBuyCount, int itemNo) {
		try {
			this.jdbcTemplate.update("INSERT INTO quotation (user_no, item_buy_count, item_no)"
				+"VALUES(?, ?, ?);",
				userNo, itemBuyCount, itemNo);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "見積情報の保存が完了しました。";
	}
	
	//見積情報を上書き保存
	public String quotationUpdate(int quotationNo, int userNo,int itemBuyCount) {
		System.out.println("UPDATE quotation SET item_buy_count = "+itemBuyCount+" WHERE quotation_no = "+quotationNo+" AND user_no = "+userNo);
		try {
			this.jdbcTemplate.update("UPDATE quotation SET item_buy_count = ? WHERE quotation_no = ? AND user_no = ?"
				,itemBuyCount, quotationNo, userNo);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "見積情報の更新が完了しました。";
	}
	
	//見積情報を削除
	public String quotationDelete(int quotationNo) {
		System.out.println("DB:"+quotationNo);
		try {
			this.jdbcTemplate.update("DELETE FROM quotation WHERE quotation_no = ?", quotationNo);
		}catch(Exception ex) {
			return "エラーが発生しました。";
		}
		return "見積情報の削除が完了しました。";
	}
	
	//見積情報が存在するか
	public ArrayList<Integer> quotationCheck(int userNo, int itemNo) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		List<Map<String, Object>> quotationSearchList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM quotation WHERE user_no = ? AND item_no = ?";
		quotationSearchList = jdbcTemplate.queryForList(sql, userNo, itemNo);
		//存在しない場合
		if(quotationSearchList.size()==0) {
			returnList.add(-1);
		//存在する場合
		}else{
			returnList.add((int) quotationSearchList.get(0).get("item_buy_count"));
			returnList.add((int) quotationSearchList.get(0).get("quotation_no"));
		}
		return returnList;
	}
	
	//見積情報を取得
	public ArrayList<QuotationModel> quotationSearchList(int userNo, ArrayList<InventoryModel> searchNoList) {
		ArrayList<QuotationModel> returnList = new ArrayList<QuotationModel>();
		List<Map<String, Object>> quotationSearchList = new ArrayList<Map<String, Object>>();
		StringBuilder noStr = new StringBuilder();
		int forNo = 1;
		for(InventoryModel searchNo : searchNoList) {
			if(forNo == 1) {
				forNo++;
			}else{
				noStr.append(",");
			}
			noStr.append(searchNo.getItemNo());
		}
		try {
			//検索
			if(searchNoList.size()>0) {
				String sql = "SELECT * FROM quotation WHERE user_no = ? AND item_no in ("+noStr.toString()+")";
				quotationSearchList = jdbcTemplate.queryForList(sql, userNo);
				//格納する
				for(Map<String, Object> mapData : quotationSearchList) {
					QuotationModel returnData = new QuotationModel();
					returnData.setQuotationNo((int)mapData.get("quotation_no"));
					returnData.setUserNo((int)mapData.get("user_no"));
					returnData.setItemBuyCount((int)mapData.get("item_buy_count"));
					returnData.setItemNo((int)mapData.get("item_no"));
					
					//quotationのitemNoとsearchNoListのitemNoが一致した場合、searchNoListの商品名、品番、金額を格納する
					for(InventoryModel searchNo : searchNoList) {
						if((int)mapData.get("item_no") == searchNo.getItemNo()) {
							returnData.setItemName((String)searchNo.getItemName());
							returnData.setItemProductNo((String)searchNo.getItemProductNo());
							returnData.setTotalPrice((int)searchNo.getItemPrice() * (int)mapData.get("item_buy_count"));
							returnList.add(returnData);
						}
					}
					
				}
			}
			
		}catch(Exception ex) {
		}
		
		return returnList;
	}
	
}
