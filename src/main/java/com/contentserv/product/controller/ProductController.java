package com.contentserv.product.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contentserv.product.entity.Product;
import com.contentserv.product.exception.ProductNotFoundException;
import com.contentserv.product.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/products/{id}")
	public Product getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return productService.getProducts();
	}
	
	@PostMapping("/products")
	public ResponseEntity<Void> createProduct(@RequestBody Product product){
		Product savedProduct = productService.createProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
		Product tempProduct = productService.updateProduct(id, product);
		if(tempProduct == null) {
			throw new ProductNotFoundException("Product not found ---->" + id);
		}
		return tempProduct;
	}
	
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable Long id) {
		Product tempProduct = productService.deleteProduct(id);
		if(tempProduct == null)
			throw new ProductNotFoundException("Product not found ---->" + id);
		
	}
  
}
