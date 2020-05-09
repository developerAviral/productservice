package com.contentserv.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.contentserv.product.entity.Product;
import com.contentserv.product.exception.ProductNotFoundException;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@Order(1)
	public void createFirstProduct() {
		Product product = new Product("AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Product> request = new HttpEntity<>(product, httpHeaders);
		ResponseEntity<String> result = this.restTemplate.postForEntity("/products", request, String.class);
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	@Order(2)
	public void createSecondProduct() {
		Product product = new Product("TV", "OLED android tv", "LG", "2019", 50004.19);
		HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Product> request = new HttpEntity<>(product, httpHeaders);
		ResponseEntity<String> result = this.restTemplate.postForEntity("/products", request, String.class);
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	@Order(3)
	public void getProductById() {
		Product product = this.restTemplate.getForObject("/products/2", Product.class);
		
		assertEquals("OLED android tv", product.getProductDescription());
		assertEquals("TV", product.getProductName());
		assertEquals("LG", product.getBrand());
		assertEquals("2019",product.getModelYear() );
		assertEquals(50004.19, product.getListPrice());
	}
	
	@Test
	@Order(4)
	public void getAllProducts() {
		Product[] products = this.restTemplate.getForObject("/products", Product [].class);
		for (int i = 0; i < products.length; i++) {
			System.out.println(products[i].getId() + "\t" + products[i].getProductName());
		}
	}
	
	@Test
	@Order(5)
	public void updateProduct() {
		
		Product product = new Product(2L, "TV", "OLED android tv", "LG", "2019", 50004.19);
		
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Product> request = new HttpEntity<>(product, headers);
		
		ResponseEntity<Product> result = this.restTemplate.exchange("/products/1", HttpMethod.PUT,
				request, Product.class, product);
		
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	@Order(6)
	public void deleteProduct() {
		this.restTemplate.delete("/products/1");
		Product product = this.restTemplate.getForObject("/products/1", Product.class);
		assertEquals(null, product.getId());
	}
	
}
