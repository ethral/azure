package com.tefloncode.azure.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tefloncode.azure.model.Order;
import com.tefloncode.azure.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepo;

	
	public List<Order> getAllOrders(){
		
	  List<Order> orders = orderRepo.findAll();
			
        for(Order order: orders) {
        	
        	Calendar calendar = Calendar.getInstance();

    		calendar.setTime(order.getOrderTs());
    		
    		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 3);
    		
    		
    		order.setOrderTs(new Timestamp(calendar.getTimeInMillis()));

        	
        	System.out.println("timestamps: " + order.getOrderTs());
        }
	  
		return orders;
	}
	
	public Order createOrder(Order order) {
		
		Order createdOrder = orderRepo.save(order);
		
		
		Calendar c = Calendar.getInstance();

		c.setTime(createdOrder.getOrderTs());
		
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 3);
		
		
		
		createdOrder.setOrderTs(new Timestamp(c.getTimeInMillis()));

		
		
		return createdOrder;
	}

}
