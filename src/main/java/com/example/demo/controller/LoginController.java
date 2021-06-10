package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.logic.UserLogic;
import com.example.demo.model.LoginModel;

/*ログイン関連*/
@Controller
@SessionAttributes("Login")
public class LoginController {
	String message;
	 @Autowired
	 HttpSession session;

	/*ログイン画面へ遷移*/
    @RequestMapping("/")
    public String index1(Model model) {
    	 if(session.getAttribute("data") != null) {
    		 userlogic.logout();
     	}

    	message ="IDとパスワードを入力してください";
        model.addAttribute("indexForm", new LoginModel());
        model.addAttribute("message" , message);
        return "html/Login";

    }

    /*ログイン画面へ遷移2*/
    @RequestMapping("/Login")
    public String index2(Model model) {
    	if(session.getAttribute("data") != null) {
    		userlogic.logout();
     	}
    	message ="IDとパスワードを入力してください";
        model.addAttribute("indexForm", new  LoginModel());
        model.addAttribute("message" , message);
        return "html/Login";
    }

    @Autowired
    UserLogic userlogic;

	 /*管理者メニュー画面へ遷移*/
   @PostMapping("ManagerMenu")
   String postResult(@RequestParam("username") String id,@RequestParam("password") String pass, Model model) {
	/*logic*/
   	String list =userlogic.post(id,pass,model);
   	return list;

   }

   /*管理者メニュー画面へ遷移*/
   @PostMapping("BackManager")
   public String backmanager(Model model) {
       return "html/ManagerMenu";
   }

    //ログアウトする際にSessionを切る
    @PostMapping(value = "logout")
    public String load() {
     userlogic.logout();

      return "html/Login";
    }


}