package com.example.reward.application.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double amount;
	private LocalDate date;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
     
	public Transaction(Long id, double amount, LocalDate date, Customer customer) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.customer = customer;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Customer getCutomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
