package com.example.springcashier;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;


// @Embeddable
@Entity
@Table(name="Orders")
@Data
@RequiredArgsConstructor
public class Order {

    private @Id @GeneratedValue int id;

    private String drinkModel;
    private String milk;
    private String size;
    private int price;
    private String status;

    public Order(String drinkModel, String milk, String size, int price){
        this.drinkModel = drinkModel;
        this.milk = milk;
        this.size = size;
        this.price = price;
    }


    public String toString(){
        return "Order: " + " " +
        "Drink: " + drinkModel + " " 
      + "Milk: " + milk + " "
      + "Size: " + size;
    }
}
