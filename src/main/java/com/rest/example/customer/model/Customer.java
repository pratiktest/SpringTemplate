package com.rest.example.customer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	@Id
	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customerId", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 10)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
