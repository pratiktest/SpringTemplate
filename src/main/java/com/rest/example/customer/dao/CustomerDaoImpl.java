package com.rest.example.customer.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.customer.model.Customer;

/**
 * @author prkale
 *
 * Here we see the @Repository annotation in action
 * This annotation is applied to CustomerDao Implementation.
 * 
 * @Repository has a target TYPE ... Retention is RUNTIME.. 
 * It is a specialization of @Component so it is autodetected through classpath scanning
 * 
 * The String value "customerDoa" indicates the component name to be turned into a spring bean
 * So we can retrieve this bean using customerDao.
 * 
 * A Repository is eligible for Spring DataAcessException
 */

@Repository("customerDao")
@EnableTransactionManagement
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;
	 
	@Transactional
	public Customer findByCustomerId(int customerId) {
		  return (Customer) sessionFactory.getCurrentSession().get(Customer.class, customerId);
	}


	@Transactional
	public void save(Customer customer) {
		sessionFactory.getCurrentSession().save(customer);
	}

	@Transactional
	public void update(Customer customer) {
		sessionFactory.getCurrentSession().update(customer);
		
	}

	@Transactional
	public void delete(Customer customer) {
		sessionFactory.getCurrentSession().delete(customer);
		
	}

}
