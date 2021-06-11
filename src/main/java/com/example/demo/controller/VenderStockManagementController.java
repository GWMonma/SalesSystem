package com.example.demo.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.VenderOrderJdbc;
import com.example.demo.model.VenderOrderModel;

/*仕入れ管理関係のコントローラー*/
@Controller
public class  VenderStockManagementController  {
	 @Autowired
	 private VenderOrderJdbc venderOrderJdbc;
	 
	 @Autowired
	 private ItemJdbc itemJdbc;

	 
	 //仕入管理画面表示
	    @GetMapping("/VenderStockManagement")
	    public String venderStockManagement() {
			return "html/VenderStockManagement";
		}
	    
	    
	 //フォームから入力された値を受け取りデータベースと照合
	    @RequestMapping("VenderSearch")
	    public String venderSearch(@RequestParam("item_name") String item_name, Model model){
			ArrayList<VenderOrderModel> list = venderOrderJdbc.getVenderOrderLog(item_name);
			model.addAttribute("venderOrderList",list);
			model.addAttribute("item_name", item_name);
	        return "html/VenderStockManagement";
	    }
	    
	 //日付の更新
	    @RequestMapping("VenderOrderDateUpdate")
	    public String venderOrderDateUpdate(@RequestParam("item_name") String item_name, @RequestParam("date_update") String vender_order_no, Model model){
	    	int totalItemNo = itemJdbc.getItemDataList().size();
	    	String resultText = null;
	    	int no = -1;
	    	ArrayList<VenderOrderModel> list = venderOrderJdbc.getVenderOrderLog(item_name);
	    	model.addAttribute("venderOrderList",list);
	    	model.addAttribute("item_name", item_name);
			
	    	try {
				no = Integer.parseInt(vender_order_no);
			}catch(Exception ex){
				model.addAttribute("resultText","数値を入力してください。");
		        return "html/VenderStockManagement";
			}
			
	    	if(totalItemNo<no) {
	    		resultText = "入力された番号は存在しません。";
	    	}else{
	    		resultText = venderOrderJdbc.arrivalDueDateUpdate(no);
	    	}
			model.addAttribute("resultText",resultText);
	        return "html/VenderStockManagement";
	    }

	   

	   
	}

