package com.example.reward.application.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.reward.application.controller.CustomerController;
import com.example.reward.application.customer.dto.CustomerDTO;
import com.example.reward.application.dto.RewardResponse;
import com.example.reward.application.entity.Customer;
import com.example.reward.application.entity.Transaction;
import com.example.reward.application.service.CustomerService;
import com.example.reward.application.transaction.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;
    TransactionDTO transactionDTO;
    private RewardResponse rewardResponse;
    private ObjectMapper objectMapper;
    HashMap<String, Integer> expectedMonthlyPoints = new HashMap<>();

    @BeforeEach
    void setUp() {
    	mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Aman M");

        transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setAmount(150.0);
        transactionDTO.setDate(LocalDate.now());
        transactionDTO.setCustomerDTO(customerDTO);
        
        
        Long customerId = 1L;
        Customer customer = new Customer(1L, "Ashish", null);
        Transaction transaction = new Transaction(1L, 120.0, LocalDate.now().minusMonths(1), customer);

        LocalDate now = LocalDate.now();
        String lastMonth = now.minusMonths(1).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        HashMap<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put(lastMonth, 90);
       
    	

    	
    	//String lastMonth = now.minusMonths(1).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    	expectedMonthlyPoints.put(lastMonth, 90);

        rewardResponse = new RewardResponse(customerId, "Ashish", monthlyPoints, 90);

      
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Aman M"));
    }
@Test
    void testGetMonthlyRewards() throws Exception {
	
        when(customerService.calculateRewards(anyLong())).thenReturn(rewardResponse);

        mockMvc.perform(get("/customers/1/rewards")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customerId").value(1L))
            .andExpect(jsonPath("$.customerName").value("Ashish"))
            .andExpect(jsonPath("$.totalPoints").value(90))
            .andExpect(jsonPath("$.monthlyPoints").value(expectedMonthlyPoints)); // Adjust the month key as needed
    }

    @Test
    void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/customers/addCustomer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(customerDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Aman M"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO));

        mockMvc.perform(get("/customers/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Aman M"));
    }

    @Test
    void testAddTransaction() throws Exception {
        when(customerService.addTransaction(any(TransactionDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/customers/addTransaction")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.amount").value(150.0));
    }
}