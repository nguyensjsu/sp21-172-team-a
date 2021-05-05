package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;

@Entity
@Table(name="Payments")
@Data
@RequiredArgsConstructor
class PaymentsCommand {

	private @Id @GeneratedValue int id;
    //transient private String action;

    private String firstname ;
    private String lastname ;
    private String address ;
    private String city ;
    private String state ;
    private String zip ;
    private String phone ;
    private String cardnum ;
    private String cardexpmon ;
    private String cardexpyear ;
    private String cardcvv ;
    private String email ;
    private String notes ;
    private String orderNumber ;
    private String transactionAmount ;
    private String transactionCurrency ;
    private String authId ;
    private String authStatus ;
    private String captureId ;
    private String captureStatus ;

    public String firstname() { return firstname; }
    public String lastname() { return lastname; }
    public String address() { return address; }
    public String city() { return city; }
    public String state() { return state; }
    public String zip() { return zip; }
    public String phone() { return phone; }
	public String cardnum() { return cardnum; }
    public String cardexpmon() { return cardexpmon; }
    public String cardexpyear() { return cardexpyear; }
    public String cardcvv() { return cardcvv; }
    public String email() { return email; }
    public String notes() { return notes; }


    public void setfirstname(String s) { firstname = s; }
    public void setlastname(String s) { lastname = s; }
    public void setaddress(String s) { address = s; }
    public void setcity(String s) { city = s; }
    public void setstate(String s) { state = s; }
    public void setzip(String s) { zip = s; }
    public void setphone(String s) { phone = s; }
    public void setcardnum(String s) { cardnum = s; }
    public void setcardexpmon(String s) { cardexpmon = s; }
    public void setcardexpyear(String s) { cardexpyear = s; }
    public void setcardcvv(String s) { cardcvv = s; }
    public void setemail(String s) { email = s; }
    public void setnotes(String s) { notes = s; }

}
