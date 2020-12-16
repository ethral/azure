package com.tefloncode.azure.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name= "product", schema= "azure")
@Data
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prod_id")
	private Long productId;
	
	
	@Column(name="prod_name")
	private String productName;
	
	@Column(name="prod_desc")
	private String productDesc;
	
	

}
