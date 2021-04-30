package com.example.springcashier;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.time.*;

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

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;
import java.util.regex.*;  

import com.example.springcybersource.*;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Controller
@RequestMapping("/")
public class CartController {

    @PostMapping("/addToCart/{carId}")
    public String addToCart(@PathVariable Long carId, @ModelAttribute CartItems cartItems, Principal principal){
    
        //to save to cartItem table.
        cartService.addToCart(carId, cartItems, principal.getName());
    
        return "products";
    }
    
    @GetMapping("/myCart")
    public  String myCart ( @AuthenticationPrincipal Principal principal, Model model){
    
       List<CartItems> cartItems =  cartService.myCart(principal.getName());
       model.addAttribute("cartItems",cartItems);
        return "myCart";
    }

    public void addToCart(Long carId, CartItems cartItems, String username){

    Product product = productRepository.findById(carId).orElse(null);
        cartItems.setProduct(product);
    
            cartItems.setSubTotal(product.getPrice());
            cartItems.setUsername(username);
    
            cartItemsRepository.save(cartItems);
    
    }
    public List<CartItems> myCart(String userName){
    
        List<CartItems> cartItems = new ArrayList<>();
        cartItemsRepository.findByUsername(userName).forEach(cartItems::add);
    
        return cartItems;
    }
}

