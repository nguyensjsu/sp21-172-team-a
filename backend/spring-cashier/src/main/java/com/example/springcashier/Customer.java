package com.example.springcashier;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;
import java.util.*;

@Entity
@Table(name="Customers")
@Data
@Getter
@Setter
public class Customer {
	@Id @GeneratedValue private int customerId;
	private String firstName;
	private String lastName;
	private int rewardsPoints;
    private int totalPurchases;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId")
	private BillingInfo billingInfo;
	// private ArrayList<Order> orders;
	// @OneToOne(mappedBy = "creditCards")
	// private CreditCard creditCard;
	// @OneToOne(mappedBy = "starbucksCards")
 // 	private StarbucksCard starbucksCard;

	
	// Customer(BillingInfo billingInfo) {
	// 	this.billingInfo = billingInfo;
	// 	firstName = null;
	// 	lastName = null;
	// 	creditCard = null;
	// 	starbucksCard = null;
	// 	rewardsPoints = 0;
	// 	totalPurchases = 0;
	// }

	// Customer(Integer id, String name) {
	// 	this.id = id;
	// 	this.name = name;
	// }

}