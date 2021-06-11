package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.VenderOrderJdbc;
import com.example.demo.model.VenderOrderModel;

/*仕入れ管理関係のコントローラー*/
@Controller
public class  VenderStockManagementController  {
	 @Autowired
	 private VenderOrderJdbc venderOrderJdbc;

	 
	 //仕入管理画面表示
	    @GetMapping("/VenderStockManagement")
	    public String venderStockManagement() {
			return "html/VenderStockManagement";
		}
	    
	    
	 //フォームから入力された値を受け取りデータベースと照合
	    @RequestMapping("VenderSearch")
	    public String venderSearch(@RequestParam("item_name") String item_name, Model model){
			List<VenderOrderModel> list = venderOrderJdbc.getVenderOrder(item_name);
			model.addAttribute("venderOrderList",list);
	        return "html/VenderStockManagement";
	    }
	    
	 //日付の更新
	    @RequestMapping("VenderOrderDateUpdate")
	    public String venderOrderDateUpdate(@RequestParam("date_update") int vender_order_no, Model model){
			String resultText = venderOrderJdbc.arrivalDueDateUpdate(vender_order_no);
			model.addAttribute("resultText",resultText);
	        return "html/VenderStockManagement";
	    }

	   

	   
	}

