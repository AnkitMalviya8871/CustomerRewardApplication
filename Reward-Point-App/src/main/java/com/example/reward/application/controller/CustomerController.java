package com.example.reward.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reward.application.customer.dto.CustomerDTO;
import com.example.reward.application.dto.RewardResponse;
import com.example.reward.application.service.CustomerService;
import com.example.reward.application.transaction.dto.TransactionDTO;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		CustomerDTO customerDTO = customerService.getCustomerById(id);
		return ResponseEntity.ok(customerDTO);

	}

	@GetMapping("/{id}/rewards")
	public ResponseEntity<RewardResponse> getMonthlyRewards(@PathVariable Long id) {
		RewardResponse rewardResponse = customerService.calculateRewards(id);
		return ResponseEntity.ok(rewardResponse);
	}

	@PostMapping("/addCustomer")
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
		CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@PostMapping("/addTransaction")
	public ResponseEntity<TransactionDTO> addTransaction(@RequestBody TransactionDTO transactionDTO) {

		TransactionDTO createdTransaction = customerService.addTransaction(transactionDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
	}
}
