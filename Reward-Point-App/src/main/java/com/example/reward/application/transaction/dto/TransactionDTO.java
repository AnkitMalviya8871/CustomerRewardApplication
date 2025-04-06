package com.example.reward.application.transaction.dto;

import java.time.LocalDate;

import com.example.reward.application.customer.dto.CustomerDTO;
import com.example.reward.application.entity.Customer;

public class TransactionDTO {
	 private Long id;
	    private double amount;
	    private LocalDate date;
		private CustomerDTO customerDTO;
	    
	    @Override
		public String toString() {
			return "TransactionDTO [id=" + id + ", amount=" + amount + ", date=" + date + ", customer=" + customerDTO
					+ "]";
		}
		public CustomerDTO getCustomerDTO() {
			return customerDTO;
		}
		public void setCustomerDTO(CustomerDTO customerDTO) {
			this.customerDTO = customerDTO;
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
}
