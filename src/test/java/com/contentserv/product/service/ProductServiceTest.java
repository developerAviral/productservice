package com.contentserv.product.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);		
		when(repository.save(Mockito.any(Product.class))).thenReturn(mockProduct);
		Product product1 = service.createProduct(mockProduct);
		verify(repository, atLeastOnce()).save(mockProduct);
	}
	
	@Test
	public void updateProduct() {
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockProduct));
		when(repository.save(Mockito.any(Product.class))).thenReturn(mockProduct);
		Product product1 = service.updateProduct(1L,mockProduct);
		verify(repository,times(1)).save(Mockito.any(Product.class));
	}
	
	@Test
	public void getProduct() {
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockProduct));
		Product product1 = service.getProduct(1L);
		verify(repository, atLeastOnce()).findById(Mockito.anyLong());
	}
	
	@Test
	public void getProducts() {
		Product mockProduct1 = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		Product mockProduct2 = new Product(2L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		List<Product> products = new ArrayList<>();
		products.add(mockProduct1);
		products.add(mockProduct2);
		Iterable<Product> itr = products;		
		when(repository.findAll()).thenReturn(itr);
		Iterable<Product> product1 = service.getProducts();
		verify(repository, atLeastOnce()).findAll();
	}
	
	@Test
	public void deleteProduct() {
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockProduct));
		//doNothing().when(Mockito.spy(repository.delete(Mockito.any(Product.class))));
		Product product1 = service.deleteProduct(1L);
		verify(repository, atLeastOnce()).delete(Mockito.any(Product.class));
	}
}
