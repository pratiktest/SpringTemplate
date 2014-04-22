
package com.rest.example.customer;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rest.example.customer.bo.CustomerBo;
import com.rest.example.customer.model.Customer;

@Component
@Path("/Customer")
public class CustomerResource {
	
	final int TEST_CUSTOMER_ID = 1;
	final String TEST_CUSTOMER_NAME = "pratik";
	
	@Context ServletContext servletContext;
    
    @GET 
    @Produces("application/json")
    @Path("/insert")
    public Response insertCustomer()  {
    	
    	Customer customer = new Customer();

	    	WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    	
	    	CustomerBo customerBo = (CustomerBo)appContext.getBean("customerBo");
	    	
	    	Customer customerInTable  = customerBo.findByCustomerId(TEST_CUSTOMER_ID);
	    	if(customerInTable != null){
	    		customerBo.delete(customerInTable);
	    	}
	    	
	    	
	    	//customer.setId(TEST_CUSTOMER_ID);
	    	customer.setName(TEST_CUSTOMER_NAME);
	    	
	    	customerBo.save(customer);
  
	    	
        return Response.status(201).entity(customer).build();
    }
}
