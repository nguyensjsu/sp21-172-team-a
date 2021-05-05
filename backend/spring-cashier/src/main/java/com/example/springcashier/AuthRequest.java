package com.example.springcashier;

public class AuthRequest extends Payload {

    public String reference ;
    public String billToFirstName ;
    public String billToLastName ;
    public String billToAddress ;
    public String billToZipCode ;
    public String billToCity ;
    public String billToState ;
    public String billToPhone ;
    public String billToEmail ;
    public String transactionAmount ;
    public String transactionCurrency ;
    public String cardNumnber ;
    public String cardCVV ;
    public String cardExpMonth ;
    public String cardExpYear ;
    public String cardType ;


    public String reference() { return reference; }
    public String billToFirstName() { return billToFirstName; }
    public String billToLastName() { return billToLastName; }
    public String billToAddress() { return billToAddress; } ;
    public String billToZipCode() { return billToZipCode; } ;
    public String billToCity() { return billToCity; } ;
    public String billToState() { return billToState; } ;
    public String billToPhone() { return billToPhone; } ;
    public String billToEmail() { return billToEmail; } ;
    public String transactionAmount() { return transactionAmount; } ;
    public String transactionCurrency() { return transactionCurrency; } ;
    public String cardNumnber() { return cardNumnber; } ;
    public String cardCVV() { return cardCVV; } ;
    public String cardExpMonth() { return cardExpMonth; } ;
    public String cardExpYear() { return cardExpYear; } ;
    public String cardType() { return cardType; } ;

}


