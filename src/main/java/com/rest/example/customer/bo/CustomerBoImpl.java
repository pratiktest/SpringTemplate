package com.rest.example.customer.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.example.customer.model.Customer;
import com.rest.example.customer.dao.CustomerDao;

@Service("customerBo")
public class CustomerBoImpl implements CustomerBo {

	@Autowired
	CustomerDao customerDao;
	
	
	public void save(Customer customer) {
		customerDao.save(customer);
		
	}

	public void update(Customer customer) {
		customerDao.update(customer);
		
	}

	public void delete(Customer customer) {
		customerDao.delete(customer);
		
	}

	public Customer findByCustomerId(int customerId) {
		return customerDao.findByCustomerId(customerId);
	}

}
