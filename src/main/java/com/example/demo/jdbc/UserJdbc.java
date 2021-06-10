package com.example.demo.jdbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class UserJdbc {
	 @Autowired
	    protected JdbcTemplate jdbcTemplate;


	    public Map<String, Object> result(String id,String pass, Model model) {
	    	Map<String, Object> list = null;
	    	String message;
	        try {
	    		/*SQL文を実行し、データベースの中にあるユーザーIDとパスワードが一致したものを表示*/
	    		list = jdbcTemplate.queryForMap("SELECT user_no, user_id, password FROM user WHERE user_id = ? AND password = ?", id, pass);

	    	} catch(Exception e) {
	    		  // 取得時にExceptionが発生した場合
		    		message ="ログインに失敗しました";
		    	 	model.addAttribute("message" , message);
	    	}
			return list;
	    }
}
