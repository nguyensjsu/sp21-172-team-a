package com.example.springsecurity;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;
import java.util.*;


@Entity
@Table(name="Customers")
@Data
@RequiredArgsConstructor
public class Customer {
	@Id @GeneratedValue private int customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String username;
	private String password;
    private int totalPurchases;
	
	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn(name = "customerId")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "billingInfos", joinColumns = @JoinColumn(name = "id"))
    private List<BillingInfo> billingInfos = new ArrayList<>();

    // @OneToOne(mappedBy = "creditCards")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "creditCards", joinColumns = @JoinColumn(name = "id"))
    private List<CreditCard> creditCards = new ArrayList<>();
    
    // @OneToOne(mappedBy = "starbucksCards")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "starbucksCards", joinColumns = @JoinColumn(name = "id"))
     private List<StarbucksCard> starbucksCards = new ArrayList<>();

     @ElementCollection(fetch = FetchType.LAZY)
     @CollectionTable(name = "orders", joinColumns = @JoinColumn(name = "id"))
     private List<Order> orders = new ArrayList<>();
    
	Customer(List<BillingInfo> billingInfos) {
		this.billingInfos = billingInfos;
		firstName = null;
		middleName = null;
		lastName = null;
		username = null;
		password = null;
		creditCards = null;
		starbucksCards = null;
		orders = null;
		totalPurchases = 0;
	}

	Customer(String firstName, String middleName, String lastName, String username, String password, int totalPurchases, List<BillingInfo> billingInfos, List<CreditCard> creditCards, List<StarbucksCard> starbucksCards, List<Order> orders) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.totalPurchases = totalPurchases;
        this.billingInfos = billingInfos;
        this.creditCards = creditCards;
        this.starbucksCards = starbucksCards;
        this.orders = orders;
    }

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
}