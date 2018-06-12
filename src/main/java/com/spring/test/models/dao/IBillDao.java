package com.spring.test.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.test.models.entity.Bill;

public interface IBillDao extends CrudRepository<Bill, Long> {

	@Query("select b from Bill b join fetch b.client c join fetch b.items l join fetch l.product where b.id=?1") // 1 es el primer parametro, si hay 2 params entonces 2
	public Bill fetchByIdWithClientWithBillItemWithProduct(Long id);
}
