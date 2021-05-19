package com.example.springcashier;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*; 
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;



import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;


@Slf4j
@Controller
public class CreditCardController {


    @Autowired
    private CustomerRepository repository ;


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


    private static Map<String, String> months = new HashMap<>();
    static {
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");        
    }     


    @GetMapping({"/paymentmethod"})
    public String getAction( @ModelAttribute("command") CreditCard command, 
                             //@PathVariable String id,
                             Model model) {
        return "paymentmethod";
    }


    @PostMapping({"/paymentmethod"})
    public String postAction(@Valid @ModelAttribute("command") CreditCard command,  
                             //@PathVariable String id,
                             Errors errors, Model model, HttpServletRequest request) {
        
        //@RequestParam(value="action", required=true) String action,
        //log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;


        if(command.getCardnum().equals(""))        { hasErrors = true; messages.add("Credit Card Number Required"); }
        if(command.getCardexpmon().equals(""))     { hasErrors = true; messages.add("Credit Card Expiration Month Required"); }
        if(command.getCardexpyear().equals(""))    { hasErrors = true; messages.add("Credit Card Expiration Year Required"); }
        if(command.getCardcvv().equals(""))        { hasErrors = true; messages.add("Credit Card CVV Required"); }
        if(!command.getCardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))       { hasErrors = true; messages.add("Invalid Credit Card Number"); }
        if(!command.getCardexpyear().matches("\\d{4}"))                        { hasErrors = true; messages.add("Invalid Credit Card Expiration Year"); }
        if(!command.getCardcvv().matches("\\d{3}"))                            { hasErrors = true; messages.add("Invalid Credit Card CVV"); }
        if(months.get(command.getCardexpmon()) == null)   {hasErrors = true; messages.add("Invalid Card Expiration Month"); }


        if(hasErrors) {
            messages.print();
            model.addAttribute("messages", messages.getMessage());
            return "paymentmethod";
        }
        else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails)principal).getUsername();
            Customer c = repository.findByUsername(username);
            c.getCreditCards().add(command);
            repository.save(c);
            System.out.println("Thank You for your Payment!");
            model.addAttribute("message", "Thank You for your Payment!");
        }
    

        return "paymentmethod";
    }


    public String currentUserName(Principal principal) {
        return principal.getName();
    }
}