package com.example.reward.application.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String name;
		
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Transaction> transaction;

	// Constructor
	public Customer(Long customerId, String name, List<Transaction> transaction) {
		this.customerId = customerId;
		this.name = name;
		this.transaction = transaction;
	}

	public Customer() {
	}

	// Getters and Setters
	public Long getId() {
		return customerId;
	}

	public void setId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transaction> getTransactionReference() {
		return transaction;
	}

	public void setTransactionReference(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "Customer{" + "id=" + customerId + ", name='" + name + '\'' + ", transactionReference=" + transaction
				+ '}';
	}

	
}