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

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;


@Slf4j
@Controller
@RequestMapping(value = "/backOffice")
public class BackOfficeController {


    @Autowired
    private CustomerRepository repository ;


    @Getter
    @Setter
    class Message {
        private String msg ; 
        public Message(String m) { msg = m ; }
    }


    class DatabaseMessages {
        private ArrayList<Message> messages = new ArrayList<Message>() ;
        public void add( String msg ) { messages.add(new Message(msg) ) ; }
        public ArrayList<Message> getMessage() { return messages ; }
        public void print() {
            for( Message m : messages ) {
                System.out.println( m.msg ) ;
            }
        }
    }


    @GetMapping
    public String getAction(@ModelAttribute("backOffice") StarbucksCard card, 
                            Model model) {
        // DatabaseMessages messages = new DatabaseMessages();
        
        // int x = 1;
        // while(repository.findById(x) != null) {
        //     Customer c = repository.findById(x);
        //     StarbucksCard s = c.getStarbucksCards().get(0);
        //     messages.add("Customer First Name: " + c.getFirstName());
        //     messages.add("Customer Last Name: " + c.getLastName());
        //     messages.add("Starbucks Cards Rewards Points: " + c.getStarbucksCards().get(0).getRewardsPoints());
        //     model.addAttribute("messages", messages.getMessage());
        //     x++;
        // }
        return "backOffice"; 
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("backOffice") StarbucksCard card,  
                            Errors errors, Model model, HttpServletRequest request) {
        
        // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // String username = ((UserDetails)principal).getUsername();
        Customer customer = repository.findById(Integer.parseInt(card.getCustomerIdText()));
        
        StarbucksCard starbucksCard = customer.getStarbucksCards().get(0);
        starbucksCard.setRewardsPoints(Double.parseDouble(card.getBalanceText()));
        repository.save(customer);
        
        DatabaseMessages messages = new DatabaseMessages();
        
        int x = 1;
        while(repository.findById(x) != null) {
            Customer c = repository.findById(x);
            StarbucksCard s = c.getStarbucksCards().get(0);
            messages.add("Customer First Name: " + c.getFirstName());
            messages.add("Customer Last Name: " + c.getLastName());
            messages.add("Starbucks Cards Rewards Points: " + c.getStarbucksCards().get(0).getRewardsPoints());
            model.addAttribute("messages", messages.getMessage());
            x++;
        }
        return "backOffice"; 
    }
}