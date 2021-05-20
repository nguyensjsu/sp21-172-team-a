package com.example.springcashier;

import java.util.List;
import java.util.HashMap;

import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Controller
public class OrderController{
    
    @Autowired
    private OrderRepository ordersRepository;

    private HashMap<Integer, Order> orders = new HashMap<>();

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

    @GetMapping("/index")
    public String getAction( @ModelAttribute("order") Order order,
                            Model model) {
        Random random = new Random();
        int x = random.nextInt(99999999) + 10000000;
        order.setId(x);
        System.out.println(order.getId());

        return "index" ;
    }
    
    // @DeleteMapping
    // void deleteOrder(){
    //     repository.deleteById(order.getId());
    // }

    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    public String postAction(@PathVariable("regid") int regid, HttpServletResponse response, @Valid @ModelAttribute("order") Order order,  
                            
                            Errors errors, Model model, HttpServletRequest request) {

        System.out.println("Placing order (Reg ID = " + regid + ") => " + order);

        if(order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request!");
        }

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;

        if(order.getDrink().equals("")) { hasErrors = true; messages.add("Drink Required"); }
        if(order.getMilk().equals("")) { hasErrors = true; messages.add("Milk type Required"); }
        if(order.getSize().equals("")) { hasErrors = true; messages.add("Size Required"); }
        if(order.getStore().equals("0")) { hasErrors = true; messages.add("Store Required"); }

        Order active = orders.get(regid);
        if(active!=null){
            System.out.println("Active order (Reg ID = " + regid + ") => " + active);
            if(active.getStatus().equals("Paid."))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active order exists!");
        }

        /* Price Calculation*/
        double price = 5.0;
        switch(order.getMilk()) {
            case("Oat"):
                price += 0.5;
                break;
            case("Almond"):
                price += 0.5;
                break;
        }
        switch(order.getSize()) {
            case("Tall"):
                price += 0.5;
                break;
            case("Grande"):
                price += 1.0;
                break;
            case("Venti"):
                price += 1.5;
                break;
        }

        log.info("Order: " + order);

        if(hasErrors) {
            messages.print();
            model.addAttribute("message", "There was an error with the order.");
        }
        else {
            double tax = 0.0725;
            double total = price + (price*tax);          
            double scale = Math.pow(10,2);
    
            double rounded = Math.round(total*scale)/scale;
            order.setPrice(rounded);
    
            order.setStatus("Paid.");
            order.setId(regid);
            // order.setCustomer(new Customer("John", "Doe", "jdoe", "Agikuej#422"));
            Order new_order = ordersRepository.save(order);
            orders.put(regid, new_order);
            System.out.println(new_order);

            order.getCustomer().getStarbucksCards().get(0).addRewardsPoints(1);
            order.getCustomer().setTotalOrders(order.getCustomer().getTotalOrders() + 1);
            if(order.getCustomer().getStarbucksCards().get(0).getRewardsPoints() % 5 == 0)
            {
                order.setPrice(order.getPrice() - order.getCustomer().getStarbucksCards().get(0).getRewardsPoints()/5);
                order.getCustomer().getStarbucksCards().get(0).setRewardsPoints(0);            
            }
            
            // mostRecentOrderId = order.getId();
            log.info("Order placed.");
            // model.addAttribute("message", "Order placed.");
        }
        return "index";
    }
    
    /*Rest API*/
    // @GetMapping("/orders")
    // @ResponseBody
    // List<Order> all(){
    //     return ordersRepository.findAll();
    // }

    @GetMapping("/order/register/{regid}")
    @ResponseBody
    Order getActiveOrder(@PathVariable String regid, HttpServletResponse response){
        Order active = orders.get(regid);
            if(active != null){
                return active;
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
            }
    }

    @DeleteMapping("/order/delete/{regid}")
    String deleteActiveOrder(@PathVariable String regid){
        Order active = orders.get(regid);
        if(active != null){
            orders.remove(regid);
            System.out.println("Active order cleared!");
            return "index";
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
        }
    }

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
