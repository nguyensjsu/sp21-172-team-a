package com.example.springcashier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.*;


import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import java.security.Principal;

@Controller
public class GreetingController {

	@RequestMapping(value = "/cards")
   public String goCards() {
      return "cards";
   }

//    @RequestMapping(value = "/index")
//    public String goIndex() {
//       return "index";
//   }

   @GetMapping("/")
   public String displayHomePage(Model model, Principal user) {
      if (user != null) {
         return "homepageUser";
      } else {
         return "homepage";
      }
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
   }

   @RequestMapping(value = "/billinginfo")
   public String goBillingInfo() {
      return "billinginfo";
   }
}