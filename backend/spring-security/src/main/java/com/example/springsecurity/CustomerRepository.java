package com.example.springsecurity;

import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByFirstName(String firstName);
    Customer findById(int id);
    Customer findByUsername(String username);
    Customer findByPassword(String password);

}