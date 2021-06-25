package com.example.demo.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

	    //セルのスタイル設定
	    CellStyle style =  workbook.createCellStyle();

	    Calendar cl = Calendar.getInstance();

	  //日付をyyyy/MM/ddの形で出力する
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
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
	  //中央に調整
	    style.setAlignment(HorizontalAlignment.CENTER);



	    //日付用の行とセルを配置
	    row = sheet.createRow(2);
	    cell = row.createCell(2);
	    cell.setCellValue("日付");
	    cell = row.createCell(3);
	    cell.setCellValue(today);


	    //会社名用の行とセルを配置
	    row = sheet.createRow(3);
	    cell = row.createCell(2);
	    cell.setCellValue("会社名");
	    cell = row.createCell(3);
	    cell.setCellValue("株式会社グッドハウス");

	    //合計金額用の行を作成
	    row = sheet.createRow(5);
	    cell = row.createCell(2);
	    cell.setCellValue("月の合計金額");
	    cell = row.createCell(3);
	    cell.setCellValue(returnSum.get("month_total") + "円");


	    //枠の設定をタイトルへ
	    row.setRowStyle(style);
	    //幅を設定
	    sheet.setColumnWidth(2, 5200);
	    sheet.setColumnWidth(3, 5200);
	    sheet.setColumnWidth(4, 5200);
	    sheet.setColumnWidth(5, 5200);
	    sheet.setColumnWidth(6, 5200);

	    //レコード用の行を作成
	    row = sheet.createRow(6);
	    // セルを作成

	    cell = row.createCell(2);
	    cell.setCellValue("No");


	    cell = row.createCell(3);
	    cell.setCellValue("売上日");

	    cell = row.createCell(4);
	    cell.setCellValue("商品名");

	    cell = row.createCell(5);
	    cell.setCellValue("数量");

	    cell = row.createCell(6);
	    cell.setCellValue("金額");
	    		int x=0;
		    for(ClientOrderModel model: returnList) {
		    	x++;

		    	//中身用の行を作成
		    	row = sheet.createRow(x+6);
		    	cell = row.createCell(2);
		    	cell.setCellValue(x);

		    	cell = row.createCell(3);
		    	cell.setCellValue(sdf1.format(model.getItem_buy_date()));

		    	cell = row.createCell(4);
		    	cell.setCellValue(model.getItem_name());

		    	cell = row.createCell(5);
		    	cell.setCellValue(model.getItem_buy_count());

		    	cell = row.createCell(6);
		    	cell.setCellValue(model.getTotal_price() +"円");

			    System.out.println(model.getItem_buy_date());
			    System.out.println(model.getItem_name());
			    System.out.println(model.getItem_buy_count());
			    System.out.println(model.getTotal_price());
		    }

	  //ファイルの出力
		FileOutputStream output = null;
		String path = "C:\\Users\\Education\\Desktop\\売上書.xlsx";
			int pathNo = 1;
			//ファイルの存在を確認する
			File file = new File(path);
			while(file.exists()) {
				//存在する場合
				path = "C:\\Users\\Education\\Desktop\\売上書("+pathNo+").xlsx";
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


}


