package com.example.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

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

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Controller
public class CustomerController {
    
    @Autowired
    private CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

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

    @GetMapping("/joinNow")
    public String getAction( @ModelAttribute("joinNow") Customer customer, 
                            Model model) {
        return "joinNow" ;
    }

    @PostMapping("/joinNow")
    public String postAction(@Valid @ModelAttribute("joinNow") Customer customer,  
                            
                            Errors errors, Model model, HttpServletRequest request) {
        
        //@RequestParam(value="action", required=true) String action,
        //log.info( "Action: " + action ) ;
        log.info( "Command: " + customer ) ;

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;
        System.out.println("hhhhhhh");                       

        if(customer.getFirstName().equals(""))        { hasErrors = true; messages.add("First Name Required"); }
        if(customer.getLastName().equals(""))           { hasErrors = true; messages.add("Last Name Required"); }
        if(customer.getUsername().equals(""))          { hasErrors = true; messages.add("Username Required"); }
        if(customer.getPassword().equals(""))            { hasErrors = true; messages.add("Password Required"); }
    
        if(hasErrors) {
            messages.print();
            model.addAttribute("messages", messages.getMessage());
            System.out.println("No sign up");
            return "joinNow";
        }
        else {
            repository.save(customer);
            System.out.println("Sign up success!");
            model.addAttribute("message", "Sign up success!");
             return "joinNow";
        }
    }


    @GetMapping("/signIn")
    public String signIn( @ModelAttribute("signIn") Customer customer, Model model){
        return "signIn";
    }

    @PostMapping("/signIn")
    public String validateUser(@Valid @ModelAttribute("signIn") Customer customer, Errors errors, Model model, HttpServletRequest request) {
        String username = customer.getUsername();
        String password = customer.getPassword();

        Customer c = repository.findByUsername(username);
        if(password.equals(c.getPassword())){
            System.out.println("Login worked");
        }

        System.out.println(username);
        return "signIn";
    }
}
