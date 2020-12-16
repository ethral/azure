package com.tefloncode.azure.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tefloncode.azure.model.Order;
import com.tefloncode.azure.repository.OrderRepository;

@Service
public class AccountingService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	
	public Double calcNetAmt(Timestamp fromDate, Timestamp toDate) {
		
		List<Order> orders= new ArrayList<>();
		
		orders = orderRepo.findByOrderTs(fromDate, toDate);
		
		double netAmt = 0.00;
		
		for(Order order: orders) {
			
			if (order.getOrderType().equalsIgnoreCase("Credit")) {
				
				netAmt = netAmt + order.getOrderCharge();
				
			} else {
				
				netAmt = netAmt - order.getOrderCharge();
				
			}
			
		}
		
		
		return netAmt;
	}

}
