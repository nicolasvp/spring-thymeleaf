package com.spring.test.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.test.models.dao.IBillDao;
import com.spring.test.models.dao.IClientDao;
import com.spring.test.models.dao.IProductDao;
import com.spring.test.models.entity.Bill;
import com.spring.test.models.entity.Client;
import com.spring.test.models.entity.Product;

@Service
public class ClientServiceImpl implements IClientService{

	@Autowired
	private IClientDao clientDao;
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IBillDao billDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);		
	}

	@Override
	@Transactional(readOnly=true)
	public Client findOne(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return clientDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findByName(String term) {
		// TODO Auto-generated method stub
		return productDao.findByNameLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveBill(Bill bill) {
		// TODO Auto-generated method stub
		billDao.save(bill);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Bill findBillById(Long id) {
		return billDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteBill(Long id) {
		billDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Bill fetchByIdWithClientWithBillItemWithProduct(Long id) {
		// TODO Auto-generated method stub
		return billDao.fetchByIdWithClientWithBillItemWithProduct(id);
	}

}
