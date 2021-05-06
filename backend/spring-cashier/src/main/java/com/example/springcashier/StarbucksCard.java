package com.example.springcashier;

import lombok.*;
import lombok.RequiredArgsConstructor;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;


@Embeddable
@Getter
@RequiredArgsConstructor
class StarbucksCard {

    private int rewardsPoints;
    private double balance;


    public void addRewardsPoints(int points) {
        rewardsPoints += points;
    }


    public void removeRewardsPoints(int points) {
        if(rewardsPoints - points >= 0) {
            rewardsPoints -= points;
        }
    }


    public void fillStarbucksCard(CreditCard creditCard) {

    }

}
