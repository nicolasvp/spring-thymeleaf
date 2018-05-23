package com.spring.test.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.test.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long>{

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String term);
	
	// Busca automaticamente con like e insensitive case, solo por el nombre que lleva la funcion
	public List<Product> findByNameLikeIgnoreCase(String term);
}
