package com.rest.example.responsePojo;

import com.rest.example.enums.AckValue;

public class ResponsePojo {
	
	private Object responseObject;
	private AckValue ackValue;
	
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
	public AckValue getAckValue() {
		return ackValue;
	}
	public void setAckValue(AckValue ackValue) {
		this.ackValue = ackValue;
	}

}
