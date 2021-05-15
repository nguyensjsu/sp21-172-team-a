package com.example.customerfrontend;import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findById(int id) ;
}
