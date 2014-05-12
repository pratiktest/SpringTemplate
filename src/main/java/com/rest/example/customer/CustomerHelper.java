package com.rest.example.customer;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rest.example.customer.bo.CustomerBo;
import com.rest.example.customer.model.Customer;
import com.rest.example.enums.AckValue;
import com.rest.example.initializer.Initializer;
import com.rest.example.pojo.responsePojo.CustomerResponsePojo;
import com.rest.example.util.ResponseHelper;
import com.rest.example.util.Utility;



public class CustomerHelper {

	public static Response getCustomer(String customerIdInString) {
				
		int customerId = Utility.convertStringToInt(customerIdInString);
	    CustomerBo customerBo = (CustomerBo)Initializer.getAppContext()
	    		.getBean("customerBo");
	    	
    	Customer customerInTable  = customerBo.findByCustomerId(customerId);
    	
    	//customer does not exist in database
    	if(customerInTable == null){
    		ResponseHelper.throwWebApplicationException(Status.INTERNAL_SERVER_ERROR, 
    				"customer not present in database", "ErrorCode", AckValue.FAILURE.getValue(), 
    				"Error code 1", "", MediaType.APPLICATION_JSON_TYPE);
    	}
    	CustomerResponsePojo customerResponsePojo = new CustomerResponsePojo();
    	customerResponsePojo.setId(customerInTable.getId());
    	customerResponsePojo.setName(customerInTable.getName());
    	return Response.ok(customerResponsePojo).type(MediaType.APPLICATION_JSON_TYPE).build();
  
	}
	
	public static Response insertCustomer() {
	
	//	    	if(customerInTable != null){
//	    		customerBo.delete(customerInTable);
//	    	}
//	    	
//	    	//customer.setId(TEST_CUSTOMER_ID);
//	    	//customer.setName(TEST_CUSTOMER_NAME);
//	    	
//	    	customerBo.save(customer);
	return null;
	}
	

}
