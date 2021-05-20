package com.example.springcustomer;import javax.persistence.Embeddable;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;

import lombok.*;

@Entity
@Table(name="Orders")
@Getter
@Setter
@RequiredArgsConstructor
public class Order implements Serializable{

    private @Id int id;

    private String drink;
    private String milk;
    private String size;
    private double price;
    private String status;
    private String store;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_to_order_id", nullable = false)
    private Customer customer;

    public Order(String drink, String milk, String size){
        this.drink = drink;
        this.milk = milk;
        this.size = size;
        this.store = store;
    }
}