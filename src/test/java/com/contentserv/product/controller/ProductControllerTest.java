package com.contentserv.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
