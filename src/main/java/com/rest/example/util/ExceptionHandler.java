package com.rest.example.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.rest.example.enums.AckValue;

@Component
@EnableAspectJAutoProxy
@Aspect
public class ExceptionHandler {
	
	@AfterThrowing(pointcut = "execution(* com.rest.example.customer.bo.*.*(..))", throwing = "e")
	public void handleBoException(final JoinPoint jp, final Exception e){
		
		Logger logger = LoggerFactory.getLogger(jp.getClass());
		logger.debug("Exception occured in "+ jp.getSourceLocation().toString() );
		
		ResponseHelper.throwWebApplicationException(Status.INTERNAL_SERVER_ERROR, 
				"Exception occured in "+ jp.getStaticPart().toShortString(), "ErrorCode", AckValue.FAILURE.getValue(), 
				"", "", MediaType.APPLICATION_JSON_TYPE);
		
	}
	
	

}
