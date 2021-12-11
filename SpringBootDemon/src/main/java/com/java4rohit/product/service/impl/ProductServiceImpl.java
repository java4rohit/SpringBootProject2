 package com.java4rohit.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4rohit.product.entity.Product;
import com.java4rohit.product.repository.ProductRepository;
import com.java4rohit.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Optional<Product> getProduct(Integer productId) {
		
		 return productRepository.findById(productId);
		
	
	}

	@Override
	public List<Product> getProducts() {
		
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(Integer productId) {
		productRepository.deleteById(productId);
		
	}

	@Override
	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public String findNameByProductId(Integer productId) {
		return productRepository.findNameById(productId);
	}

}
