
package com.rest.example.customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/customer")
public class CustomerResource {
	
	final int TEST_CUSTOMER_ID = 1;
	final String TEST_CUSTOMER_NAME = "pratik";

    
    @GET 
    @Produces("application/json")
    @Path("/getCustomer/{customerId}")
    public Response insertCustomer(@PathParam("customerId") String customerId)  {
    	
    	return CustomerHelper.getCustomer(customerId);
    	
    }
}
