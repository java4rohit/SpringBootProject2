package com.java4rohit.product.controller;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java4rohit.product.entity.Product;
import com.java4rohit.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@GetMapping(value="/getproducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> list=	productService.getProducts();
	return new ResponseEntity<>(list, list.isEmpty()?HttpStatus.NO_CONTENT:HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int productId){
		Optional<Product> productOpt = productService.getProduct(productId);
		return new ResponseEntity<>(productOpt.isPresent()?productOpt.get():null, productOpt.isPresent()?HttpStatus.OK:HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<Product> saveProduct(@Validated  @RequestBody Product product) {
		Product responseProduct = productService.saveProduct(product);
		return new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update")
	public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
		productService.updateProduct(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") int productId){
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Product> getProductById(@PathVariable("name") String name){
		Product product = productService.findByName(name);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping(value = "/namebyId/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getProductNameById(@PathVariable("id") int productId){
		String name = productService.findNameByProductId(productId);
		return new ResponseEntity<>(name, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
}
