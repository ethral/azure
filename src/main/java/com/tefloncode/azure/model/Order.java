package com.tefloncode.azure.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name= "order", schema= "azure")
@Data
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	
	
	@Column(name="prod_name")
	private String productName;
	
	@Column(name="order_charge")
	private Double orderCharge;
	
	@Column(name="order_tmstmp")
	private Timestamp orderTs;
	
	@Column(name="order_type")
	private String orderType;
	
	@Column(name="order_poc")
	private String orderPoc;
	
	
	

}
