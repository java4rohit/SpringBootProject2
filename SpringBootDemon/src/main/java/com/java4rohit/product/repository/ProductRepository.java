package com.java4rohit.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java4rohit.product.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	
	public Product findByName(String name);
	
	public String findNameById(Integer id); // Select Name from Product where id = 'id';
	
	@Query("FROM Product where price>50")
	
	public List<Product> getThisProduct();
}
