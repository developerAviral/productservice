package com.contentserv.product.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contentserv.product.entity.Product;
import com.contentserv.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	/*Returns list of all products*/
	public List<Product> getProducts(){
		return (List<Product>) repository.findAll();
	}
	
	/* Return product of given id*/
	public Product getProduct(Long id) {
		return (repository.findById(id)).orElse(null);
	}
	
	/* Insert new product */
	public Product createProduct(Product product) {
		return repository.save(product);
	} 
	
	/*Update details of existing product*/
	Product tempProduct;
	public Product updateProduct(Long id, Product product) {
		Optional<Product> products = repository.findById(id);
		
		
		products.ifPresent(prod -> {
			prod.setProductName(product.getProductName());
			prod.setProductDescription(product.getProductDescription());
			prod.setModelYear(product.getModelYear());
			prod.setListPrice(product.getListPrice());
			prod.setBrand(product.getBrand());
			tempProduct = repository.save(prod);
		});
		return tempProduct;
	}
	
	/*Delete existing product*/	
	public Product deleteProduct(Long id) {
		Optional<Product> product = repository.findById(id);
		product.ifPresent(prod ->{
			repository.delete(prod);
			tempProduct = prod;
		});		
		return tempProduct;
	}
}
