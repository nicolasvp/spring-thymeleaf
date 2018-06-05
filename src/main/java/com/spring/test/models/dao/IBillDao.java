package com.spring.test.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.spring.test.models.entity.Bill;

public interface IBillDao extends CrudRepository<Bill, Long> {

}
