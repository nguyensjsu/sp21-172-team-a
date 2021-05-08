package com.example.springcashier;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;


@Embeddable
@RequiredArgsConstructor
public class Order {


    private String drink;
    private boolean milk;
    private String size;
    private int price;

    public Order(String drink, boolean milk, String size, int price){
        this. drink = drink;
        this.milk = milk;
        this.size = size;
        this.price = price;
    }


    public String toString(){
        return "Order: " + " " +
        "Drink: " + drink + " " 
      + "Milk: " + milk + " "
      + "Size: " + size;
    }


    public boolean getMilk() {
        return milk;
    }
}
