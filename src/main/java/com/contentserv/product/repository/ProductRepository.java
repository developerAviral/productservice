package com.contentserv.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.contentserv.product.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
