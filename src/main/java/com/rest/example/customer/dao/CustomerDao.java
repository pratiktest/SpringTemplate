package com.rest.example.customer.dao;

import com.rest.example.customer.model.Customer;

public interface CustomerDao{
	
	void save(Customer customer);
	void update(Customer customer);
	void delete(Customer customer);
	Customer findByCustomerId(int customerId);

}
