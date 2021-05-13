package com.example.springcustomer;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;
import javax.annotation.*;


@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
public
class CreditCard {


    private String cardnum ;
    private String cardexpmon ;
    private String cardexpyear ;
    private String cardcvv ;



}
