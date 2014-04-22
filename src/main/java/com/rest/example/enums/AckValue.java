package com.rest.example.enums;

public enum AckValue {
	
	SUCCESS("Success"), FAILURE("Failure"), WARNING("Warning");

	private String value;
	
	@Override
	public String toString(){
		return this.value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public AckValue fromValue(String value) throws IllegalArgumentException{
		
		for(AckValue ackValue: AckValue.values()) {
			if(ackValue.value.equals(value)){
				return ackValue;
			}
		}
		
		throw new IllegalArgumentException("the value "+ value + " is not present");
	}
	
	private AckValue(String value){
		this.value = value;
	}
}
