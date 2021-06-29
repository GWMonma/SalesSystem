package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ClientOrderJdbc;
import com.example.demo.logic.ClientOrderLogic;
import com.example.demo.logic.ItemLogic;
import com.example.demo.logic.VenderOrderLogic;
import com.example.demo.model.ClientOrderModel;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.LoginModel;
import com.example.demo.model.VenderOrderModel;

/*在庫管理関係のコントローラー*/
@Controller
public class InventoryManagementController {
	String message;
	@Autowired
	HttpSession session;

	@Autowired
	private ClientOrderJdbc clientOrderJdbc;
	
	@Autowired
	ItemLogic itemLogic;
	
	@Autowired
	VenderOrderLogic venderOrderLogic;
	
	@Autowired
	ClientOrderLogic COLogic;



	 /*入荷管理画面へ遷移*/
   	@RequestMapping("ArrivalManagement")
    	public String arrival(Model model) {
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}

        	return "html/ArrivalManagement";
    	}

	    /*出荷管理画面へ遷移*/
	    @RequestMapping("ShipmentManagement")
	    public String shipment(Model model) {
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}

	        return "html/ShipmentManagement";
	    }

	    /*在庫管理画面へ遷移*/
	    @RequestMapping("InventoryManagement")
	    public String inventory(Model model) {
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}
	        return "html/InventoryManagement";
	    }

	    /*棚卸・在庫調整画面へ遷移*/
	    @RequestMapping("InventoryAdjustment")
	    public String  	inventoryadjustment(Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
		    model.addAttribute("indexForm", new LoginModel());
		    model.addAttribute("message" , message);
		    return "html/Login";
		}
	        return "html/InventoryAdjustment";
	    }

	    @RequestMapping("InventrySearch")
		public String inventoryadjustmentsearch(@RequestParam("item_name") String searchWord, Model model) {
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}
	    	ArrayList<InventoryModel> returnList = itemLogic.getInventoryLog(searchWord);
			//検索結果を渡す
			if(returnList.size()==0){
				model.addAttribute("searchList", null);
			}else{
				model.addAttribute("searchList", returnList);
			}
			model.addAttribute("resultText", "検索結果"+returnList.size()+"件");
			return "html/InventoryAdjustment";
		}
	
	//出荷管理↓
	//データベースと照合して受注情報を表示(ボタン)
	@RequestMapping("ClientOrderSearchButton")
		public String orderSearchButton(@RequestParam("selectBtn") String selectBtn,Model model){
			if(session.getAttribute("data") == null) {
				message ="IDとパスワードを入力してください";
				model.addAttribute("indexForm", new LoginModel());
				model.addAttribute("message" , message);
				return "html/Login";
			}
		//セッションからuser_noを取得
    	Map<String, Object> sessionlist = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) sessionlist.get("user_no");
		String selectBtnText = COLogic.shipmentStateBtnStr(selectBtn);
		if(selectBtnText.equals("エラー")) {
    		model.addAttribute("resultText", selectBtnText+"が発生しました。");
    		return "html/ArrivalManagement";
    	}
		ArrayList<ClientOrderModel> list = clientOrderJdbc.getClientOrderLog(selectBtnText, userNo);
		if(list.size()>0) {
			model.addAttribute("clientOrderList",list);
    	}
		model.addAttribute("search", "button");
		model.addAttribute("selectBtn", selectBtn);
		model.addAttribute("resultText", "検索結果："+list.size()+"件");
		return "html/ShipmentManagement";
	}
			    
		
	//商品名をデータベースと照合して受注情報を表示(フォーム)
	@RequestMapping("ClientOrderSearchForm")
	public String orderSearchForm(@RequestParam("searchWord") String searchWord, Model model){
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
		//セッションからuser_noを取得
    	Map<String, Object> sessionlist = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) sessionlist.get("user_no");
		ArrayList<ClientOrderModel> list = clientOrderJdbc.getClientOrderLog(searchWord, userNo);
		if(list.size()>0) {
			model.addAttribute("clientOrderList",list);
    	}
		model.addAttribute("item_name", searchWord);
		model.addAttribute("search", "word");
		model.addAttribute("resultText", "検索結果："+list.size()+"件");
	  	return "html/ShipmentManagement";
	}
	
	
	//出荷予定日の更新
		@RequestMapping("ShipmentDueDateUpdate")
		public String shipmentDueDateUpdate(@RequestParam("client_order_no") String client_order_no,@RequestParam("shipment_due_date") String shipment_due_date ,@RequestParam("searchWord") String searchWord,@RequestParam("selectBtn") String selectBtn, @RequestParam("search") String search, Model model){
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}
			//セッションからuser_noを取得
	    	Map<String, Object> sessionlist = (Map<String, Object>) session.getAttribute("data");
			int userNo = (int) sessionlist.get("user_no");
			ArrayList<ClientOrderModel> list = new ArrayList<ClientOrderModel>();
			//intに変換可能か確認する
	    	String noConfirmationStr = itemLogic.inputConfirmation(client_order_no);
	    	if(noConfirmationStr.equals("true")){
	    	}else{
	    		model.addAttribute("resultText", noConfirmationStr);
	    		return "html/ShipmentManagement";
	    	}
	    	String returnText = COLogic.checkShipmentDueDateLogic(Integer.parseInt(client_order_no),shipment_due_date, userNo);
			model.addAttribute("resultText",returnText);
			
	    	//キーワード検索
	    	if(selectBtn.equals("")&& search.equals("word")){
				list = clientOrderJdbc.getClientOrderLog(searchWord, userNo);
	    		model.addAttribute("search", "word");
	    		model.addAttribute("item_name", searchWord);
	    		model.addAttribute("resultText", "検索結果："+list.size()+"件");
	    	}
	    	//button検索
	    	if(searchWord.equals("") && search.equals("button")){
	    		String selectBtnText = COLogic.shipmentStateBtnStr(selectBtn);
	    		list = clientOrderJdbc.getClientOrderLog(selectBtnText, userNo);
	    		model.addAttribute("search", "button");
	        	model.addAttribute("selectBtn", selectBtn);
	        	model.addAttribute("resultText", selectBtnText+"："+list.size()+"件");
	    	}
	    	if(list.size()>0) {
	    		model.addAttribute("clientOrderList", list);
	    	}

			return "html/ShipmentManagement";
		}
		 	
		//出荷日の更新(出荷確定処理)
		@RequestMapping("ShipmentDateUpdate")
		public String shipmentDateUpdate(@RequestParam("client_order_no") String client_order_no,@RequestParam("searchWord") String searchWord,@RequestParam("selectBtn") String selectBtn, @RequestParam("search") String search, Model model){
    		if(session.getAttribute("data") == null) {
    			message ="IDとパスワードを入力してください";
    	    		model.addAttribute("indexForm", new LoginModel());
    	    		model.addAttribute("message" , message);
    	    		return "html/Login";
    		}
			//セッションからuser_noを取得
	    	Map<String, Object> sessionlist = (Map<String, Object>) session.getAttribute("data");
			int userNo = (int) sessionlist.get("user_no");
			ArrayList<ClientOrderModel> list = new ArrayList<ClientOrderModel>();
			//intに変換可能か確認する
	    	String noConfirmationStr = itemLogic.inputConfirmation(client_order_no);
	    	if(noConfirmationStr.equals("true")){
	    	}else{
	    		model.addAttribute("resultText", noConfirmationStr);
	    		return "html/ShipmentManagement";
	    	}
	    	String returnText = COLogic.checkClientOrderNoLogic(Integer.parseInt(client_order_no), userNo);
	    	model.addAttribute("resultText",returnText);
	    	
			//キーワード検索
	    	if(selectBtn.equals("")&& search.equals("word")){
				list = clientOrderJdbc.getClientOrderLog(searchWord, userNo);
	    		model.addAttribute("search", "word");
	    		model.addAttribute("item_name", searchWord);
	    		model.addAttribute("resultText", "検索結果："+list.size()+"件");
	    	}
	    	//button検索
	    	if(searchWord.equals("") && search.equals("button")){
	    		String selectBtnText = COLogic.shipmentStateBtnStr(selectBtn);
	    		list = clientOrderJdbc.getClientOrderLog(selectBtnText, userNo);
	    		model.addAttribute("search", "button");
	        	model.addAttribute("selectBtn", selectBtn);
	        	model.addAttribute("resultText", selectBtnText+"："+list.size()+"件");
	    	}
	    	if(list.size()>0) {
	    		model.addAttribute("clientOrderList", list);
	    	}
			
			return "html/ShipmentManagement";
		}
