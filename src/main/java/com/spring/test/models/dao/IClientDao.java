package com.spring.test.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.spring.test.models.entity.Client;

public interface IClientDao extends CrudRepository<Client, Long>{

}
