package com.example.springcashier;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// import javax.validation.Valid;
// import javax.servlet.http.HttpServletRequest;
// import java.net.InetAddress;
// import java.util.Optional;
// import java.time.*; 
// import java.util.List;

// import javax.crypto.Mac;
// import javax.crypto.spec.SecretKeySpec;
// import java.security.InvalidKeyException;
// import java.security.NoSuchAlgorithmException;
// import java.util.Base64.Encoder;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.Errors;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import lombok.extern.slf4j.Slf4j;
// import lombok.Getter;
// import lombok.Setter;

// import java.util.*;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class SpringCashierApplication{

 // implements CommandLineRunner

	// @Autowired
    // private CustomerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringCashierApplication.class, args);
    }
/*
    @Bean(initMethod = "start", destroyMethod = "stop")
public Server h2Server() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093");
}
*/

    // @Override
    // public void run(String... args) throws Exception {
    //     // Cleanup database tables.
    //     repository.deleteAll();

    //     // Insert a user with multiple phone numbers and addresses.
    //     List<BillingInfo> billingInfo = new ArrayList<>();
    //     billingInfo.add(new BillingInfo("address", "city", "state", "zip", "phone", "email"));
    //     // int x = billingInfo.getCustomerId();
    //     // System.out.println(x);


    //     Customer user = new Customer(billingInfo);
    //     // Customer test = repository.getById(1);
    //     // System.out.println(test.toString());

    //     repository.save(user);
        
    //     BillingInfo x = billingInfo.get(0);
    //     x.setAddress("HELLO");
    //     billingInfo.set(0, x);
    //     user.setBillingInfos(billingInfo);
    //     repository.save(user);
    // }

}