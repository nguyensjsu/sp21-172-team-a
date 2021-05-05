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


@Slf4j
@Controller
@RequestMapping(value = "/paymentmethod")
public class PaymentsController {
    

    // @Value("${cybersource.apihost}") private String apiHost ;
    // @Value("${cybersource.merchantkeyid}") private String merchantKeyId ;
    // @Value("${cybersource.merchantsecretkey}") private String merchantsecretKey ;
    // @Value("${cybersource.merchantid}") private String merchantId ;
    // private CyberSourceAPI api = new CyberSourceAPI() ;


    @Autowired
    private PaymentsCommandRepository repository ;


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


    // private static Map<String, String> states = new HashMap<>();
    // static {
    //     states.put("AL", "Alabama");
    //     states.put("AK", "Alaska");
    //     states.put("AZ", "Arizona");
    //     states.put("AR", "Arkansas");
    //     states.put("CA", "California");
    //     states.put("CO", "Colorado");
    //     states.put("CT", "Connecticut");
    //     states.put("DE", "Delaware");
    //     states.put("DC", "District of Columbia");
    //     states.put("FL", "Florida");
    //     states.put("FO", "Foljava");
    //     states.put("GA", "Georgia");
    //     states.put("GU", "Guam");
    //     states.put("HI", "Hawaii");
    //     states.put("ID", "Idaho");
    //     states.put("IL", "Illinois");
    //     states.put("IN", "Indiana");
    //     states.put("IA", "Iowa");
    //     states.put("KS", "Kansas");
    //     states.put("KY", "Kentucky");
    //     states.put("LA", "Louisiana");
    //     states.put("ME", "Maine");
    //     states.put("MD", "Maryland");
    //     states.put("MA", "Massachusetts");
    //     states.put("MI", "Michigan");
    //     states.put("MN", "Minnesota");
    //     states.put("MS", "Mississippi");
    //     states.put("MO", "Missouri");
    //     states.put("MT", "Montana");
    //     states.put("NE", "Nebraska");
    //     states.put("NV", "Nevada");
    //     states.put("NH", "New Hampshire");
    //     states.put("NJ", "New Jersey");
    //     states.put("NM", "New Mexico");
    //     states.put("NY", "New York");
    //     states.put("NC", "North Carolina");
    //     states.put("ND", "North Dakota");
    //     states.put("MP", "Northern Mariana");
    //     states.put("OH", "Ohio");
    //     states.put("OK", "Oklahoma");
    //     states.put("OR", "Oregon");
    //     states.put("PA", "Pennsylvania");
    //     states.put("PR", "Puerto Rico");
    //     states.put("RI", "Rhode Island");
    //     states.put("SC", "South Carolina");
    //     states.put("SD", "South Dakota");
    //     states.put("TN", "Tennesse");
    //     states.put("TX", "Texas");
    //     states.put("UT", "Utah");
    //     states.put("VT", "Vermont");
    //     states.put("VA", "Virginia");
    //     states.put("VI", "Virgin Islands");
    //     states.put("WA", "Washington");
    //     states.put("WV", "West Virginia");
    //     states.put("WI", "Wisconsin");
    //     states.put("WY", "Wyoming");
    // }      


    @GetMapping
    public String getAction( @ModelAttribute("command") PaymentsCommand command, 
                            Model model) {
        return "paymentmethod" ;
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") PaymentsCommand command,  
                            
                            Errors errors, Model model, HttpServletRequest request) {
        
        //@RequestParam(value="action", required=true) String action,
        //log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        ErrorMessages messages = new ErrorMessages();
        boolean hasErrors = false;


        // if(command.firstname().equals(""))      { hasErrors = true; messages.add("First Name Required"); }
        // if(command.lastname().equals(""))       { hasErrors = true; messages.add("Last Name Required"); }
        // if(command.address().equals(""))        { hasErrors = true; messages.add("Address Required"); }
        // if(command.city().equals(""))           { hasErrors = true; messages.add("City Required"); }
        // if(command.state().equals(""))          { hasErrors = true; messages.add("State Required"); }
        // if(command.zip().equals(""))            { hasErrors = true; messages.add("Zip Required"); }
        // if(command.phone().equals(""))          { hasErrors = true; messages.add("Phone Number Required"); }
        if(command.cardnum().equals(""))        { hasErrors = true; messages.add("Credit Card Number Required"); }
        if(command.cardexpmon().equals(""))     { hasErrors = true; messages.add("Credit Card Expiration Month Required"); }
        if(command.cardexpyear().equals(""))    { hasErrors = true; messages.add("Credit Card Expiration Year Required"); }
        if(command.cardcvv().equals(""))        { hasErrors = true; messages.add("Credit Card CVV Required"); }
        // if(command.email().equals(""))          { hasErrors = true; messages.add("Email Required"); }
        // if(!command.zip().matches("\\d{5}"))                                { hasErrors = true; messages.add("Invalid Zip Code"); }
        // if(!command.phone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}"))          { hasErrors = true; messages.add("Invalid Phone Number"); }
        if(!command.cardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))       { hasErrors = true; messages.add("Invalid Credit Card Number"); }
        if(!command.cardexpyear().matches("\\d{4}"))                        { hasErrors = true; messages.add("Invalid Credit Card Expiration Year"); }
        if(!command.cardcvv().matches("\\d{3}"))                            { hasErrors = true; messages.add("Invalid Credit Card CVV"); }
        if(months.get(command.cardexpmon()) == null)   {hasErrors = true; messages.add("Invalid Card Expiration Month"); }
        // if(states.get(command.state()) == null)        {hasErrors = true; messages.add("Invalid State"); }


