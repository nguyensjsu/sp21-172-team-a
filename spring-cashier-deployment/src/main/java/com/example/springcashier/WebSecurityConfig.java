package com.example.springcashier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;



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
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.config.http.SessionCreationPolicy ;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomerRepository repository;
    

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().formLogin()
    //             .loginPage("/signIn").permitAll().and().logout().logoutSuccessUrl("/signIn").permitAll();
    //     http.cors().and().csrf().disable();    
    // }

        @Override
        protected void configure(HttpSecurity http) {
            try {
                http.csrf().disable();
                http.authorizeRequests()
                        .antMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        .loginPage("/signIn")
                        .permitAll()
                        .failureUrl("/signIn?error=true")
                        .defaultSuccessUrl("/")
                        .and()
                        .logout()
                        .logoutSuccessUrl("/");

                http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/signIn");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    // cashier's app & backoffice
    // employee login -> allows you to access everything on this app = not a blacklist/whitelist 

    // customer
    // already on app -> customer login -> allows you to access account info = blacklist

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/h2-console/**");
    }
    
   // @Override
   //  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

   //      // int x = 1;
   //      // String username = "null";
   //      // String password = "null";
   //      // System.out.println("Username: " + username + " Password: " + password);
   //      // while(repository.findById(x) != null) {
   //      //     Customer c = repository.findById(x);
   //      //     username = c.getUsername();
   //      //     password = c.getPassword();

   //      //     System.out.println("Username: " + username + " Password: " + password);

   //      //     auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
   //      //         .withUser("admin").password("admin1pass").roles("USER", "ADMIN").and()
   //      //         .withUser(username).password(password).roles("USER");
   //      // }  


   //      auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
   //              .withUser("admin").password("admin1pass").roles("USER", "ADMIN").and()
   //              .withUser("user1").password("user1pass").roles("USER").and()
   //              .withUser("user2").password("user2pass").roles("USER").and()
   //              .withUser("user3").password("user3pass").roles("USER");
   //  } 

    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager();
    }
}