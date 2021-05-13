package com.example.springcashier;

import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping(value = {"/", "/index"})
public class OrderController{
    @Autowired
    private OrderRepository ordersRepository;

    @Getter
    @Setter
    class Message {
        private String msg ; 
        public Message(String m) { msg = m ; }
    }


    class ErrorMessages {
        private ArrayList<Message> messages = new ArrayList<Message>() ;
        public void add( String msg ) { messages.add(new Message(msg) ) ; }
        public ArrayList<Message> getMessage() { return messages ; }
        public void print() {
            for( Message m : messages ) {
                System.out.println( m.msg ) ;
            }
        }
    }

    // @GetMapping
    // public String main(Model model) {
    //     model.addAttribute("order", new Order());
    //     return "home";
    // }

    @GetMapping
    public String getAction( @ModelAttribute("order") Order order,
                            Model model) {
  
        /* Render View */
        // model.addAttribute( "message", "Hello World!" ) ;
        // model.addAttribute("order", new Order());

        return "index" ;

    }

    
    // @PostMapping
    // public String postAction(@ModelAttribute("order") Order command,  
                            
    //                         Model model, HttpServletRequest request) {
    //     return "saved";
    // }

    @PostMapping
    public String postAction(@ModelAttribute("order") Order order,  
                            
                            Errors errors, Model model, HttpServletRequest request) {

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;

        if(order.getDrinkModel().equals("")) { hasErrors = true; messages.add("Drink Required"); }
        if(order.getMilk().equals("")) { hasErrors = true; messages.add("Milk type Required"); }
        if(order.getSize().equals("")) { hasErrors = true; messages.add("Size Required"); }

        if(hasErrors) {
            messages.print();
            model.addAttribute("order", "There was an error with the order.");
            return "index";
        }
        else {
            ordersRepository.save(order);
            model.addAttribute("order", "Order placed.");
        }
        return "index";
    }

    // @PostMapping
    // public String save(Order order, Model model) {
    //     model.addAttribute("order", order);
    //     return "index";
    // }
    

    // @GetMapping("/orders")
    // List<Order> all(){
    //     return repository.findAll();
    // }

    // @GetMapping("/order/register/{regid}")
    //     Order getActiveOrder(@PathVariable String regid, HttpServletResponse response){
    //         StarbucksOrder active = orders.get(regid);
    //             if(active != null){
    //                 return active;
    //             }
    //             else{
    //                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
    //             }
    //     }

    // @DeleteMapping("/orders")
    // void deleteAll(){
    //     repository.deleteAllInBatch();
    //     orders.clear();
    // }

    // @PostMapping("/order/register/{regid}")
    // @ResponseStatus(HttpStatus.CREATED)
    // Order newOrder(@PathVariable String regid, @RequestBody Order order) {
    //     System.out.println("Placing order (Reg ID = " + regid + ") => " + order);

    //     if(order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request!");
    //     }

    //     Order active = orders.get(regid);
    //     if(active!=null){
    //         System.out.println("Active order (Reg ID = " + regid + ") => " + active);
    //         if(active.getStatus().equals("Ready for Payment."))
    //             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active order exists!");
    //     }

    //     double price = 0.0;
    //     switch(order.getDrink()){
    //     case "Caffe Latte":
    //         switch(order.getSize()){
    //             case "Tall":
    //                 price=2.95;
    //                 break;
    //             case "Grande":
    //                 price=3.65;
    //                 break;
    //             case "Venti":
    //             case "Your Own Cup":
    //                 price=3.95;
    //                 break;
    //             default:
    //                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
    //         }
    //         break;
    //     case "Caffe Americano":
    //         switch(order.getSize()){
    //             case "Tall":
    //                 price=2.25;
    //                 break;
    //             case "Grande":
    //                 price=2.65;
    //                 break;
    //             case "Venti":
    //             case "Your Own Cup":
    //                 price=2.95;
    //                 break;
    //             default:
    //                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
    //         }
    //         break;
    //     case "Caffe Mocha":
    //         switch(order.getSize()){
    //             case "Tall":
    //                 price=3.45;
    //                 break;
    //             case "Grande":
    //                 price=4.15;
    //                 break;
    //             case "Venti":
    //             case "Your Own Cup":
    //                 price=4.45;
    //                 break;
    //             default:
    //                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
    //         }
    //         break;
    //         default:
    //             System.out.println(order);
    //             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink!");
    //     }

    //     double tax = 0.0725;
    //     double total = price + (price*tax);
    //     double scale = Math.pow(10,2);

    //     double rounded = Math.round(total*scale)/scale;
    //     order.setTotal(rounded);

    //     order.setStatus("Ready for Payment.");
    //     Order new_order = repository.save(order);
    //     orders.put(regid, new_order);
    //     System.out.println(new_order);
    //     return new_order;
    // }

    // @DeleteMapping("/order/register/{regid}")
    // Message deleteActiveOrder(@PathVariable String regid){
    //     Order active = orders.get(regid);
    //     if(active != null){
    //         orders.remove(regid);
    //         Message msg = new Message();
    //         msg.setStatus("Active order cleared!");
    //         return msg;
    //     }
    //     else{
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
    //     }
    // }

    // @PostMapping("/order/register/{regid}/pay/{cardnum}")
    // CreditCard processOrder(@PathVariable String regid, @PathVariable String cardnum){
    //     System.out.println("Pay for order (Reg ID = " + regid + " Using Card = " + cardnum);

    //     Order active = orders.get(regid);
    //     if(active==null){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not Found!");
    //     }
    //     if (cardnum.equals("")){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number not provided!");
    //     }

    //     if(active.getStatus().startsWith("Paid with Card")){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clear paid active order!");
    //     }

    //     CreditCard card = cardsRepository.findByCardNumber(cardnum);
    //     if(card == null){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card not Found!");
    //     }

    //     if(!card.isActivated()){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card not activated!");
    //     }

    //     double price = active.getTotal();
    //     double balance = card.getBalance();
    //     if(balance - price < 0){
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds on Card.");
    //     }

    //     double new_balance = balance - price;
    //     card.setBalance(new_balance);
    //     String status="Paid with Card: " + cardnum + " Balance: $" + new_balance + ".";
    //     active.setStatus(status);
    //     cardsRepository.save(card);
    //     repository.save(active);
    //     return card;
    // }
}
