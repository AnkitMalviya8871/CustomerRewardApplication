package com.example.reward.application.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reward.application.customer.dto.CustomerDTO;
import com.example.reward.application.dto.RewardResponse;
import com.example.reward.application.entity.Customer;
import com.example.reward.application.entity.Transaction;
import com.example.reward.application.exception.CustomerNotFoundException;
import com.example.reward.application.exception.InvalidTransactionAmountException;
import com.example.reward.application.repository.CustomerRepository;
import com.example.reward.application.repository.TransectionRepository;
import com.example.reward.application.transaction.dto.TransactionDTO;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TransectionRepository transactionRepository;

	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setName(customerDTO.getName());
		if (customerDTO.getTransactions() != null) {
			final Customer finalCustomer = customer;
			List<TransactionDTO> dtoTransaaction = customerDTO.getTransactions();
			List<Transaction> transactions = dtoTransaaction.stream().map(dto -> {
				Transaction transaction = convertTransactionDTOToTransaction(dto);
				transaction.setCustomer(finalCustomer); // Set the customer object
				return transaction;
			}).collect(Collectors.toList());
			customer.setTransactionReference(transactions);
		}

		customer = customerRepository.save(customer);

		return convertToDTO(customer);
	}

	public TransactionDTO createTransaction(Long customerId, TransactionDTO transactionDTO) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate());
		transaction.setCustomer(customer);
		transaction = transactionRepository.save(transaction);
		return convertTransactionToDTO(transaction);
	}

	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
		return convertToDTO(customer);
	}

	private CustomerDTO convertToDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer.getId());
		customerDTO.setName(customer.getName());
		if (customer.getTransactionReference() != null) {
			customerDTO.setTransactions(customer.getTransactionReference().stream().map(this::convertTransactionToDTO)
					.collect(Collectors.toList()));
		}
		return customerDTO;
	}

	private TransactionDTO convertTransactionToDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(transaction.getId());
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setDate(transaction.getDate());
		// transactionDTO.setCustomer(transaction.getCutomer());
		return transactionDTO;
	}

	public int calculateRewardPoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100;
		}
		if (amount > 50) {
			points += (amount - 50);
		}
		return points;
	}

	public RewardResponse calculateRewards(Long customerId) {
		LocalDate now = LocalDate.now();
		LocalDate threeMonthsAgo = now.minusMonths(3);
		if (!customerRepository.existsById(customerId)) {
			throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
		}

		List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateBetween(customerId,
				threeMonthsAgo, now);
		if (transactions.isEmpty()) {
			throw new InvalidTransactionAmountException(
					"Transaction Not Found for last 3 Month for Given Customer Id" + customerId);
		}
		String customerName = transactions.get(0).getCutomer().getName();
		HashMap<String, Integer> monthlyPoints = new HashMap();
		int totalpoints = 0;
		for (Transaction transaction : transactions) {
			int points = calculateRewardPoints(transaction.getAmount());

			String month = transaction.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

			monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
			totalpoints += points;
		}

		return new RewardResponse(customerId, customerName, monthlyPoints, totalpoints);
	}

	public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
		Customer customer = customerRepository.findById(transactionDTO.getCustomerDTO().getId())
				.orElseThrow(() -> new CustomerNotFoundException(
						"Customer not found with id: " + transactionDTO.getCustomerDTO().getId()));

		Transaction transaction = convertTransactionDTOToTransaction(transactionDTO);
		transaction.setCustomer(customer);
		transaction = transactionRepository.save(transaction);

		return convertTransactionToDTO(transaction);
	}

	private Transaction convertTransactionDTOToTransaction(TransactionDTO dto) {
		Transaction transaction = new Transaction();
		transaction.setId(dto.getId());
		transaction.setAmount(dto.getAmount());
		transaction.setDate(dto.getDate());
		return transaction;
	}

	private Transaction convertTransactionDTOToTransction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setId(transactionDTO.getId());
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setDate(transactionDTO.getDate());

		return transaction;
	}

}
