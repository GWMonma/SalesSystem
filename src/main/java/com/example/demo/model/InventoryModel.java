package com.example.demo.model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryModel {
	int itemNo;
	String itemName = null;
	String itemProductNo = null;
	int itemPrice;
	int itemStock;
	

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
	

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	

	public int getItemStock() {
		return itemStock;
	}

	public void setItemStock(int itemStock) {
		this.itemStock = itemStock;
	}
	
}
