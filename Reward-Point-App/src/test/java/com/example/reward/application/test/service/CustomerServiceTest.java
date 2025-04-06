package com.example.reward.application.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.reward.application.customer.dto.CustomerDTO;
import com.example.reward.application.dto.RewardResponse;
import com.example.reward.application.entity.Customer;
import com.example.reward.application.entity.Transaction;
import com.example.reward.application.repository.CustomerRepository;
import com.example.reward.application.repository.TransectionRepository;
import com.example.reward.application.service.CustomerService;
import com.example.reward.application.transaction.dto.TransactionDTO;

public class CustomerServiceTest {
	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private TransectionRepository transactionRepository;
	@InjectMocks
	CustomerService customerService;

	private Customer customer;
	private Transaction transaction;
	private CustomerDTO customerDTO;
	private TransactionDTO transactionDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		customer = new Customer();
		customer.setId(1L);
		customer.setName("Aman M");

		transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAmount(150.0);
		transaction.setDate(LocalDate.now());
		transaction.setCustomer(customer);
		transactionDTO = new TransactionDTO();
		transactionDTO.setId(1L);
		transactionDTO.setAmount(150.0);
		transactionDTO.setDate(LocalDate.now());

		customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Aman M");
		customerDTO.setTransactions(Arrays.asList(transactionDTO));

		transactionDTO.setCustomerDTO(customerDTO);
	}

	@Test
	void testCreateCustomer() {
		Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customer);

		CustomerDTO result = customerService.createCustomer(customerDTO);

		assertNotNull(result);
		assertEquals(customerDTO.getName(), result.getName());
		verify(customerRepository, times(1)).save(any(Customer.class));
	}

	@Test
	void testGetAllCustomers() {
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

		List<CustomerDTO> result = customerService.getAllCustomers();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(customerRepository, times(1)).findAll();
	}

	@Test
	void testAddTransaction() {
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		TransactionDTO result = customerService.addTransaction(transactionDTO);

		assertNotNull(result);
		assertEquals(transactionDTO.getAmount(), result.getAmount());
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}

	@Test
	public void testCalculateRewards() {
		Long customerId = 1L;

		Customer cus1 = new Customer(1L, "Ashish", null);
		Customer cus2 = new Customer(2L, "Vinay m", null);
		Transaction transaction1 = new Transaction(1L, 120.0, LocalDate.now().minusMonths(1), cus1);

		LocalDate now = LocalDate.now();
		LocalDate threeMonthsAgo = now.minusMonths(3);

		Mockito.when(customerRepository.existsById(customerId)).thenReturn(true);
		List<Transaction> transactions = Arrays.asList(transaction1);

		Mockito.when(transactionRepository.findByCustomerIdAndDateBetween(customerId, threeMonthsAgo, now))
				.thenReturn(transactions);
		RewardResponse response = (RewardResponse) customerService.calculateRewards(customerId);
		HashMap<String, Integer> expectedMonthlyPoints = new HashMap<>();
		String lastMonth = now.minusMonths(1).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		expectedMonthlyPoints.put(lastMonth, 90);
		assertEquals(customerId, response.getCustomerId());
		assertEquals("Ashish", response.getCustomerName());
		assertEquals(90, response.getTotalPoints());

		assertEquals(expectedMonthlyPoints, response.getMonthlyPoints());

	}

}
