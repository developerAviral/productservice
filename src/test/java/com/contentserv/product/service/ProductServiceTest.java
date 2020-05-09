package com.contentserv.product.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.contentserv.product.entity.Product;
import com.contentserv.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Test
	public void createProduct() {
		Product product = new Product();
		
		product.setBrand("LG");
		product.setProductName("AC");
		product.setProductDescription("A very cool effective AC");
		product.setListPrice(25000.00);
		product.setModelYear("2018");
		
		when(repository.save(product)).thenReturn(product);
		Product product1 = service.createProduct(product);
		verify(repository, atLeastOnce()).save(product);
	}
}
