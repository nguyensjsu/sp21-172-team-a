package com.example.springcashier;

import lombok.*;
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

}