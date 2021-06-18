package com.example.demo.logic;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.QuotationJdbc;
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

}
