package com.example.springcustomer;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*; 
import java.util.List;

import javax.annotation.*;

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


@Slf4j
@Controller
@RequestMapping(value = "/starbuckscards")
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


    @GetMapping
    public String getAction(Model model) {
        return "starbuckscards" ;
    }


    @PostMapping
    public String postAction(Errors errors, Model model, HttpServletRequest request) {
        
        //@RequestParam(value="action", required=true) String action,
        //log.info( "Action: " + action ) ;
        //log.info( "Command: " + command ) ;

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;


        CyberSourceAPI.setHost( apiHost ) ;
        CyberSourceAPI.setKey( merchantKeyId ) ;
        CyberSourceAPI.setSecret(merchantsecretKey ) ;
        CyberSourceAPI.setMerchant( merchantId ) ;


        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest() ;
        

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
        BillingInfo billingInfo = customer.getBillingInfos().get(0);
        CreditCard creditCard = customer.getCreditCards().get(0);
        StarbucksCard starbucksCard = customer.getStarbucksCards().get(0);


        System.out.println("Got all customer items");
        

        auth.reference = order_num; 
        auth.billToFirstName = customer.getFirstName();
        auth.billToLastName = customer.getLastName();
        auth.billToAddress = billingInfo.getAddress();
        auth.billToCity = billingInfo.getCity();
        auth.billToState = billingInfo.getState();
        auth.billToZipCode = billingInfo.getZip();
        auth.billToPhone = billingInfo.getPhone();
        auth.billToEmail = billingInfo.getEmail();  
        auth.transactionAmount = "30.00"; // This is a temp value
        auth.transactionCurrency = "USD";
        auth.cardNumnber = creditCard.getCardnum();
        auth.cardExpMonth = months.get(creditCard.getCardexpmon());
        auth.cardExpYear = creditCard.getCardexpyear();
        auth.cardCVV = creditCard.getCardcvv();
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);


        System.out.println("Created Auth Request");

       
        if(auth.cardType.equals("ERROR")) {
            System.out.println("Unsupported Card Type");
            model.addAttribute("message", "Unsupported Card Type");
            return "starbuckscards";
        }


        System.out.println("Auth Request Valid");


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
            capture.transactionAmount = "30.00";
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


        System.out.println("Auth Request Valid and Captured");


        if(authValid && captureValid){
            // command.setOrderNumber(order_num);
            // command.setTransactionAmount("30.00");
            // command.setTransactionCurrency("USD");
            // command.setAuthId(authResponse.id);
            // command.setAuthStatus(authResponse.status);
            // command.setCaptureId(captureResponse.id);
            // command.setCaptureStatus(captureResponse.status);

            starbucksCard.addBalance(30);
            repository.save(customer);
            System.out.println("Thank You for your Payment! Your Order Number is: " + order_num);
            model.addAttribute("message", "Thank You for your Payment! Your Order Number is: " + order_num);
        }
    

        return "starbuckscards";
    }

}