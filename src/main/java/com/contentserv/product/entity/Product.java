package com.contentserv.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	@NotNull
	@Size(max = 100, message = "Product name should not exceed length of 100 characters.")
	@Column(name = "product_name")
	private String productName;
	
	@NotNull
	@Size(max=400, message="Product description should not exceed length of 400 characters.")
	@Column(name = "product_description")
	private String productDescription;
	
	@NotNull(message = "Brand field should not be null.")
	@NotBlank(message = "Brand field should not be blank.")
	@Column(name = "product_brand")
	private String brand;
	
	@NotNull
	//@Pattern(regexp = "@\\d{4}", message = "Please provide model year in format YYYY.")
	@Column(name = "model_year")
	private String modelYear;
	
	@NotNull
	//@Pattern(regexp = "@\"^\\d$\"", message = "Please provide valid value for List Price.")
	@Column(name = "list_price")
	private double listPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productDescription=" + productDescription
				+ ", brand=" + brand + ", modelYear=" + modelYear + ", listPrice=" + listPrice + "]";
	}
	
	
}
