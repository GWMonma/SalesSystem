package com.example.demo.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.VenderOrderJdbc;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.VenderOrderModel;

@Service
public class VenderOrderLogic {
	@Autowired
	private ItemJdbc itemJdbc;
	@Autowired
	private VenderOrderJdbc venderOrderJdbc;	
	
	
	//データベースに発注情報を保存する
	public String venderOrderSaveLogic(int itemNo, int itemBuyCount, int totalPrice) {
		//商品情報の取得
		InventoryModel itemData = itemJdbc.getItemData(itemNo);
		if(itemData==null) {
			return "エラーが発生しました。";
		}
		//合計金額が変更された場合
		if(totalPrice != (itemData.getItemPrice()*itemBuyCount) ) {
			return "金額が変更されました。申し訳ございませんが、もう一度ご確認ください。";
		}
		
		//現在の日付を取得
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String itemBuyDate = format.format(date);
		
		//発注情報を保存
		venderOrderJdbc.venderOrderSave(itemData, itemBuyCount, totalPrice, itemBuyDate);
		return "発注が完了しました。";
	
	}
	
	//合計金額を計算
	public int getVenderOrderTotalPrice(int itemNo, int itemBuyCount) {
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
	
	//データベースから発注履歴を取得する。
	public ArrayList<VenderOrderModel> getVenderOrderLog(String searchWord) {
		ArrayList<VenderOrderModel> returnList = new ArrayList<VenderOrderModel>();
		returnList = venderOrderJdbc.getVenderOrderLog(searchWord);
		
		return returnList;
	}
	
	//番号が存在するか確認する
	

}
