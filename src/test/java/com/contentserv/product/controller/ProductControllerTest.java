package com.contentserv.product.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.contentserv.product.entity.Product;
import com.contentserv.product.repository.ProductRepository;
import com.contentserv.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class) 
@WebMvcTest(ProductController.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;

	@MockBean
	private ProductRepository repository;
	
	@Test
	public void saveProduct() throws Exception {

		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);

		Mockito.when(service.createProduct(Mockito.any(Product.class))).thenReturn(mockProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products").accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(mockProduct)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(201, response.getStatus());

	}
	
	@Test
	public void getProduct() throws Exception{
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		Mockito.when(service.getProduct(1L)).thenReturn(mockProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{id}", 1).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void getProducts() throws Exception{
		Product mockProduct1 = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		Product mockProduct2 = new Product(2L, "TV", "Android 52 inch teleivison", "LG", "2019", 54304.93);
		List<Product> products = new ArrayList<>();
		products.add(mockProduct1);
		products.add(mockProduct2);
		
		Mockito.when(service.getProducts()).thenReturn(products);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void updateProducts() throws Exception{
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);
		Mockito.when(service.updateProduct(Mockito.anyLong(), Mockito.any(Product.class) )).thenReturn(mockProduct);	
		Mockito.when(repository.save(mockProduct)).thenReturn(mockProduct);		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockProduct))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
		
		verify(service, times(1)).updateProduct(Mockito.anyLong(), Mockito.any(Product.class));
	}
	
	@Test
	public void deleteProducts() throws Exception{
		Product mockProduct = new Product(1L, "AC", "1.5 ton cooling ac", "sony", "2018", 20004.93);		
		Mockito.when(service.deleteProduct(1L)).thenReturn(mockProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{id}", 1).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(200, response.getStatus());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
