package com.example.customerfrontend;import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;

// @Entity
@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
class BillingInfo {

	// @Id
 //    @OneToOne(fetch = FetchType.LAZY, mappedBy = "billingInfo")
    private String address ;
    private String city ;
    private String state ;
    private String zip ;
    private String phone ;
    private String email ;

    BillingInfo(String address, String city, String state, String zip, String phone, String email) {
    	this.address = address;
    	this.city = city;
    	this.state = state;
    	this.zip = zip;
    	this.phone = phone;
    	this.email = email;
    }

}