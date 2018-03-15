package com.spring.test.models.dao;

import java.util.List;

import com.spring.test.models.entity.Client;

public interface IClientDao {

	public List<Client> findAll();
	
}
