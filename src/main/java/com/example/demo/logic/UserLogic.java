package com.example.demo.logic;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.jdbc.UserJdbc;


@Service
public class UserLogic {

	String message;
	 @Autowired
	 HttpSession session;
	 @Autowired
	    protected JdbcTemplate jdbcTemplate;

	 @Autowired
	 UserJdbc userjdbc;

	    public String post(String id,String pass, Model model) {
	    	Map<String, Object> list =userjdbc.result(id,pass,model);
	    	/*一致している場合は管理者画面へ*/
	       	if(list != null) {
	       		// セッションでユーザーデータを保存
	       	    session.setAttribute("data", list);
	       	    System.out.println(session);
	       	    System.out.println("セッションが開始しました！");
	       		message ="ログインしました！";
	       		 model.addAttribute("message" , message);
	       		 // 管理者メニューへ
	       		 return "managermenu";
	       	}else {
	       		message ="ログインに失敗しました";
	       	 	model.addAttribute("message" , message);
       	 		// ログイン画面へ
          		return "Login";
	       	}

	    }


	    public void logout() {
	    	session.getAttribute("data");  // ログイン中のユーザー情報を取得
	        System.out.println( session.getAttribute("data"));
	        session.invalidate(); // セッション情報の削除

	        //セッション情報を確認
	        if(session.getAttribute("data") == null) {
	      	  System.out.println("セッションが終了しました");
	        }else {
	            System.out.println("セッションが終了してません！");
	        }
			return;

	    }
}
