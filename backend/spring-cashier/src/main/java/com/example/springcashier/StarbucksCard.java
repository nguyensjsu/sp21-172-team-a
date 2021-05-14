package com.example.springcashier;

import lombok.*;
import lombok.RequiredArgsConstructor;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;


@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
class StarbucksCard {
    private String amountToAdd;
    private int customerId;
    private double rewardsPoints;
    private double balance;

    public void addRewardsPoints(double points) {
        rewardsPoints += points;
    }


    public void removeRewardsPoints(double points) {
        if(rewardsPoints - points >= 0) {
            rewardsPoints -= points;
        }
    }


    public void addBalance(double amount) {
        balance += amount;
    }
    

    public void pay(double amount) {
        if(balance - amount >= 0) {
            balance -= amount;
        }
    }


    StarbucksCard(int customerId, double rewardsPoints, double balance) {
        this.customerId = customerId;
        this.rewardsPoints = rewardsPoints;
        this.balance = balance;
    }
}
