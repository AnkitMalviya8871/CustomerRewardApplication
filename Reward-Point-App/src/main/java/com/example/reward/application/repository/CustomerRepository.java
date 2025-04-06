package com.example.reward.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reward.application.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
   
}
