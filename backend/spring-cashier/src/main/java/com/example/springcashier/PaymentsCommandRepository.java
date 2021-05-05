package com.example.springcashier;

import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsCommandRepository extends JpaRepository<PaymentsCommand, Integer> {
}
