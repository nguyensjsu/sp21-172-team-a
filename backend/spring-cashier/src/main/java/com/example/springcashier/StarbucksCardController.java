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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

public class StarbucksCardController {
    

    @Value("${cybersource.apihost}") private String apiHost ;
    @Value("${cybersource.merchantkeyid}") private String merchantKeyId ;
    @Value("${cybersource.merchantsecretkey}") private String merchantsecretKey ;
    @Value("${cybersource.merchantid}") private String merchantId ;
    private CyberSourceAPI api = new CyberSourceAPI() ;


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


    @GetMapping("/starbuckscards")
    public String getAction(@ModelAttribute("starbuckscards") StarbucksCard dummy, 
                            Model model) {
        getStarbucksCardInfo(1, model);     
        return "starbuckscards" ;
    }

    @GetMapping("/starbuckscard")
    @ResponseBody
    StarbucksCard getOne(HttpServletResponse response) {
        StarbucksCard card = new StarbucksCard(0,0,0);
        //Customer test = repository.findById(1);
        //card.setCustomerId(test.getId());
        //test.getStarbucksCards().add(card);
        //if(card == null)
           //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
        return card;
    }
/*
    @DeleteMapping("/deletestarbuckscard")
    @ResponseBody
    void deleteAll() {
        StarbucksCard card = customer.getStarbucksCard().get(0);
        repository.delete(card);
    }
*/
    @PostMapping("/starbuckscards")
    public String postAction(@Valid @ModelAttribute("starbuckscards") StarbucksCard dummy,  
                            
                            Errors errors, Model model, HttpServletRequest request) {
        /*  
        *   TEST
        */
        StarbucksCard starbucksCardTest = new StarbucksCard(0, 0, 0);
        Customer test = repository.findById(1);
        starbucksCardTest.setCustomerId(test.getId());
        test.getStarbucksCards().add(starbucksCardTest);
        repository.save(test);
        /*  
        *   TEST
        */


        Customer customer = repository.findById(starbucksCardTest.getCustomerId());
        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;
        

        if(customer.getBillingInfos().isEmpty()) {
            messages.add("No Billing Info Found. Please add one before filling your card");
            hasErrors = true;
        }
        if(customer.getCreditCards().isEmpty()) {
            messages.add("No Credit Card Info Found. Please add one before filling your card");
            hasErrors = true;
        }
        if(hasErrors) {
            messages.print();
            model.addAttribute("messages", messages.getMessage());
            return "starbuckscards";
        }
        

        BillingInfo billingInfo = customer.getBillingInfos().get(0);
        CreditCard creditCard = customer.getCreditCards().get(0);
        StarbucksCard starbucksCard = customer.getStarbucksCards().get(0);

        
        CyberSourceAPI.setHost( apiHost ) ;
        CyberSourceAPI.setKey( merchantKeyId ) ;
        CyberSourceAPI.setSecret(merchantsecretKey ) ;
        CyberSourceAPI.setMerchant( merchantId ) ;


        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest() ;
        auth.reference = order_num; 
        auth.billToFirstName = customer.getFirstName();
        auth.billToLastName = customer.getLastName();
        auth.billToAddress = billingInfo.getAddress();
        auth.billToCity = billingInfo.getCity();
        auth.billToState = billingInfo.getState();
        auth.billToZipCode = billingInfo.getZip();
        auth.billToPhone = billingInfo.getPhone();
        auth.billToEmail = billingInfo.getEmail();  
        

        auth.transactionAmount = dummy.getBalanceText(); // This is a temp value
        

        auth.transactionCurrency = "USD";
        auth.cardNumnber = creditCard.getCardnum();
        auth.cardExpMonth = months.get(creditCard.getCardexpmon());
        auth.cardExpYear = creditCard.getCardexpyear();
        auth.cardCVV = creditCard.getCardcvv();
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);

       
        if(auth.cardType.equals("ERROR")) {
            System.out.println("Unsupported Card Type");
            model.addAttribute("message", "Unsupported Card Type");
            return "starbuckscards";
        }


        boolean authValid = false;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Response: " + authResponse.toJson());
        authValid = true;
        if (!authResponse.status.equals("AUTHORIZED")) {
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "starbuckscards";  
        }
        
        boolean captureValid = false ;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if (authValid) {
            capture.reference = order_num;
            capture.paymentId = authResponse.id;
            

            capture.transactionAmount = dummy.getBalanceText();
            

            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson() ) ;
            captureResponse = api.capture(capture) ;
            System.out.println("\n\nCapture Response: " + captureResponse.toJson() ) ;
            captureValid = true;
            if ( !captureResponse.status.equals("PENDING") ) {
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "starbuckscards";
            }
        }


        if(authValid && captureValid){
            

            starbucksCard.addBalance(Double.parseDouble(dummy.getBalanceText()));
            

            repository.save(customer);
            System.out.println("Thank You for your Payment! Your Order Number is: " + order_num);
            model.addAttribute("message", "Thank You for your Payment! Your Order Number is: " + order_num);
            getStarbucksCardInfo(1, model);  
        }
    

        return "starbuckscards";
    }


    private void getStarbucksCardInfo(int id, Model model) {

       
        ErrorMessages e = new ErrorMessages();

        
        if(repository.findById(id) != null) {
            Customer c = repository.findById(id);
            if(c.getStarbucksCards().isEmpty()) {
                c.getStarbucksCards().add(new StarbucksCard(id, 0, 0));
            }
        
            e.add("Rewards Points: " + c.getStarbucksCards().get(0).getRewardsPoints());
            e.add("Balance:        " + c.getStarbucksCards().get(0).getBalance());
            
        }
        

        model.addAttribute("test", e.getMessage());   
    }
}