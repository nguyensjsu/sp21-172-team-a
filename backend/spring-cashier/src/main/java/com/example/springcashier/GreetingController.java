package com.example.springcashier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@RequestMapping(value = "/cards")
   public String goCards() {
      return "cards";
   }

   @RequestMapping(value = "/index")
   public String goIndex() {
      return "index";
   }

   @RequestMapping(value = "/home")
   public String goHome() {
      return "home";
   }

   @RequestMapping(value = "/signIn")
   public String goSignIn() {
      return "signIn";
   }

   @RequestMapping(value = "/joinNow")
   public String goJoinNow() {
      return "joinNow";
   }

   @RequestMapping(value = "/paymentmethod")
   public String goPaymentMethod() {
      return "paymentmethod";
   }

   @RequestMapping(value = "/starbucksmenu")
   public String goStarbucksMenu() {
      return "starbucksmenu";
   
      @RequestMapping(value = "/userinfo")
   public String goUserInfo() {
      return "userinfo";
   }
}