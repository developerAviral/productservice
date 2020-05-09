package com.contentserv.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.contentserv.product.entity.Product;
import com.contentserv.product.exception.ProductNotFoundException;
import com.contentserv.product.service.ProductService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProductServiceApplicationTests {

	@Autowired
	private ProductService productService;
	
	@Test
	@Order(3)
	public void test_getProduct() {
		Product product = productService.getProduct(1L);
		
		assertEquals("LG", product.getBrand());
		assertEquals("AC", product.getProductName());
		assertEquals("A very cool effective AC", product.getProductDescription());
		assertEquals(25000.0, product.getListPrice());
		assertEquals("2018", product.getModelYear());
	}
	
	@Test
	@Order(2)
	public void test_getAllProducts() {
		List<Product> products = productService.getProducts();
		Iterator<Product> itr= products.iterator();
		
		while(itr.hasNext()) {
			Product product = itr.next();
			System.out.println("Product Details: ");
			System.out.println(product);
		}
	}
	
	@Test
	@Order(1)
	public void test_createProduct() {
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
		
		Product savedProduct1 = productService.createProduct(product1);
		Product savedProduct2 = productService.createProduct(product2);
		
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
	@Order(4)
	public void test_updateProduct() {
		Product product = productService.getProduct(2L);
		product.setBrand("Videocon");
		Product updatedProduct = productService.updateProduct(product.getId(), product);
		
		assertEquals("Videocon", updatedProduct.getBrand());
	}
	
	@Test
	@Order(5)
	public void test_deleteProduct() {
		productService.deleteProduct(2L);
		Exception exception = assertThrows(ProductNotFoundException.class, ()->{
			productService.getProduct(2L);
		});
		
		String expectedMessage = "Product not found";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

}
