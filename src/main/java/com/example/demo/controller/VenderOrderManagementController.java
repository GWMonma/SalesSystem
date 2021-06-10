package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.logic.VenderOrderLogic;

@Controller
public class VenderOrderManagementController {
	@Autowired
	private VenderOrderLogic venderOrderLogic;
	
	@Autowired
	private ItemJdbc itemJdbc;
	
	//ページを表示
	@RequestMapping("VenderOrderInput")
	public String venderOrderInputPage(Model model) {
		return "html/VenderOrderInput";
	}

	//計算を行い、合計金額を表示する
	@RequestMapping("VenderOrderCheck")
	public String orderCheck(@RequestParam("itemNo") int itemNo, @RequestParam("itemBuyCount") int itemBuyCount, Model model) {
		int totalPrice = venderOrderLogic.getVenderTotalPrice(itemNo, itemBuyCount);
		if(totalPrice==-1) {
			model.addAttribute("resultText", "エラーが発生しました。");
		}else{
			int itemPrice = itemJdbc.getItemData(itemNo).getItemPrice();
			model.addAttribute("itemNo", itemNo);
			model.addAttribute("itemBuyCount", itemBuyCount);
			model.addAttribute("totalPrice", itemPrice * itemBuyCount);
			model.addAttribute("resultText", "発注内容をご確認ください。");
		}
		return "html/VenderOrderInput";
	}
	
	//発注情報を保存する
	@RequestMapping("VenderOrderSave")
	public String venderOrderSave(@RequestParam("itemNo") int itemNo, @RequestParam("itemBuyCount") int itemBuyCount, @RequestParam("totalPrice") int totalPrice,Model model) {
		//保存するためのLogic
		String resultText = venderOrderLogic.venderOrderSaveLogic(itemNo, itemBuyCount, totalPrice);
		int checkItemTotalPrice = -10;
		//金額が変更された場合
		if(resultText.equals("金額が変更されました。申し訳ございませんが、もう一度ご確認ください。")) {
			checkItemTotalPrice = venderOrderLogic.getVenderTotalPrice(itemNo, itemBuyCount);
		}
		//金額変更の際にエラーが発生した場合
		if(checkItemTotalPrice==-1) {
			resultText = "エラーが発生しました。";
		}else if(checkItemTotalPrice>0){
			model.addAttribute("itemNo", itemNo);
			model.addAttribute("itemBuyCount", itemBuyCount);
			model.addAttribute("totalPrice", checkItemTotalPrice);
		}
		//resultTextを渡して画面遷移
		model.addAttribute("resultText", resultText);
		return "html/VenderOrderInput";
	}
		
	
}
