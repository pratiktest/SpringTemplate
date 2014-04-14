package com.rest.example.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import util.CustomHibernateDaoSupport;

import com.rest.example.customer.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends CustomHibernateDaoSupport implements CustomerDao {

	public void save(Customer customer) {
		getHibernateTemplate().save(customer);
		
	}

	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
		
	}

	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);
		
	}

	public Customer findByCustomerId(int customerId) {
		List<?> list = getHibernateTemplate().find("from Customer where customerId=?", customerId);
		return (Customer)(list.get(0));
	}

}
