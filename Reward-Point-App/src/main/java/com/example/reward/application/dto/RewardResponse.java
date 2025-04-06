package com.example.reward.application.dto;

import java.util.Map;

public class RewardResponse {
	
	@Override
	public String toString() {
		return "RewardResponse [cutomerId=" + customerId + ", cutomerName=" + customerName + ", monthlyPoints="
				+ monthlyPoints + ", totalPoints=" + totalPoints + "]";
	}
	private Long customerId;
	private String customerName;
	private Map<String, Integer> monthlyPoints;
	private int totalPoints;
	
	
	public RewardResponse() {
		
	}
	public RewardResponse(Long customerId, String customerName, Map<String, Integer> monthlyPoints,
			int totalPoints) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Map<String, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}
	public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Object thenReturn(Map<String, Integer> of) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	

}
