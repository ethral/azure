package com.tefloncode.azure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tefloncode.azure.model.Product;
import com.tefloncode.azure.repository.ProductRepository;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository prodRepo;

	
	public List<Product> getAllProducts(){
		
		return prodRepo.findAll();
	}
	
	
	public Product createProduct(Product prod) {
		
		return prodRepo.save(prod);
	}
}
