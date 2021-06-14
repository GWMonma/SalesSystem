package com.example.demo.logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.model.InventoryModel;


@Service
public class ItemLogic {
	@Autowired
	ItemJdbc itemjdbc;

	//データベースから在庫履歴を取得する。
		public ArrayList<InventoryModel>getInventoryLog(String searchWord) {
			ArrayList<InventoryModel> returnList = new ArrayList<InventoryModel>();
			returnList =itemjdbc.getInventoryLog(searchWord);

			return returnList;
		}
	
	//入力確認
	public String inputConfirmation(String... inputNo) {
		String returnText = "intTrue";
		int n = -1;
		try {
		for(String no: inputNo) {
			System.out.println(no);
			n = Integer.parseInt(no);
		}
		}catch(Exception ex){
			returnText = "数値を入力してください。";
		}
		
		return returnText;
	}
}
