package com.example.springcashier;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;


@Getter
@Setter
@RequiredArgsConstructor
class BillingInfo {

    private String address ;
    private String city ;
    private String state ;
    private String zip ;
    private String phone ;
    private String email ;

    private int customerId;

}
