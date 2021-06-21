package com.example.demo.logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ClientOrderJdbc;
import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.VenderOrderJdbc;
import com.example.demo.model.InventoryModel;

@Service
public class ItemLogic {
	@Autowired
	VenderOrderJdbc venderOrderJdbc;

	@Autowired
	ItemJdbc itemjdbc;
	
	@Autowired
	ClientOrderJdbc clientOrderJdbc;

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
		int inputNoInt;
		try {
		for(String no: inputNo) {
			inputNoInt = Integer.parseInt(no);
			//負の数か判断する
			
			if(Math.signum(inputNoInt) == -1.0) {
				return "負数は入力できません。";
			}
			if(inputNoInt==0) {
				return "入力された番号は存在しません。";
			}
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
	
	//在庫追加処理
	public String InventoryAdditionLogic(int venderOrderNo) {
		String returnText = null;
		//品番と入荷数を取得
		ArrayList<VenderOrderModel> list = venderOrderJdbc.getVenderOrderLog(String.valueOf(venderOrderNo), "no");
		String itemProductNo = list.get(0).getItem_product_no();
		int itemBuyCount = list.get(0).getItem_buy_count();
		//在庫を追加
		returnText = itemJdbc.InventoryAddition(itemProductNo, itemBuyCount);
		return returnText;
	}
	
//出荷管理↓
	//出荷確定処理を行う前の確認処理
			public String checkClientOrderNoLogic(int client_order_no) {
				String returnText = "";
				int clientOrderLogSize = clientOrderJdbc.getClientOrderLog("").size();
				String checkShipmentDate = clientOrderJdbc.CheckShipmentDue(client_order_no);
				//番号が存在するか確認
		    	if(client_order_no>clientOrderLogSize) {
		    		return "出荷番号が存在しません。";
		    	}

		    	//出荷確定済みか確認
		    	if(checkShipmentDate == null) {
		        	//出荷確定処理を行う
		    		returnText = shipmentFixingLogic(client_order_no);
		    	}else if(checkShipmentDate.equals("出荷済み")){
		    		returnText = "出荷済みです。";
		    	}else {
		    		returnText = "エラーが発生しました。";
		    	}

				return returnText;
			}
			
			//出荷確定処理を行う前の確認処理
			public String checkShipmentDueDateLogic(int client_order_no,String shipment_due_date) {
				String returnText = "";
				int clientOrderLogSize = clientOrderJdbc.getClientOrderLog("").size();
				String checkShipmentDate = clientOrderJdbc.CheckShipmentDue(client_order_no);
				//番号が存在するか確認
		    	if(client_order_no>clientOrderLogSize) {
		    		return "番号が存在しません。";
		    	}

		    	//出荷確定済みか確認
		    	if(checkShipmentDate == null) {
		        	//出荷確定処理を行う
		    		returnText = clientOrderJdbc.shipmentDueDateUpdateJdbc(client_order_no,shipment_due_date);
		    	}else if(checkShipmentDate.equals("出荷済み")){
		    		returnText = "出荷済みです。";
		    	}else {
		    		returnText = "エラーが発生しました。";
		    	}

				return returnText;
			}
			
			
	//出荷確定処理
		public String shipmentFixingLogic(int client_order_no) {
			String returnText = clientOrderJdbc.shipmentDateUpdateJdbc(client_order_no);
			return returnText;
		}
		
	//出荷日更新処理
		public String shipmentDueDateUpdateLogic(int client_order_no,String shipment_due_date) {
			String returnText = clientOrderJdbc.shipmentDueDateUpdateJdbc(client_order_no,shipment_due_date);
			return returnText;
		}
		
		

	//どのボタンが押されたか判断
		public String shipmentStateBtnStr(String selectBtn) {
			String returnText = null;
			if(selectBtn.equals("shipped")) {
				returnText = "出荷済みの商品";
				
			}else if(selectBtn.equals("entered")) {
				returnText = "出荷前の商品";
				
			}else if(selectBtn.equals("unentered")) {
				returnText="出荷予定日未入力の商品";
			}
			else{
				returnText = "エラー";
			}
				
			return returnText;
			
		}
		
//出荷管理↑
}
