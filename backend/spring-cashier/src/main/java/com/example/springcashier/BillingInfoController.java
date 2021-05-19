package com.example.springcashier;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
@Controller
public class BillingInfoController {


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


    private static Map<String, String> states = new HashMap<>();
    static {
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("DC", "District of Columbia");
        states.put("FL", "Florida");
        states.put("FO", "Foljava");
        states.put("GA", "Georgia");
        states.put("GU", "Guam");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("MP", "Northern Mariana");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("PR", "Puerto Rico");
        states.put("RI", "Rhode Island");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennesse");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("VI", "Virgin Islands");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
    }      


    @GetMapping("/billinginfo")
    public String getAction( @ModelAttribute("billingInfo") BillingInfo billingInfo, 
                            Model model) {
        return "billinginfo" ;
    }

    @GetMapping("/billing")
    @ResponseBody
    BillingInfo getOne(HttpServletResponse response) {
        BillingInfo billingInfoAPI = repository.findById(CustomerController.loggedInCustomerId).getBillingInfos().get(0);
        if(billingInfoAPI == null)
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
        return billingInfoAPI;
    }

    @PostMapping("/billinginfo")
    public String postAction(@Valid @ModelAttribute("billingInfo") BillingInfo billingInfo,  
                            
                            Errors errors, Model model, HttpServletRequest request) {
        
        //@RequestParam(value="action", required=true) String action,
        //log.info( "Action: " + action ) ;
        log.info( "Command: " + billingInfo ) ;

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;


        if(billingInfo.getAddress().equals(""))        { hasErrors = true; messages.add("Address Required"); }
        if(billingInfo.getCity().equals(""))           { hasErrors = true; messages.add("City Required"); }
        if(billingInfo.getState().equals(""))          { hasErrors = true; messages.add("State Required"); }
        if(billingInfo.getZip().equals(""))            { hasErrors = true; messages.add("Zip Required"); }
        if(billingInfo.getPhone().equals(""))          { hasErrors = true; messages.add("Phone Number Required"); }
        if(billingInfo.getEmail().equals(""))          { hasErrors = true; messages.add("Email Required"); }
        if(!billingInfo.getZip().matches("\\d{5}"))                                { hasErrors = true; messages.add("Invalid Zip Code"); }
        if(!billingInfo.getPhone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}"))          { hasErrors = true; messages.add("Invalid Phone Number"); }
        if(states.get(billingInfo.getState()) == null)        {hasErrors = true; messages.add("Invalid State"); }


        if(hasErrors) {
            messages.print();
            model.addAttribute("messages", messages.getMessage());
            return "billinginfo";
        }
        else {
            Customer c = repository.findById(CustomerController.loggedInCustomerId);
            c.getBillingInfos().add(billingInfo);
            repository.save(c);
            System.out.println("Billing Information Updated!");
            System.out.println(CustomerController.loggedInCustomerId);
            model.addAttribute("message", "Billing Information Updated!");
            return "billinginfo";
        }
    }


    // @GetMapping("/billinginfo/get")
    // @ResponseBody
    // BillingInfo getBillingInfo() {
        
    //         BillingInfo billingInfo = new BillingInfo("address", "city", "CA", "12345", "(408) 270-1510", "john@example.com");
    //         ArrayList<BillingInfo> list = new ArrayList<BillingInfo>();
    //         list.add(billingInfo);
    //         Customer c = new Customer("John2", null, null, null, null, 0, 0, list, null, null);
    //         repository.save(c);
    //         return billingInfo;
   
    // }


    // @PostMapping("/billinginfo/create")
    // @ResponseBody
    // BillingInfo newBillingInfo() {
        
    //         BillingInfo billingInfo = new BillingInfo("address", "city", "CA", "12345", "(408) 270-1510", "john@example.com");
    //         ArrayList<BillingInfo> list = new ArrayList<BillingInfo>();
    //         list.add(billingInfo);
    //         Customer c = new Customer("John2", null, null, null, null, 0, 0, list, null, null);
    //         repository.save(c);
    //         return billingInfo;
   
    // }


    // @PostMapping("/billinginfo/{address}/{city}/{state}/{zip}/{phone}/{email}")
    // @ResponseBody
    // BillingInfo newBillingInfo(@PathVariable String address, @PathVariable String city, @PathVariable String state,
    //                            @PathVariable String zip, @PathVariable String phone, @PathVariable String email, @RequestBody BillingInfo billingInfo){
        
    //     ErrorMessages messages = new ErrorMessages();
    //     boolean hasErrors = false;


    //     if(billingInfo.getAddress().equals(""))        { hasErrors = true; messages.add("Address Required"); }
    //     if(billingInfo.getCity().equals(""))           { hasErrors = true; messages.add("City Required"); }
    //     if(billingInfo.getState().equals(""))          { hasErrors = true; messages.add("State Required"); }
    //     if(billingInfo.getZip().equals(""))            { hasErrors = true; messages.add("Zip Required"); }
    //     if(billingInfo.getPhone().equals(""))          { hasErrors = true; messages.add("Phone Number Required"); }
    //     if(billingInfo.getEmail().equals(""))          { hasErrors = true; messages.add("Email Required"); }
    //     if(!billingInfo.getZip().matches("\\d{5}"))                                { hasErrors = true; messages.add("Invalid Zip Code"); }
    //     if(!billingInfo.getPhone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}"))          { hasErrors = true; messages.add("Invalid Phone Number"); }
    //     if(states.get(billingInfo.getState()) == null)        {hasErrors = true; messages.add("Invalid State"); }


    //     if(hasErrors) {
    //         messages.print();
    //         return null;
    //     }
    //     else {
    //         ArrayList<BillingInfo> list = new ArrayList<BillingInfo>();
    //         list.add(billingInfo);
    //         Customer c = new Customer("John2", null, null, null, null, 0, 0, list, null, null);
    //         repository.save(c);
    //         return billingInfo;
    //     }
    // }
}