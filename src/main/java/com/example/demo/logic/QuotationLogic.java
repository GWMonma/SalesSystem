package com.example.demo.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

import com.example.demo.jdbc.ItemJdbc;
import com.example.demo.jdbc.QuotationJdbc;
import com.example.demo.model.InventoryModel;
import com.example.demo.model.QuotationModel;


@Service
public class QuotationLogic {
	 @Autowired
	 ItemJdbc itemJdbc;
	 
	 @Autowired
	 QuotationJdbc quotationJdbc;
	 
	 @Autowired
	 HttpSession session;
	 
	//データベースに見積情報を保存する
	public String quotationSaveLogic(int itemNo, int itemBuyCount) {
		Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
		int userNo = (int) list.get("user_no");
		//既に保存されているか確認
		ArrayList<Integer> returnList = quotationJdbc.quotationCheck(userNo, itemNo);
		//20件以上は保存できないように設定するため、件数を取得
		ArrayList<InventoryModel> searchNoList = itemJdbc.getInventoryLog("");
		int quotationListNo = quotationJdbc.quotationSearchList(userNo, searchNoList).size();
		System.out.println("件数："+quotationListNo);
		if(returnList.get(0)==-1 && quotationListNo < 20) {//保存されていない場合
			return quotationJdbc.quotationSave(userNo, itemBuyCount, itemNo);
		
		}else if(returnList.get(0)==-1 && quotationListNo == 20) {
			return "見積情報を20件以上保存することはできません。";
			
		}else{//保存されている場合
			int itemStock = itemJdbc.getItemData(itemNo).getItemStock();
			
			if(itemStock<returnList.get(0)+itemBuyCount) {//在庫数を上回っている場合
				return "既に保存されている購入数に加算した結果、購入希望数が在庫数を超えています。";
			
			}else{
				return quotationJdbc.quotationUpdate(returnList.get(1), userNo, (returnList.get(0)+itemBuyCount));
			}
		}
	}
	
	//合計金額を計算
		public int getQuotationTotalPrice(int itemNo, int itemBuyCount) {
			InventoryModel itemData = itemJdbc.getItemData(itemNo);
			int returnTotalPrice = -10;
			//計算に失敗した場合
			if(itemData==null) {
				returnTotalPrice = -1;
			}else{
				returnTotalPrice = itemData.getItemPrice()*itemBuyCount;
			}
			return returnTotalPrice;
		}
	
	//見積情報を検索
		public ArrayList<QuotationModel> quotationSearchListLogic(String... searchWord){
			ArrayList<InventoryModel> searchNoList = new ArrayList<InventoryModel>();
			//ユーザー番号を取得
			ArrayList<QuotationModel> returnList = new ArrayList<QuotationModel>();
			Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
			int userNo = (int) list.get("user_no");
				if(searchWord.length>0) {
					//品番取得、合計金額計算
					searchNoList = itemJdbc.getInventoryLog(searchWord[0]);
					returnList = quotationJdbc.quotationSearchList(userNo, searchNoList);
				}else if(searchWord.length==0){
					//全件取得
					searchNoList = itemJdbc.getInventoryLog("");
					returnList = quotationJdbc.quotationSearchList(userNo, searchNoList);
				}
			return returnList;
		}
		
	//見積情報を削除
		public String quotationDeleteLogic(int quotationNo) {
			String returntext = quotationJdbc.quotationDelete(quotationNo);
			return returntext;
		}
		
	//見積書を出力
		public String QuotationOutputLogic() {
			//ユーザー番号を取得
			Map<String, Object> list = (Map<String, Object>) session.getAttribute("data");
			int userNo = (int) list.get("user_no");
			//見積情報を取得
			ArrayList<InventoryModel> searchNoList = itemJdbc.getInventoryLog("");
			ArrayList<QuotationModel> quotationList = quotationJdbc.quotationSearchList(userNo, searchNoList);
			//現在の日付を取得
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			String nowDate = dateFormat.format(date);
			//合計金額を計算
			int QuotationTotalPrice = 0;
			for(QuotationModel quotationData: quotationList) {
				QuotationTotalPrice += quotationData.getTotalPrice();
			}
			//ワークブックの作成
			Workbook workbook1 = new XSSFWorkbook();
			//シートの作成
			Sheet sheet = workbook1.createSheet("見積書");
			//セルの線を削除
			sheet.setDisplayGridlines(false);
			//幅を設定
			sheet.setColumnWidth(2,2600);
			sheet.setColumnWidth(3,5200);
			sheet.setColumnWidth(4,5200);
			sheet.setColumnWidth(5,2600);
			sheet.setColumnWidth(6,5200);
			//セルの結合
			sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 6));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 4));
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));
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
			//行を作成 日付や名前を表示するために必要な行数+見積情報数
			for(int no = 0;no <= forNo; no++) {
				Row row = sheet.createRow(no);
				Cell cell;
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
					case 2://見積書
						cell = row.createCell(1);
						cell.setCellStyle(styleBorderLeft);
						cell = row.createCell(2);
						cell.setCellStyle(styleTitle);
						cell.setCellValue("見積書");
						cell = row.createCell(7);
						cell.setCellStyle(styleBorderRight);
						continue;
					case 3:
						continue;
					case 4://ユーザー名
						cell = row.createCell(2);
						cell.setCellStyle(styleVerticalCenterLeft);
						cell.setCellValue(list.get("user_id")+"様");
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
						cell.setCellValue("合計金額 \\"+QuotationTotalPrice);
						continue;
					case 8:
						cell = row.createCell(2);
						cell.setCellStyle(styleCenterGrid);
						cell.setCellValue("見積番号");
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
						continue;
					}
				}
				if(no-9 < quotationList.size()) {
					cell = row.createCell(2);
					cell.setCellValue(no-8);
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(3);
					cell.setCellValue(quotationList.get(no-9).getItemName());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(4);
					cell.setCellValue(quotationList.get(no-9).getItemProductNo());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(5);
					cell.setCellValue(quotationList.get(no-9).getItemBuyCount());
					cell.setCellStyle(styleVerticalCenterGrid);
					cell = row.createCell(6);
					cell.setCellValue(quotationList.get(no-9).getTotalPrice());
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
			String path = "C:\\Users\\Education\\Desktop\\sample\\Quotation.xlsx";
				int pathNo = 1;
				//ファイルの存在を確認する
				File file = new File(path);
				while(file.exists()) {
					//存在する場合
					path = "C:\\Users\\Education\\Desktop\\sample\\Quotation("+pathNo+").xlsx";
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
			
			return "見積書の保存が完了しました。";
		}

}
