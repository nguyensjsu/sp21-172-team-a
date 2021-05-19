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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Controller
@RequestMapping(value = {"/joinNow"})
public class CustomerController{


    @Autowired
    private CustomerRepository customersRepository;

    public static int loggedInCustomerId;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public CustomerController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
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
    

    @GetMapping
    public String getAction( @ModelAttribute("customer") Customer customer,
                            Model model) {
        return "joinNow" ;
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("customer") Customer customer,  
                            
                            Errors errors, Model model, HttpServletRequest request) {

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;

        if(customer.getFirstName().equals(""))      { hasErrors = true; messages.add("First Name Required"); }
        if(customer.getLastName().equals(""))      { hasErrors = true; messages.add("Last Name Required"); }
        if(customer.getUsername().equals(""))      { hasErrors = true; messages.add("Username Required"); }
        if(customer.getPassword().equals(""))      { hasErrors = true; messages.add("Password Required"); }
        if(!customer.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) { hasErrors = true; messages.add("Password must have a minimum of eight characters, at least one letter, one number and one special character."); }

        if(hasErrors) {
            messages.print();
            model.addAttribute("message", "Please provide valid input for all of the fields.");
            return "joinNow";

        }
        else {
            customersRepository.save(customer);
            loggedInCustomerId = customer.getId();
            System.out.println(loggedInCustomerId);
            customer.getStarbucksCards().add(new StarbucksCard(loggedInCustomerId, 0, 0));
            customersRepository.save(customer);
            log.info("User account created.");
            inMemoryUserDetailsManager.createUser(User.withUsername(customer.getUsername()).password("{noop}" + customer.getPassword()).roles("USER").build());
            return "homepage";
        }
    }


    public static void setLoggedInCustomerId(int id) {
        loggedInCustomerId = id;
    }
}
