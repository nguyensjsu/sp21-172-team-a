package com.example.springcustomer;
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

    private int customerId;
    private double rewardsPoints;
    private double balance;
    private String balanceText;
    private String customerIdText;


    public void addRewardsPoints(double points) {
        rewardsPoints += points;
    }


    public void addBalance(double amount) {
        balance += amount;
    }
    

    public void payWithRewardsPoints(double points) {
        if(rewardsPoints - points >= 0) {
            rewardsPoints -= points;
        }
    }


    public void payWithBalance(double amount) {
        if(balance - amount >= 0) {
            balance -= amount;
        }
    }


    StarbucksCard(int customerId, double rewardsPoints, double balance) {
        this.customerId = customerId;
        this.rewardsPoints = rewardsPoints;
        this.balance = balance;
        balanceText = null;
    }
}