//出荷管理↑
	
	
		
  //在庫の更新
    @RequestMapping("InventryUpdate")
  		public String inventoryadjustmentupdate(@RequestParam("item_no") String searchWord, @RequestParam("item_stock") String updateStock,Model model){
			if(session.getAttribute("data") == null) {
				message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
			}
    		ArrayList<InventoryModel> returnList = itemLogic.getInventoryUpdate(searchWord,updateStock);

  			//更新結果を渡す
  			if(returnList.size()==0){
  				model.addAttribute("searchList", null);
  				model.addAttribute("resultText", "更新できませんでした。");
  				return "html/InventoryAdjustment";
  			}else{
  				model.addAttribute("searchList", returnList);
  			}
  			model.addAttribute("resultText", "更新しました！");
  			return "html/InventoryAdjustment";
  		}
	
    //入荷確定処理
    @RequestMapping("ArrivalDateUpdate")
    public String arrivalDueDateUpdate(@RequestParam("itemNo") String itemNo, @RequestParam("searchItemName") String searchItemName, @RequestParam("selectBtn") String selectBtn, @RequestParam("search") String search, Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
    	int no;
    	//intに変換可能か確認する
    	String noConfirmationStr = itemLogic.inputConfirmation(itemNo);
    	if(noConfirmationStr.equals("true")){
    		no = Integer.parseInt(itemNo);
    	}else{
    		model.addAttribute("resultText", noConfirmationStr);
    		return "html/ArrivalManagement";
    	}
    	
    	//入力された番号が存在するか、既に確定されているか確認し、入荷確定処理を行う。
    	String resultText = itemLogic.checkItemNoLogic(no);
    	if(resultText.equals("入荷が確定")){
        	//在庫を追加する
        	String resultText2 = itemLogic.InventoryAdditionLogic(no);
        	model.addAttribute("resultText", resultText+resultText2);
    	}else{
        	model.addAttribute("resultText", resultText);
    	}
    	
    	//検索方法によって取得情報を変更する
    	ArrayList<VenderOrderModel> searchList = new ArrayList<VenderOrderModel>();
    	//キーワード検索
    	if(selectBtn.equals("")&& search.equals("word")){
    		searchList = venderOrderLogic.getVenderOrderLog(searchItemName);
    		model.addAttribute("search", "word");
    		model.addAttribute("searchItemName", searchItemName);
    		model.addAttribute("searchResultText", "検索結果："+searchList.size()+"件");
    	}
    	
    	//ボタン検索
    	if(searchItemName.equals("") && search.equals("button")){
    		searchList = venderOrderLogic.getVenderOrderLog(selectBtn, "button");
    		String selectBtnText = venderOrderLogic.arrivalStateBtnStr(selectBtn);
    		model.addAttribute("search", "button");
        	model.addAttribute("selectBtn", selectBtn);
        	model.addAttribute("searchResultText", selectBtnText+"："+searchList.size()+"件");
    	}
    	if(searchList.size()>0) {
    		model.addAttribute("searchList", searchList);
    	}
    	
    	return "html/ArrivalManagement";
    	
    }
    
    //検索機能
    @RequestMapping("ArrivalDataKeywordSearch")
    public String arrivalDateSearch(@RequestParam("searchItemName") String searchItemName, Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
    	ArrayList<VenderOrderModel> searchList = venderOrderLogic.getVenderOrderLog(searchItemName);
    	if(searchList.size()>0) {
    		model.addAttribute("searchList", searchList);
    	}
    	model.addAttribute("searchResultText", "検索結果："+searchList.size()+"件");
    	model.addAttribute("search", "word");
    	model.addAttribute("searchItemName", searchItemName);
    	return "html/ArrivalManagement";
    }
    
    //入荷前、入荷済みボタン用。検索機能
    @RequestMapping("ArrivalDataSearch")
    public String arrivalDataSearchBtn(@RequestParam("selectBtn") String selectBtn, Model model) {
		if(session.getAttribute("data") == null) {
			message ="IDとパスワードを入力してください";
	    		model.addAttribute("indexForm", new LoginModel());
	    		model.addAttribute("message" , message);
	    		return "html/Login";
		}
    	String selectBtnText = venderOrderLogic.arrivalStateBtnStr(selectBtn);
    	if(selectBtnText.equals("エラー")) {
    		model.addAttribute("resultText", selectBtnText+"が発生しました。");
    		return "html/ArrivalManagement";
    	}
    	ArrayList<VenderOrderModel> searchList = venderOrderLogic.getVenderOrderLog(selectBtn, "button");
    	if(searchList.size()>0) {
    		model.addAttribute("searchList", searchList);
    	}
    	model.addAttribute("search", "button");
    	model.addAttribute("selectBtn", selectBtn);
    	model.addAttribute("searchResultText", selectBtnText+"："+searchList.size()+"件");
    	return "html/ArrivalManagement";
    }
	
}
