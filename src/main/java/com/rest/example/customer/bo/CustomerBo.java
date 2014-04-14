package com.rest.example.customer.bo;

import com.rest.example.customer.model.Customer;

public interface CustomerBo {
	
	void save(Customer customer);
	void update(Customer customer);
	void delete(Customer customer);
	Customer findByCustomerId(int id);

}
