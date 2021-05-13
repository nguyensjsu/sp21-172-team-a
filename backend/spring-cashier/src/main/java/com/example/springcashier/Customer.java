package com.example.springcashier;
import com.example.springcustomer.BillingInfo;
import com.example.springcustomer.CreditCard;

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
	@Id @GeneratedValue private int id;
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


	Customer(String firstName, String middleName, String lastName, String username, String password, int totalPurchases, List<BillingInfo> billingInfos, List<CreditCard> creditCards, List<StarbucksCard> starbucksCards) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.totalPurchases = totalPurchases;
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