package com.example.demo;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("Login")
public class LoginController {
	String message;
	 @Autowired
	 HttpSession session;

	/*ログイン画面へ遷移*/
    @RequestMapping("/")
    public String index1(Model model) {
    	message ="IDとパスワードを入力してください";
        model.addAttribute("indexForm", new Pass());
        model.addAttribute("message" , message);
        return "Login";
    }

    /*ログイン画面へ遷移2*/
    @RequestMapping("/Login")
    public String index2(Model model) {
    	message ="IDとパスワードを入力してください";
        model.addAttribute("indexForm", new  Pass());
        model.addAttribute("message" , message);
        return "Login";
    }

    /*Jdbcテンプレート*/
	@Autowired
	JdbcTemplate jdbcTemplate;

	 /*ログイン完了画面へ遷移*/
    @PostMapping("ManagerMenu")
    String postResult(@RequestParam("username") String id,@RequestParam("password") String pass, Model model) {
    	/*データベースの中に*/
    	Map<String,Object>list = null;
    	try {
    		/*SQL文を実行し、データベースの中にあるユーザーIDとパスワードが一致したものを表示*/
    		list = jdbcTemplate.queryForMap("SELECT user_no, user_id, password FROM user WHERE user_id = ? AND password = ?", id, pass);

    	} catch(Exception e) {
    		  // 取得時にExceptionが発生した場合
	    		message ="ログインに失敗しました";
	    	 	model.addAttribute("message" , message);
	       		return "Login";
    	}

    	/*一致している場合は管理者画面へ*/
    	if(list != null) {
    		// セッションでユーザーデータを保存
    	    session.setAttribute("data", list);
    	    System.out.println(session);
    	    System.out.println("セッションが開始しました！");
    		message ="ログインしました！";
    		 model.addAttribute("message" , message);
    		 return "managermenu";
    	}else {
    		// 例外処理以外
    		message ="ログインに失敗しました";
    	 	model.addAttribute("message" , message);
       		return "Login";
    	}
    }

    //ログアウトする際にSessionを切る
    @PostMapping(value = "logout")
    public String load() {
      session.getAttribute("data");  // ログイン中のユーザー情報を取得
      System.out.println( session.getAttribute("data"));
      session.invalidate(); // セッション情報の削除

      //セッション情報を確認
      if(session.getAttribute("data") == null) {
    	  System.out.println("セッションが終了しました");
      }else {
          System.out.println("セッションが終了してません！");
      }
      return "Login";
    }

    /*販売管理画面へ遷移*/
    @RequestMapping("MarketingManagement")
    public String menu(Model model) {

        return "MarketingManagement";
    }

    /*購買管理画面へ遷移*/
    @RequestMapping("PurchaseManagement")
    public String purchase(Model model) {

        return "PurchaseManagement";
    }

    /*在庫管理画面へ遷移*/
    @RequestMapping("InventoryManagement")
    public String inventory(Model model) {

        return "InventoryManagement";
    }

}