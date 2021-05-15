package com.example.cashierbackend;
import java.util.Arrays;
import java.util.List;

// import javax.servlet.http.HttpServletRequest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.Errors;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;


// @Controller
// public class MenuController {
//     @Autowired
//     private OrderRepository repository;
    


    /*
	@GetMapping
	public String greeting( @ModelAttribute("order") Order order, Model model) {
        		return "starbucksmenu";
    }
    */


     
//   List<String> sizeList = Arrays.asList("Short", "Tall", "Grande", "Venti");

//     @GetMapping()
//     public String showForm(@ModelAttribute("order") Order order, String action, Errors errors, Model model, HttpServletRequest request) {
//         model.addAttribute("order", order);

//         List<String> sizeList = Arrays.asList("Short", "Tall", "Grande", "Venti");
//         model.addAttribute("sizeList", sizeList);
//         return "starbucksmenu";
//     }

//     @PostMapping("/order")
//     public String submitForm(@ModelAttribute("order") Order order){
//         System.out.println(order);
//         String drink = order.getDrink();
//         boolean milk = order.getMilk();
//         String size = order.getSize();
//         order.setDrink(drink);
//         order.setMilk(milk);
//         order.setSize(size);
//         order.setPrice(10);
//         repository.save(order);
        
//         return "starbucksmenu";
//     }
// }
