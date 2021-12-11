package com.java4rohit.product.service;

import java.util.List;
import java.util.Optional;

import com.java4rohit.product.entity.Product;

public interface ProductService {

public Optional<Product> getProduct(Integer productId);

public List<Product> getProducts();

public Product saveProduct(Product product);

public void updateProduct(Product product);

public void deleteProduct(Integer productId);

public Product findByName(String name);

public String findNameByProductId(Integer productId);

	
}
