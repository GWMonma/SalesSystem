package com.example.demo.model;

public class QuotationModel {

	int quotationNo;
	int userNo;
	int itemBuyCount;
	int itemNo;
	String itemName = null;
	String itemProductNo = null;
	int totalPrice;
	
	public int getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(int quotationNo) {
		this.quotationNo = quotationNo;
	}
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public int getItemBuyCount() {
		return itemBuyCount;
	}
	public void setItemBuyCount(int itemBuyCount) {
		this.itemBuyCount = itemBuyCount;
	}
	
	public int getItemNo() {
		return itemNo;
	}
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemProductNo() {
		return itemProductNo;
	}
	public void setItemProductNo(String itemProductNo) {
		this.itemProductNo = itemProductNo;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
