package com.example.demo.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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


	//売上出力を取得。
	public ArrayList<ClientOrderModel> getSalesOutput(String SearchWord)  throws EncryptedDocumentException, IOException{
		ArrayList<ClientOrderModel> returnList = new ArrayList<ClientOrderModel>();
		returnList = ClientOrderJdbc.getSalesOutput(SearchWord);
		//データベースから売上合計を取得する。
		Map<String, Object> returnSum = ClientOrderJdbc.getSalesTotalPrice(SearchWord);

		System.out.println(returnSum.get("month_total"));

		 // Excelファイルを作成
	    Workbook workbook = new XSSFWorkbook();

	    // シートを作成
	    Sheet sheet = workbook.createSheet("売上書");

	    //セルの枠線削除
	    sheet.setDisplayGridlines(false);

	    //セルのスタイル設定
	    CellStyle style =  workbook.createCellStyle();

	    Calendar cl = Calendar.getInstance();

	  //日付をyyyy/MM/ddの形で出力する
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        String today = sdf1.format(cl.getTime());

	    //日付の設定
	    CreationHelper createHelper = workbook.getCreationHelper();
	    short daystyle = createHelper.createDataFormat().getFormat("yyyy/mm/dd");
	    style.setDataFormat(daystyle);

	    // タイトル用の行を作成
	    Row row = sheet.createRow(1);
	    // タイトル用のセルを作成
	    Cell cell = row.createCell(4);
	    //タイトル用のセルに値を設定
	    cell.setCellValue("売上書");
	    //行の高さを調整
	    row.setHeightInPoints(40);
	  //セルの結合
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 6));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 4));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 4));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 4));

		//フォント タイトル
		Font fontTitle = workbook.createFont();
		fontTitle.setFontHeightInPoints((short)24);
		fontTitle.setFontName("游ゴシック");

		//縦横 中央揃え、フォントサイズ 24pt
		CellStyle styleTitle = workbook.createCellStyle();
		styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleTitle.setAlignment(HorizontalAlignment.CENTER);
		styleTitle.setFont(fontTitle);

		//縦横 中央揃えと格子 細線
		CellStyle styleCenterGrid = workbook.createCellStyle();
		styleCenterGrid.setVerticalAlignment(VerticalAlignment.CENTER);
		styleCenterGrid.setAlignment(HorizontalAlignment.CENTER);
		styleCenterGrid.setBorderTop(BorderStyle.THIN);
		styleCenterGrid.setBorderBottom(BorderStyle.THIN);
		styleCenterGrid.setBorderLeft(BorderStyle.THIN);
		styleCenterGrid.setBorderRight(BorderStyle.THIN);

		//縦 中央揃えと格子 細線
		CellStyle styleVerticalCenterGrid = workbook.createCellStyle();
		styleVerticalCenterGrid.setVerticalAlignment(VerticalAlignment.CENTER);
		styleVerticalCenterGrid.setBorderTop(BorderStyle.THIN);
		styleVerticalCenterGrid.setBorderBottom(BorderStyle.THIN);
		styleVerticalCenterGrid.setBorderLeft(BorderStyle.THIN);
		styleVerticalCenterGrid.setBorderRight(BorderStyle.THIN);

		//縦 中央揃えと横 左揃え
		CellStyle styleVerticalCenterLeft = workbook.createCellStyle();
		styleVerticalCenterLeft.setVerticalAlignment(VerticalAlignment.CENTER);
		styleVerticalCenterLeft.setAlignment(HorizontalAlignment.LEFT);

		//縦 中央揃えと横 右揃え
		CellStyle styleVerticalCenterRight = workbook.createCellStyle();
		styleVerticalCenterRight.setVerticalAlignment(VerticalAlignment.CENTER);
		styleVerticalCenterRight.setAlignment(HorizontalAlignment.RIGHT);

		//横 中央揃え
		CellStyle styleVerticalCenter = workbook.createCellStyle();
		styleVerticalCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		//上罫線 細線
		CellStyle styleBorderTop = workbook.createCellStyle();
		styleBorderTop.setBorderTop(BorderStyle.THIN);

		//左罫線 細線
		CellStyle styleBorderLeft = workbook.createCellStyle();
		styleBorderLeft.setBorderLeft(BorderStyle.THIN);

		//右罫線 細線
		CellStyle styleBorderRight = workbook.createCellStyle();
		styleBorderRight.setBorderRight(BorderStyle.THIN);

		//下罫線 細線
		CellStyle styleBorderBottom = workbook.createCellStyle();
		styleBorderBottom.setBorderBottom(BorderStyle.THIN);

		//上罫線と左罫線 細線
		CellStyle styleBorderTopLeft = workbook.createCellStyle();
		styleBorderTopLeft.setBorderTop(BorderStyle.THIN);
		styleBorderTopLeft.setBorderLeft(BorderStyle.THIN);


		//上罫線と右罫線 細線
		CellStyle styleBorderTopRight = workbook.createCellStyle();
		styleBorderTopRight.setBorderTop(BorderStyle.THIN);
		styleBorderTopRight.setBorderRight(BorderStyle.THIN);

		//下罫線と左罫線 細線
		CellStyle styleBorderBottomLeft = workbook.createCellStyle();
		styleBorderBottomLeft.setBorderBottom(BorderStyle.THIN);
		styleBorderBottomLeft.setBorderLeft(BorderStyle.THIN);

		//下罫線と右罫線 細線
		CellStyle styleBorderBottomRight = workbook.createCellStyle();
		styleBorderBottomRight.setBorderBottom(BorderStyle.THIN);
		styleBorderBottomRight.setBorderRight(BorderStyle.THIN);

		int forNo = 30;

	    //幅を設定
	    sheet.setColumnWidth(2, 5200);
	    sheet.setColumnWidth(3, 5200);
	    sheet.setColumnWidth(4, 5200);
	    sheet.setColumnWidth(5, 5200);
	    sheet.setColumnWidth(6, 5200);

	  //行を作成 日付や名前を表示するために必要な行数+見積情報数
		for(int no = 0;no <= forNo; no++) {
			row = sheet.createRow(no);

			if(no == 0) {
				continue;
			}
			cell = row.createCell(1);
			cell.setCellStyle(styleBorderLeft);
			cell = row.createCell(7);
			cell.setCellStyle(styleBorderRight);
			if(no<=8) {
				switch(no) {
				//セルを生成し、入力
				case 0:
					continue;
				case 1:
					cell = row.createCell(1);
					cell.setCellStyle(styleBorderTopLeft);
					for(int cellNo = 2; cellNo < 7; cellNo++) {
						cell = row.createCell(cellNo);
						cell.setCellStyle(styleBorderTop);
					}
					cell = row.createCell(7);
					cell.setCellStyle(styleBorderTopRight);
					continue;
				case 2://売上書
					cell = row.createCell(1);
					cell.setCellStyle(styleBorderLeft);
					cell = row.createCell(2);
					cell.setCellStyle(styleTitle);
					cell.setCellValue("売上書");
					cell = row.createCell(7);
					cell.setCellStyle(styleBorderRight);
					continue;
				case 3:
					continue;
				case 4://会社名
					cell = row.createCell(5);
					cell.setCellStyle(styleVerticalCenterRight);
					cell.setCellValue("株式会社グッドハウス");
					continue;
				case 5://日付
					cell = row.createCell(2);
					cell.setCellStyle(styleVerticalCenterLeft);
					cell.setCellValue("作成日 "+today);
					continue;
				case 6:
					continue;
				case 7:
					cell = row.createCell(2);
					cell.setCellStyle(styleVerticalCenterLeft);
					cell.setCellValue("合計金額 \\" + returnSum.get("month_total") + "円");
					continue;
				case 8:
					cell = row.createCell(2);
					cell.setCellStyle(styleCenterGrid);
					cell.setCellValue("No");
					cell = row.createCell(3);
					cell.setCellStyle(styleCenterGrid);
					cell.setCellValue("売上日");
					cell = row.createCell(4);
					cell.setCellStyle(styleCenterGrid);
					cell.setCellValue("商品名");
					cell = row.createCell(5);
					cell.setCellStyle(styleCenterGrid);
					cell.setCellValue("購入数");
					cell = row.createCell(6);
					cell.setCellStyle(styleCenterGrid);
					cell.setCellValue("金額");
					continue;
				}
			}
			if(no-9 < returnList.size()) {
				cell = row.createCell(2);
				cell.setCellValue(no-8);
				cell.setCellStyle(styleVerticalCenterGrid);
				cell = row.createCell(3);
				cell.setCellValue(sdf1.format(returnList.get(no-9).getItem_buy_date()));
				cell.setCellStyle(styleVerticalCenterGrid);
				cell = row.createCell(4);
				cell.setCellValue(returnList.get(no-9).getItem_name());
				cell.setCellStyle(styleVerticalCenterGrid);
				cell = row.createCell(5);
				cell.setCellValue(returnList.get(no-9).getItem_buy_count());
				cell.setCellStyle(styleVerticalCenterGrid);
				cell = row.createCell(6);
				cell.setCellValue(returnList.get(no-9).getTotal_price() +"円");
				cell.setCellStyle(styleVerticalCenterGrid);
				continue;
			}
			//データが無い場合 合計20件格子
			if(no-9 < 20) {
				for(int cellGridNo = 2; cellGridNo < 7; cellGridNo++) {
					cell = row.createCell(cellGridNo);
					cell.setCellStyle(styleVerticalCenterGrid);
				}
				continue;
			}

			if(no == 30) {
				cell = row.createCell(1);
				cell.setCellStyle(styleBorderBottomLeft);
				for(int cellNo = 2; cellNo < 7; cellNo++) {
					cell = row.createCell(cellNo);
					cell.setCellStyle(styleBorderBottom);
				}
				cell = row.createCell(7);
				cell.setCellStyle(styleBorderBottomRight);
				continue;
			}

		}


	  //ファイルの出力
		FileOutputStream output = null;
		String path = "C:\\Users\\Education\\Desktop\\sample\\売上書.xlsx";
			int pathNo = 1;
			//ファイルの存在を確認する
			File file = new File(path);
			while(file.exists()) {
				//存在する場合
				path = "C:\\Users\\Education\\Desktop\\sample\\売上書("+pathNo+").xlsx";
				file = new File(path);
				pathNo++;
			}

			try{
				output = new FileOutputStream(path);
				//書き込み
				workbook.write(output);
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("エラー1");
			}finally {
				try {
					if(workbook!=null) {workbook.close();}
					if(output!=null) {output.close();}
				}catch(Exception ex2) {
					ex2.printStackTrace();
					System.out.println("エラー2");
				}
			}

		return returnList;
	}


	//データベースから売上合計を取得。
	public Map<String, Object> getSalesTotalPrice(String SearchWord) {
		Map<String, Object> returnList = ClientOrderJdbc.getSalesTotalPrice(SearchWord);

		System.out.println(returnList.get("month_total"));

		return returnList;
	}

	
	
	//出荷管理↓
		//出荷確定処理を行う前の確認処理
				public String checkClientOrderNoLogic(int client_order_no,int userNo) {
					String returnText = "";
					int clientOrderLogSize = ClientOrderJdbc.getClientOrderLog("", userNo).size();
					String checkShipmentDate = ClientOrderJdbc.CheckShipmentDue(client_order_no);
					//番号が存在するか確認
			    	if(client_order_no>clientOrderLogSize) {
			    		return "出荷番号が存在しません。";
			    	}

			    	//出荷確定済みか確認
			    	if(checkShipmentDate == null) {
			        	//出荷確定処理を行う
			    		returnText = shipmentFixingLogic(client_order_no);
			    	}else if(checkShipmentDate.equals("出荷済み")){
			    		returnText = "出荷済みです。";
			    	}else {
			    		returnText = "エラーが発生しました。";
			    	}

					return returnText;
				}
				
				//出荷確定処理を行う前の確認処理
				public String checkShipmentDueDateLogic(int client_order_no,String shipment_due_date,int userNo) {
					String returnText = "";
					int clientOrderLogSize = ClientOrderJdbc.getClientOrderLog("", userNo).size();
					String checkShipmentDate = ClientOrderJdbc.CheckShipmentDue(client_order_no);
					//番号が存在するか確認
			    	if(client_order_no>clientOrderLogSize) {
			    		return "番号が存在しません。";
			    	}

			    	//出荷確定済みか確認
			    	if(checkShipmentDate == null) {
			        	//出荷確定処理を行う
			    		returnText = ClientOrderJdbc.shipmentDueDateUpdateJdbc(client_order_no,shipment_due_date);
			    	}else if(checkShipmentDate.equals("出荷済み")){
			    		returnText = "出荷済みです。";
			    	}else {
			    		returnText = "エラーが発生しました。";
			    	}

					return returnText;
				}
				
				
		//出荷確定処理
			public String shipmentFixingLogic(int client_order_no) {
				String returnText = ClientOrderJdbc.shipmentDateUpdateJdbc(client_order_no);
				return returnText;
			}
			
		//出荷日更新処理
			public String shipmentDueDateUpdateLogic(int client_order_no,String shipment_due_date) {
				String returnText = ClientOrderJdbc.shipmentDueDateUpdateJdbc(client_order_no,shipment_due_date);
				return returnText;
			}
			
			

		//どのボタンが押されたか判断
			public String shipmentStateBtnStr(String selectBtn) {
				String returnText = null;
				if(selectBtn.equals("shipped")) {
					returnText = "出荷済みの商品";
					
				}else if(selectBtn.equals("entered")) {
					returnText = "出荷前の商品";
					
				}else if(selectBtn.equals("unentered")) {
					returnText="出荷予定日未入力の商品";
				}
				else{
					returnText = "エラー";
				}
					
				return returnText;
				
			}
			
	//出荷管理↑

	//受注帳票出力
	public String newClientOrderExcel(String searchWord,int userNo) {
			
		    ArrayList<ClientOrderModel> list = ClientOrderJdbc.getClientOrderLog(searchWord, userNo);
		    
			//現在の日付を取得
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			String nowDate = dateFormat.format(date);
			//合計金額を計算
			int COTotalPrice = 0;
			for(ClientOrderModel li: list) {
				COTotalPrice += li.getTotal_price();
			}
			
		  //ワークブックの作成
			Workbook workbook1 = new XSSFWorkbook();
			//シートの作成
			Sheet sheet = workbook1.createSheet("受注情報");
			//目盛線を消す
			sheet.setDisplayGridlines(false);
			//余白
			sheet.setMargin(Sheet.TopMargin, 1.4);    //上マージン
	        sheet.setMargin(Sheet.BottomMargin, 1.4);   //下マージン
	        sheet.setMargin(Sheet.LeftMargin, 0.3);   //左マージン
	        sheet.setMargin(Sheet.RightMargin, 0.3);  //右マージン
	        sheet.setMargin(Sheet.HeaderMargin, 0.3); //ヘッダーマージン
	        sheet.setMargin(Sheet.FooterMargin, 1.3); //フッターマージン
			//列の幅設定
	        sheet.setColumnWidth(0, 200);
	        sheet.setColumnWidth(1, 1000);
	        sheet.setColumnWidth(2, 1200);
	        sheet.setColumnWidth(3, 4500);
	        sheet.setColumnWidth(4, 6000);
	        sheet.setColumnWidth(5, 1500);
	        sheet.setColumnWidth(6, 3000);
	        sheet.setColumnWidth(7, 4000);
	        sheet.setColumnWidth(8, 1000);
	      
			//セルの結合
			sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 6));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 4));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 8));
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 4));
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 4));
			//フォント タイトル
			Font fontTitle = workbook1.createFont();
			fontTitle.setFontHeightInPoints((short)24);
			fontTitle.setFontName("游ゴシック");
			//縦横 中央揃え、フォントサイズ 24pt
			CellStyle styleTitle = workbook1.createCellStyle();
			styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			styleTitle.setAlignment(HorizontalAlignment.CENTER);
			styleTitle.setFont(fontTitle);
			//縦横 中央揃えと格子 細線
			CellStyle styleCenterGrid = workbook1.createCellStyle();
			styleCenterGrid.setVerticalAlignment(VerticalAlignment.CENTER);
			styleCenterGrid.setAlignment(HorizontalAlignment.CENTER);
			styleCenterGrid.setBorderTop(BorderStyle.THIN);
			styleCenterGrid.setBorderBottom(BorderStyle.THIN);
			styleCenterGrid.setBorderLeft(BorderStyle.THIN);
			styleCenterGrid.setBorderRight(BorderStyle.THIN);
			//縦 中央揃えと格子 細線
			CellStyle styleVerticalCenterGrid = workbook1.createCellStyle();
			styleVerticalCenterGrid.setVerticalAlignment(VerticalAlignment.CENTER);
			styleVerticalCenterGrid.setBorderTop(BorderStyle.THIN);
			styleVerticalCenterGrid.setBorderBottom(BorderStyle.THIN);
			styleVerticalCenterGrid.setBorderLeft(BorderStyle.THIN);
			styleVerticalCenterGrid.setBorderRight(BorderStyle.THIN);
			//縦 中央揃えと横 左揃え
			CellStyle styleVerticalCenterLeft = workbook1.createCellStyle();
			styleVerticalCenterLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			styleVerticalCenterLeft.setAlignment(HorizontalAlignment.LEFT);
			//縦 中央揃えと横 右揃え
			CellStyle styleVerticalCenterRight = workbook1.createCellStyle();
			styleVerticalCenterRight.setVerticalAlignment(VerticalAlignment.CENTER);
			styleVerticalCenterRight.setAlignment(HorizontalAlignment.RIGHT);
			//横 中央揃え
			CellStyle styleVerticalCenter = workbook1.createCellStyle();
			styleVerticalCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			//上罫線 細線
			CellStyle styleBorderTop = workbook1.createCellStyle();
			styleBorderTop.setBorderTop(BorderStyle.THIN);
			//左罫線 細線
			CellStyle styleBorderLeft = workbook1.createCellStyle();
			styleBorderLeft.setBorderLeft(BorderStyle.THIN);
			//右罫線 細線
			CellStyle styleBorderRight = workbook1.createCellStyle();
			styleBorderRight.setBorderRight(BorderStyle.THIN);
			//下罫線 細線
			CellStyle styleBorderBottom = workbook1.createCellStyle();
			styleBorderBottom.setBorderBottom(BorderStyle.THIN);
			//上罫線と左罫線 細線
			CellStyle styleBorderTopLeft = workbook1.createCellStyle();
			styleBorderTopLeft.setBorderTop(BorderStyle.THIN);
			styleBorderTopLeft.setBorderLeft(BorderStyle.THIN);
			//上罫線と右罫線 細線
			CellStyle styleBorderTopRight = workbook1.createCellStyle();
			styleBorderTopRight.setBorderTop(BorderStyle.THIN);
			styleBorderTopRight.setBorderRight(BorderStyle.THIN);
			//下罫線と左罫線 細線
			CellStyle styleBorderBottomLeft = workbook1.createCellStyle();
			styleBorderBottomLeft.setBorderBottom(BorderStyle.THIN);
			styleBorderBottomLeft.setBorderLeft(BorderStyle.THIN);
			//下罫線と右罫線 細線
			CellStyle styleBorderBottomRight = workbook1.createCellStyle();
			styleBorderBottomRight.setBorderBottom(BorderStyle.THIN);
			styleBorderBottomRight.setBorderRight(BorderStyle.THIN);
			int forNo = 30;
			//行を作成 日付や名前を表示するために必要な行数+情報数
			for(int no = 0;no <= forNo; no++) {
				Row row = sheet.createRow(no);
				Cell cell;
				if(no == 0) {
					continue;
				}
				cell = row.createCell(1);
				cell.setCellStyle(styleBorderLeft);
				cell = row.createCell(8);
				cell.setCellStyle(styleBorderRight);
				if(no<=9) {
					switch(no) {
					//セルを生成し、入力
					case 0:
						continue;
					case 1:
						cell = row.createCell(1);
						cell.setCellStyle(styleBorderTopLeft);
						for(int cellNo = 2; cellNo < 8; cellNo++) {
							cell = row.createCell(cellNo);
							cell.setCellStyle(styleBorderTop);
						}
						cell = row.createCell(8);
						cell.setCellStyle(styleBorderTopRight);
						continue;
					case 2://タイトル
						cell = row.createCell(1);
						cell.setCellStyle(styleBorderLeft);
						cell = row.createCell(2);
						cell.setCellStyle(styleTitle);
						cell.setCellValue("受注情報");
						cell = row.createCell(8);
						cell.setCellStyle(styleBorderRight);
						continue;
					case 3:
						continue;
					case 4://ユーザー名
						cell = row.createCell(2);
						cell.setCellStyle(styleVerticalCenterLeft);
						cell.setCellValue(userNo+"　　様");
						cell = row.createCell(5);
						cell.setCellStyle(styleVerticalCenterRight);
						cell.setCellValue("株式会社グッドハウス");
						continue;
					case 5://日付
						cell = row.createCell(2);
						cell.setCellStyle(styleVerticalCenterLeft);
						cell.setCellValue("作成日 "+nowDate);
						continue;
					case 6:
						continue;
					case 7:
						cell = row.createCell(2);
						cell.setCellStyle(styleVerticalCenterLeft);
						cell.setCellValue("合計金額　　"+COTotalPrice);
						continue;
					case 8:
						cell = row.createCell(2);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("No");
						cell = row.createCell(3);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("商品名");
						cell = row.createCell(4);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("品番");
						cell = row.createCell(5);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("購入数");
						cell = row.createCell(6);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("金額");
						cell = row.createCell(7);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("受注日");
						continue;
					}
				}
				if(no-9 < list.size()) {
					cell = row.createCell(2);
					cell.setCellValue(no-8);
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(3);
					cell.setCellValue(list.get(no-9).getItem_name());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(4);
					cell.setCellValue(list.get(no-9).getItem_product_no());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(5);
					cell.setCellValue(list.get(no-9).getItem_buy_count());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(6);
					cell.setCellValue(list.get(no-9).getTotal_price());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(7);
					SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
					cell.setCellValue(df.format(list.get(no-9).getItem_buy_date()));
					cell.setCellStyle(styleVerticalCenterGrid);
					continue;
				}
				//データが無い場合 合計20件格子
				if(no-9 < 20) {
					for(int cellGridNo = 2; cellGridNo < 8; cellGridNo++) {
						cell = row.createCell(cellGridNo);
						cell.setCellStyle(styleVerticalCenterGrid);
					}
					continue;
				}
				
				if(no == 30) {
					cell = row.createCell(1);
					cell.setCellStyle(styleBorderBottomLeft);
					for(int cellNo = 2; cellNo < 8; cellNo++) {
						cell = row.createCell(cellNo);
						cell.setCellStyle(styleBorderBottom);
					}
					cell = row.createCell(8);
					cell.setCellStyle(styleBorderBottomRight);
					continue;
				}
				
			}
					
			//ファイルの出力
			FileOutputStream output = null;
			String path = "C:\\Users\\Education\\Documents\\受注情報.xlsx";
				int pathNo = 1;
				//ファイルの存在を確認する
				File file = new File(path);
				while(file.exists()) {
					//存在する場合
					path = "C:\\Users\\Education\\Documents\\受注情報("+pathNo+").xlsx";
					file = new File(path);
					pathNo++;
				}
					
				try{
					output = new FileOutputStream(path);
					//書き込み
					workbook1.write(output);
				}catch(Exception ex){
					ex.printStackTrace();
					System.out.println("エラー1");
				}finally {
					try {
						if(workbook1!=null) {workbook1.close();}
						if(output!=null) {output.close();}
					}catch(Exception ex2) {
						ex2.printStackTrace();
						System.out.println("エラー2");
					}
				}
			
			return "受注情報の保存が完了しました。";
		}

}

