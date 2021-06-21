package com.example.demo.logic;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.QuotationJdbc;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.QuotationModel;
import com.example.demo.model.InventoryModel;

@Service
public class QuotationLogic {
	 @Autowired
	 ItemJdbc itemJdbc;
	 
	 @Autowired
	 QuotationJdbc quotationJabc;
	 
	 @Autowired
	 HttpSession session;
	 
	//データベースに見積情報を保存する
	public String quotationSaveLogic(int itemNo, int itemBuyCount) {			
		//見積情報を保存
		Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) list.get("user_no");
		return quotationJabc.quotationSave(userNo, itemBuyCount, itemNo);
		
		}
	
	//合計金額を計算
		public int getQuotationTotalPrice(int itemNo, int itemBuyCount) {
			InventoryModel itemData = itemJdbc.getItemData(itemNo);
			int returnTotalPrice = -10;
			//計算に失敗した場合
			if(itemData==null) {
				returnTotalPrice = -1;
			}else{
				returnTotalPrice = itemData.getItemPrice()*itemBuyCount;
			}
			return returnTotalPrice;
		}
	
	//見積情報を検索
		public ArrayList<QuotationModel> quotationSearchListLogic(String... searchWord){
			ArrayList<InventoryModel> searchNoList = new ArrayList<InventoryModel>();
			//ユーザー番号を取得
			ArrayList<QuotationModel> returnList = new ArrayList<QuotationModel>();
			Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
			int userNo = (int) list.get("user_no");
				if(searchWord.length>0) {
					//品番取得、合計金額計算
					searchNoList = itemJdbc.getInventoryLog(searchWord[0]);
					returnList = quotationJdbc.quotationSearchList(userNo, searchNoList);
				}else if(searchWord.length==0){
					//品番取得、合計金額計算
					returnList = quotationJdbc.quotationSearchList(userNo, searchNoList);
				}
			return returnList;
		}
		
	//見積情報を削除
		public String quotationDeleteLogic(int quotationNo) {
			String returntext = quotationJdbc.quotationDelete(quotationNo);
			return returntext;
		}

}
