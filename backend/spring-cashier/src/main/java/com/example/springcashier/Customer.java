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
	@CollectionTable(name = "billingInfos", joinColumns = @JoinColumn(name = "customerId"))
	private List<BillingInfo> billingInfos = new ArrayList<>();

	// @OneToOne(mappedBy = "creditCards")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "creditCards", joinColumns = @JoinColumn(name = "customerId"))
	private Set<CreditCard> creditCards = new HashSet<>();
	
	// @OneToOne(mappedBy = "starbucksCards")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "starbucksCards", joinColumns = @JoinColumn(name = "customerId"))
 	private Set<StarbucksCard> starbucksCards = new HashSet<>();

	 @ElementCollection(fetch = FetchType.LAZY)
	 @CollectionTable(name = "orders", joinColumns = @JoinColumn(name = "customerId"))
	 private Set<Order> orders = new HashSet<>();

    
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


	// Customer(List<CreditCard> creditCards) {
	// 	billingInfos = null;
	// 	firstName = null;
	// 	middleName = null;
	// 	lastName = null;
	// 	username = null;
	// 	password = null;
	// 	this.creditCards = creditCards;
	// 	starbucksCards = null;
	// 	orders = null;
	// 	totalPurchases = 0;
	// }


	void addBillingInfo(BillingInfo billingInfo) {
		billingInfos.add(billingInfo);
	}


	void addCreditCard(CreditCard creditCard) {
		creditCards.add(creditCard);
	}


	void addCreditCard(StarbucksCard starbucksCard) {
		starbucksCards.add(starbucksCard);
	}
}