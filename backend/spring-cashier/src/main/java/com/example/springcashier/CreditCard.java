package com.example.springcashier;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;


@Getter
@Setter
@RequiredArgsConstructor
class CreditCard {

    private String cardnum ;
    private String cardexpmon ;
    private String cardexpyear ;
    private String cardcvv ;

    private final int customerId;

}
