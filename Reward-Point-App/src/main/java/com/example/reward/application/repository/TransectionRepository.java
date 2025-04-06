package com.example.reward.application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reward.application.entity.Transaction;

public interface TransectionRepository extends JpaRepository<Transaction, Integer> {
	
List<Transaction>findByCustomerIdAndDateBetween(Long customerId, LocalDate startDate,LocalDate endDate );

}
