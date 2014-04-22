package com.rest.example.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rest.example.enums.AckValue;
import com.rest.example.responsePojo.ErrorPojo;
import com.rest.example.responsePojo.ResponsePojo;


/**
 * 
 * @author prkale
 * 
 * This class is a generic application response and will contain success failure status
 * This object will be emmitted in every response
 */
public class ResponseHelper {
	
	public static Response constructResponse(Object o, AckValue ackValue){
		
		ResponsePojo response = new ResponsePojo();
		response.setAckValue(AckValue.SUCCESS);
		response.setResponseObject(o);
		
		return Response.ok(response).type(MediaType.APPLICATION_JSON_TYPE).build();
		
	}
	
	
	public static void throwWebApplicationException(Status status,
			String errorString, String errorCode, String errorSeverity, 
			String module, String eventLogging, MediaType mediaType) {

		WebApplicationException we = new WebApplicationException(
				constructExceptionResponse(
						status, errorString,
						errorCode, MediaType.APPLICATION_JSON," /"+module+"/", mediaType));
	
		throw we;
	}


	private static Response constructExceptionResponse(Status status,
			String message, String errorCode, String applicationJson,
			String module, MediaType mediatype) {
		
		ResponsePojo responsePojo = new ResponsePojo();
		responsePojo.setAckValue(AckValue.FAILURE);
		responsePojo.setResponseObject(buildAndGetErrorData(message, errorCode, module));
		
		return Response.status(status).entity(responsePojo)
				.type(mediatype).build();
		
	}
	
	private static ErrorPojo buildAndGetErrorData(
			String message, 
			String errorCode,
			String errorParam){

		ErrorPojo errorObj = new ErrorPojo();
		errorObj.setErrorCode(errorCode);
		errorObj.setMessage(message);
		errorObj.setErrorParam(errorParam);

		return errorObj;
	}

	
}
