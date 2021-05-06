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
    private int totalPurchases;
	
	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @JoinColumn(name = "customerId")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "billingInfos", joinColumns = @JoinColumn(name = "customerId"))
	private Set<BillingInfo> billingInfos = new HashSet<>();
	// private ArrayList<Order> orders;
	// @OneToOne(mappedBy = "creditCards")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "creditCards", joinColumns = @JoinColumn(name = "customerId"))
	private Set<CreditCard> creditCards = new HashSet<>();
	// @OneToOne(mappedBy = "starbucksCards")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "starbucksCards", joinColumns = @JoinColumn(name = "customerId"))
 	private Set<StarbucksCard> starbucksCards = new HashSet<>();


    // private String address ;
    // private String city ;
    // private String state ;
    // private String zip ;
    // private String phone ;
    // private String email ;

    // private String cardnum ;
    // private String cardexpmon ;
    // private String cardexpyear ;
    // private String cardcvv ;

    // private int rewardsPoints;
    // private double balance;


	
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