        if(hasErrors) {
            messages.print();
            model.addAttribute("messages", messages.getMessage());
            return "paymentmethod";
        }
        else {
            repository.save(command);
            // PaymentsCommand test = repository.findById(1);
            // command.setfirstname(test.firstname());
            System.out.println("Thank You for your Payment!");
            model.addAttribute("message", "Thank You for your Payment!");
        }


        // CyberSourceAPI.setHost( apiHost ) ;
        // CyberSourceAPI.setKey( merchantKeyId ) ;
        // CyberSourceAPI.setSecret(merchantsecretKey ) ;
        // CyberSourceAPI.setMerchant( merchantId ) ;


        // int min = 1239871;
        // int max = 9999999;
        // int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        // String order_num = String.valueOf(random_int);
        // AuthRequest auth = new AuthRequest() ;
        
        // auth.reference = order_num; 
        // auth.billToFirstName = command.firstname();
        // auth.billToLastName = command.lastname();
        // auth.billToAddress = command.address();
        // auth.billToCity = command.city();
        // auth.billToState = command.state();
        // auth.billToZipCode = command.zip();
        // auth.billToPhone = command.phone();
        // auth.billToEmail = command.email();  
        // auth.transactionAmount = "30.00"; // This is a temp value
        // auth.transactionCurrency = "USD";
        // auth.cardNumnber = command.cardnum();
        // auth.cardExpMonth = months.get(command.cardexpmon());
        // auth.cardExpYear = command.cardexpyear();
        // auth.cardCVV = command.cardcvv();
        // auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);

       
        // if(auth.cardType.equals("ERROR")) {
        //     System.out.println("Unsupported Card Type");
        //     model.addAttribute("message", "Unsupported Card Type");
        //     return "paymentmethod";
        // }


        // boolean authValid = false;
        // AuthResponse authResponse = new AuthResponse();
        // System.out.println("\n\nAuth Request: " + auth.toJson());
        // authResponse = api.authorize(auth);
        // System.out.println("\n\nAuth Response: " + authResponse.toJson());
        // authValid = true;
        // if (!authResponse.status.equals("AUTHORIZED")) {
        //     System.out.println(authResponse.message);
        //     model.addAttribute("message", authResponse.message);
        //     return "paymentmethod";  
        // }
        
        // boolean captureValid = false ;
        // CaptureRequest capture = new CaptureRequest();
        // CaptureResponse captureResponse = new CaptureResponse();
        // if (authValid) {
        //     capture.reference = order_num;
        //     capture.paymentId = authResponse.id;
        //     capture.transactionAmount = "30.00";
        //     capture.transactionCurrency = "USD";
        //     System.out.println("\n\nCapture Request: " + capture.toJson() ) ;
        //     captureResponse = api.capture(capture) ;
        //     System.out.println("\n\nCapture Response: " + captureResponse.toJson() ) ;
        //     captureValid = true;
        //     if ( !captureResponse.status.equals("PENDING") ) {
        //         System.out.println(captureResponse.message);
        //         model.addAttribute("message", captureResponse.message);
        //         return "paymentmethod";
        //     }
        // }


        // if(authValid && captureValid){
        //     command.setOrderNumber(order_num);
        //     command.setTransactionAmount("30.00");
        //     command.setTransactionCurrency("USD");
        //     command.setAuthId(authResponse.id);
        //     command.setAuthStatus(authResponse.status);
        //     command.setCaptureId(captureResponse.id);
        //     command.setCaptureStatus(captureResponse.status);

            
        //     repository.save(command);
        //     System.out.println("Thank You for your Payment! Your Order Number is: " + order_num);
        //     model.addAttribute("message", "Thank You for your Payment! Your Order Number is: " + order_num);
        // }
    

        return "paymentmethod";
    }

}