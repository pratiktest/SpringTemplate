package com.rest.example.customer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * 
 * @author prkale
 *
 * @XmlRootElement is used with a top level class or an enum
 * This will associate an element with an XML Schema Type
 * To get a name in xml for tht type use name attribute name = ""
 * target is type retention is runtime
 * leaf classes need not be annotated if root is annotated @XmlRootElement
 * 
 * @Table specifies the primary table for the entity
 * 
 * @Entity specifies that class is an entity. The name can be used in query
 * 
 * @Column specifies the column name to be used in query. @Id 
 * 
 * @GeneratedValue is used to specify the value of primary key. Identity generates an automatic value 
 * during commit.
 * 
 */
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String address;
	private Boolean isActive;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "address")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "isActive")
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
