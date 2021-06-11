package com.example.demo.model;

import java.util.Date;

public class VenderOrderModel {
	
	int vender_order_no;
	String item_name;
	String item_product_no;
	int item_buy_count;
	int total_price;
	Date arrival_due_date;

	
	public int getVender_order_no() {
		return vender_order_no;
	}
	public void setVender_order_no(int vender_order_no) {
		this.vender_order_no=vender_order_no;
	}

	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name=item_name;
	}	


	public String getItem_product_no() {
		return item_product_no;
	}
	public void setItem_product_no(String item_product_no) {
		this.item_product_no=item_product_no;
	}
	
	
	public int getItem_buy_count() {
		return item_buy_count;
	}
	public void setItem_buy_count(int item_buy_count) {
		this.item_buy_count=item_buy_count;
	}
	
	
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price=total_price;
	}

	
	public Date getArrival_due_date() {
		return  arrival_due_date;
	}
	public void setArrival_due_date(Date  arrival_due_date) {
		this. arrival_due_date= arrival_due_date;
	}






	
	
	
	
}
