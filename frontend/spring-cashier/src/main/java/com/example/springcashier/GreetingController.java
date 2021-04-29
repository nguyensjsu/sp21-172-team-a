package com.example.springcashier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping(value = "/cards")
   public String index() {
      return "cards";
   }

//    @RequestMapping(value = "/index")
//    public String indexx() {
//       return "cards";
//    }

//    @RequestMapping(value = "/home")
//    public String indexxx() {
//       return "cards";
//    }
}