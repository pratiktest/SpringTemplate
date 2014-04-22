package com.rest.example.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rest.example.customer.model.Customer;
import com.rest.example.util.CustomHibernateDaoSupport;

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
