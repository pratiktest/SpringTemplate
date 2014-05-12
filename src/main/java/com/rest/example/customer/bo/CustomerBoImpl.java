package com.rest.example.customer.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.customer.model.Customer;
import com.rest.example.customer.dao.CustomerDao;

/**
 * @author prkale
 *
 * Here we have used the @Service annotation
 * @Service annotation is again a specialization of @component and is sutodetected by classpath scanning
 *
 * The String value indicates the name with which this bean will be created in context
 *
 * @Service has target TYPE and Retention is RUNTIME. This serves as a business Service Facade.
 * It is an ideal target for pointcut.
 *
 * We have also used the @Autowired annotation.
 * This annotation has target as CONSTRUCTOR METHOD or FIELD. Here we have used a field dependency injection
 * If we have a autowire over a setter method each argument of the method will be autowired with a bean in
 * the Spring container. Such a method acts as a configuration method.
 * 
 *  required in @autowire will specify if the dependency is required or not.
 */
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
