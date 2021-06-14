package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.ItemLogic;

/*在庫管理関係のコントローラー*/
@Controller
public class InventoryManagementController {
	 @Autowired
	 HttpSession session;
	 @Autowired
	 ItemLogic itemLogic;

 /*入荷管理画面へ遷移*/
    @RequestMapping("ArrivalManagement")
	public String inventory(Model model) {
    	return "html/ArrivalManagement";
	}
    
    @RequestMapping("ArrivalDateUpdate")
    public String arrivalDueDateUpdate(@RequestParam("itemNo") String itemNo, Model model) {
    	//intに変換可能か確認する
    	String noConfirmationStr = itemLogic.inputConfirmation(itemNo);
    	if(noConfirmationStr.equals("数値を入力してください。")){
    		model.addAttribute("resultText", noConfirmationStr);
    		return "html/ArrivalManagement";
    	}
    	
    	//入力された番号が存在するか、既に確定されているか確認し、入荷確定処理を行う。
    	String resultText = itemLogic.checkItemNoLogic(Integer.parseInt(itemNo));
    	model.addAttribute("resultText", resultText);
        return "html/ArrivalManagement";
    }
    


}