package com.example.springcashier;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="Orders")
@Data
@RequiredArgsConstructor
public class Order {

    private @Id @GeneratedValue int id;

    private String drink;
    private String milk;
    private String size;
    private double price;
    private String status;
    private int customerId;

    public Order(String drink, String milk, String size){
        this.drink = drink;
        this.milk = milk;
        this.size = size;
    }
}
