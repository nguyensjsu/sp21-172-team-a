package com.example.registrationlogin;
import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    private Long id;
    private String name;
    private Set<Customer> customers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Sustomer> customers) {
        this.customers = customers;
    }
}