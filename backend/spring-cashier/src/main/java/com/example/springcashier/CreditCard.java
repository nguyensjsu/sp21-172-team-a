package com.example.springcashier;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;


@Getter
@Setter
@RequiredArgsConstructor
class CreditCard {

	private int customerId;

    private String cardnum ;
    private String cardexpmon ;
    private String cardexpyear ;
    private String cardcvv ;



}
