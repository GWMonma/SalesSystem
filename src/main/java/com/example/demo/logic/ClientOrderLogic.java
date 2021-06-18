package com.example.demo.logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.jdbc.ClientOrderJdbc;
import com.example.demo.model.ClientOrderModel;

@Service
public class ClientOrderLogic {

	@Autowired
	private ClientOrderJdbc ClientOrderJdbc;

	//データベースから売上履歴を取得する。
			public ArrayList<ClientOrderModel> getSalesSearch(String SearchWord) {
				ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
				returnList = ClientOrderJdbc.getSalesSearch(SearchWord);
				return returnList;
			}
}


