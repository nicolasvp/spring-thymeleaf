package com.spring.test.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.spring.test.models.entity.Client;

public interface IClientDao extends PagingAndSortingRepository<Client, Long>{

}
