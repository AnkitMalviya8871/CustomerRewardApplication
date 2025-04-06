package com.example.reward.application.customer.dto;

import java.util.List;

import com.example.reward.application.transaction.dto.TransactionDTO;



public class CustomerDTO {
	private Long id;
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
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
	private String name;
    private List<TransactionDTO> transactions;
}

