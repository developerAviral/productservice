package com.contentserv.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.contentserv.product.entity.Product;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	@Test
	@Order(1)
	public void createProduct() {
		Product product1 = new Product();
		Product product2 = new Product();
		
		product1.setBrand("LG");
		product1.setProductName("AC");
		product1.setProductDescription("A very cool effective AC");
		product1.setListPrice(25000.00);
		product1.setModelYear("2018");
		
		product2.setBrand("Sony");
		product2.setProductName("TV");
		product2.setProductDescription("A OLED television with Android OS.");
		product2.setListPrice(85000.00);
		product2.setModelYear("2019");
		
		Product savedProduct1 = repository.save(product1);
		Product savedProduct2 = repository.save(product2);
		
		assertEquals(product1.getBrand(), savedProduct1.getBrand());
		assertEquals(product1.getProductName(), savedProduct1.getProductName());
		assertEquals(product1.getProductDescription(), savedProduct1.getProductDescription());
		assertEquals(product1.getListPrice(), savedProduct1.getListPrice());
		assertEquals(product1.getModelYear(), savedProduct1.getModelYear());
		
		assertEquals(product2.getBrand(), savedProduct2.getBrand());
		assertEquals(product2.getProductName(), savedProduct2.getProductName());
		assertEquals(product2.getProductDescription(), savedProduct2.getProductDescription());
		assertEquals(product2.getListPrice(), savedProduct2.getListPrice());
		assertEquals(product2.getModelYear(), savedProduct2.getModelYear());
	}
	
	@Test
	@Order(2)
	public void getProductById() {
		Optional<Product> products = repository.findById(1L);
		
		products.ifPresent(product ->{
		assertEquals("LG", product.getBrand());
		assertEquals("AC", product.getProductName());
		assertEquals("A very cool effective AC", product.getProductDescription());
		assertEquals(25000.0, product.getListPrice());
		assertEquals("2018", product.getModelYear());
		});
	}
	
	@Test 
	@Order(3)
	public void getAllProducts() {
		Iterable<Product> products = repository.findAll();
		Iterator<Product> itr= products.iterator();
		
		while(itr.hasNext()) {
			Product product = itr.next();
			System.out.println("Product Details: ");
			System.out.println(product);
		}
	}
	
	@Test
	@Order(4)
	public void updateProduct() {
		Optional<Product> products = repository.findById(2L);
		
		products.ifPresent(product ->{
		product.setBrand("Videocon");
		Product updatedProduct = repository.save(product);		
		assertEquals("Videocon", updatedProduct.getBrand());
		});
	}
	
	@Test 
	@Order(5)
	public void deleteProduct() {
		Optional<Product> products = repository.findById(2L);
		products.ifPresent(prod ->{
			repository.delete(prod);
		});
		
		assertThrows(NoSuchElementException.class, ()->{
			Optional<Product> deletedProduct = repository.findById(2L);
			deletedProduct.get();
		});
	}
}
