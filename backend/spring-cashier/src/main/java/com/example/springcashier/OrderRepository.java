package com.example.springcashier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer(Customer customer, Sort sort);
}
