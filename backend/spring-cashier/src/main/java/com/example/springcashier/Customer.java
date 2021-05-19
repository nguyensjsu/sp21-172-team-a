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
@Getter
@Setter
@RequiredArgsConstructor
public class Customer implements Serializable{
	@Id @GeneratedValue private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String username;
	private String password;
    private int totalOrders;
    private int rewards;

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

	Customer(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	Customer(String firstName, String middleName, String lastName, String username, String password, int totalOrders, int rewards, List<BillingInfo> billingInfos, List<CreditCard> creditCards, List<StarbucksCard> starbucksCards) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.totalOrders = totalOrders;
		if(billingInfos != null) {
			this.billingInfos = billingInfos;
		}
		if(creditCards != null) {
			this.creditCards = creditCards;
		}
		if(starbucksCards != null) {
			this.starbucksCards = starbucksCards;
		}
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