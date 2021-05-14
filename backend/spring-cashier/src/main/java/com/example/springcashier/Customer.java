package com.example.springcashier;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;
import java.util.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name="Customers")
@Data
@RequiredArgsConstructor
public class Customer implements Serializable{
	@Id @GeneratedValue private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
    private int totalOrders;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Order> orders;
	
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

	//  @ElementCollection(fetch = FetchType.LAZY)
	//  @CollectionTable(name = "orders", joinColumns = @JoinColumn(name = "id"))
	//  private List<Order> orders = new ArrayList<>();

	Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	Customer(String firstName, String middleName, String lastName, String email, String password, int totalOrders, List<BillingInfo> billingInfos, List<CreditCard> creditCards, List<StarbucksCard> starbucksCards) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.totalOrders = totalOrders;
		this.billingInfos = billingInfos;
		this.creditCards = creditCards;
		this.starbucksCards = starbucksCards;
	}
	

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