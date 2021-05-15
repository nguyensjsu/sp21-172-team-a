package com.example.springcashier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/billingInfo").setViewName("billingInfo");
        registry.addViewController("/signIn").setViewName("signIn");
        registry.addViewController("/cards").setViewName("cards");
        registry.addViewController("/paymentMethod").setViewName("paymentMethod");
        registry.addViewController("/rewardMethod").setViewName("rewardMethod");
        registry.addViewController("/starbucksmenu").setViewName("starbucksmenu");
    }

}