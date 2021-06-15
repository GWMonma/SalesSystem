package com.example.demo.logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.VenderOrderJdbc;
import com.example.demo.model.InventoryModel;

@Service
public class ItemLogic {
	@Autowired
	VenderOrderJdbc venderOrderJdbc;

	@Autowired
	ItemJdbc itemjdbc;

	//データベースから在庫履歴を取得する。
			public ArrayList<InventoryModel>getInventoryLog(String searchWord) {
				ArrayList<InventoryModel> returnList = new ArrayList<InventoryModel>();
				returnList =itemjdbc.getInventoryLog(searchWord);

				return returnList;
			}

			//データベース内の在庫履歴を更新する。
			public ArrayList<InventoryModel>getInventoryUpdate(String searchWord,String updateStock) {
				ArrayList<InventoryModel> returnList = new ArrayList<InventoryModel>();
				returnList =itemjdbc.getInventoryUpdate(searchWord,updateStock);

				return returnList;
			}

	//入力確認
	public String inputConfirmation(String... inputNo) {
		String returnText = "true";
		try {
		for(String no: inputNo) {
			Integer.parseInt(no);
		}
		}catch(Exception ex){
			returnText = "数値を入力してください。";
		}

		return returnText;
	}

	//入荷確定処理
	public String arrivalFixingLogic(int itemNo) {
		String returnText = venderOrderJdbc.arrivalFixing(itemNo);
		return returnText;
	}

	//入荷確定処理を行う前の確認処理。
	public String checkItemNoLogic(int venderOrderNo) {
		String returnText = "";
		int venderOrderLogSize = venderOrderJdbc.getVenderOrderLog("").size();
		String checkArrivalDate = venderOrderJdbc.CheckArrivalDue(venderOrderNo);
		//番号が存在するか確認
    	if(venderOrderNo>venderOrderLogSize) {
    		return "入力された入荷番号は存在しません。";
    	}

    	//入荷確定済みか確認
    	if(checkArrivalDate == null) {
        	//入荷確定処理を行う
    		returnText = arrivalFixingLogic(venderOrderNo);
    	}else if(checkArrivalDate.equals("入荷確定済み")){
    		returnText = "入荷確定済みです。";
    	}else {
    		returnText = "エラーが発生しました。";
    	}

		return returnText;
	}
}
