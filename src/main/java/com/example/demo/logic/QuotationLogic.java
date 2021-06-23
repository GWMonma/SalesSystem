package com.example.demo.logic;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.QuotationJdbc;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.QuotationModel;


@Service
public class QuotationLogic {
	 @Autowired
	 ItemJdbc itemJdbc;
	 
	 @Autowired
	 QuotationJdbc quotationJdbc;
	 
	 @Autowired
	 HttpSession session;
	 
	//データベースに見積情報を保存する
	public String quotationSaveLogic(int itemNo, int itemBuyCount) {
		Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) list.get("user_no");
		//既に保存されているか確認
		ArrayList<Integer> returnList = quotationJdbc.quotationCheck(userNo, itemNo);
		
		if(returnList.get(0)==-1) {//保存されていない場合
			return quotationJdbc.quotationSave(userNo, itemBuyCount, itemNo);
		
		}else{//保存されている場合
			int itemStock = itemJdbc.getItemData(itemNo).getItemStock();
			
			if(itemStock<returnList.get(0)+itemBuyCount) {//在庫数を上回っている場合
				return "既に保存されている購入数に加算した結果、購入希望数が在庫数を超えています。";
			
			}else{
				return quotationJdbc.quotationUpdate(returnList.get(1), userNo, (returnList.get(0)+itemBuyCount));
			}
		}
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
