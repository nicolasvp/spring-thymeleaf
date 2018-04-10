package com.spring.test.models.service;

import java.util.List;

import com.spring.test.models.entity.Client;

public interface IClientService {
	public List<Client> findAll();
	
	public void save(Client client);
	
	public Client findOne(Long id);
	
	public void delete(Long id);
}
