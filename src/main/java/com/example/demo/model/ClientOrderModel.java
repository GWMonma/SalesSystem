package com.example.demo.model;

import java.util.Date;

public class ClientOrderModel {
	int client_order_no;
	int user_no;
	String item_name;
	String item_product_no;
	int item_buy_count;
	int total_price;
	Date item_buy_date;
	Date shipment_due_date;
	Date shipment_date;
	int completed_delivery;
	
	public int getClient_order_no() {
		return client_order_no;
	}
	public void setClient_order_no(int client_order_no) {
		this.client_order_no=client_order_no;
	}	
	
	
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no=user_no;
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
	
	
	public Date getItem_buy_date() {
		return item_buy_date;
	}
	public void setItem_buy_date(Date item_buy_date) {
		this.item_buy_date = item_buy_date;
	}
	
	
	
	public Date getShipment_due_date() {
		return  shipment_due_date;
	}
	public void setShipment_due_date(Date  shipment_due_date) {
		this. shipment_due_date= shipment_due_date;
	}


	
	public Date getShipment_date() {
		return shipment_date;
	}
	public void setShipment_date(Date shipment_date) {
		this.shipment_date=shipment_date;
	}	
	
	
	
	
	public int getComplete_delivery() {
		return completed_delivery;
	}
	public void setComplete_delivery(int completed_delivery) {
		this.completed_delivery=completed_delivery;
	}	
	
	
	
	
	

}
