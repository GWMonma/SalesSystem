package com.example.demo.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.ItemLogic;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.LoginModel;

/*在庫管理関係のコントローラー*/
@Controller
public class InventoryManagementController {
	String message;
	 @Autowired
	 HttpSession session;

	@Autowired
	private ItemJdbc itemJdbc;

	@Autowired
	private ClientOrderJdbc clientOrderJdbc;



	 /*入荷管理画面へ遷移*/
	    @RequestMapping("ArrivalManagement")
	    public String arrival(Model model) {
	    	if(session.getAttribute("data") == null) {
	    		message ="IDとパスワードを入力してください";
	    	    model.addAttribute("indexForm", new LoginModel());
	    	    model.addAttribute("message" , message);
	    	    return "Login";
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
	    	    return "Login";
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
		    return "Login";
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
		    return "Login";
		}
	        return "html/InventoryAdjustment";
	    }

	    @Autowired
		private ItemLogic ItemLogic;

	    @RequestMapping("InventrySearch")
		public String inventoryadjustmentsearch(@RequestParam("item_name") String searchWord, Model model) {
			ArrayList<InventoryModel> returnList = ItemLogic.getInventoryLog(searchWord);
			//検索結果を渡す
			if(returnList.size()==0){
				model.addAttribute("searchList", null);
			}else{
				model.addAttribute("searchList", returnList);
			}
			model.addAttribute("resultText", "検索結果"+returnList.size()+"件");
			return "html/InventoryAdjustment";
		}


  //データベースと照合して受注情報を表示(フォーム)
    @RequestMapping("OrderSearchForm")
    public String orderSearch1(@RequestParam("item_name") String item_name, Model model){
		ArrayList<ClientOrderModel> list = clientOrderJdbc.getClientOrderLog(item_name);
		model.addAttribute("clientOrderList",list);
		model.addAttribute("item_name", item_name);
        return "html/ShipmentManagement";
    }


	    //出荷予定日の更新
	 @RequestMapping("ShipmentManagementDateUpdate")
	    public String shipmentManagementDateUpdate(@RequestParam("order_no") String client_order_no,@RequestParam("date_update") String shipment_due_date ,@RequestParam("item_name") String item_name, Model model){

		 	int totalItemNo = itemJdbc.getItemDataList().size();
	    	String resultText = null;
	    	int no = -1;

	    	try {
				no = Integer.parseInt(client_order_no);
			}catch(Exception ex){
				model.addAttribute("resultText","数値を入力してください。");
		        return "html/ShipmentManagement";
			}

	    	if(totalItemNo<no) {
	    		resultText = "入力された番号は存在しません。";
	    	}else{
	    		resultText = clientOrderJdbc.shipmentDateUpdate(no,shipment_due_date);
	    	}
	    	ArrayList<ClientOrderModel> list = clientOrderJdbc.getClientOrderLog(item_name);
	    	model.addAttribute("clientOrderList",list);
	    	model.addAttribute("item_name", item_name);
			model.addAttribute("resultText",resultText);
	        return "html/ShipmentManagement";
	    }






}
