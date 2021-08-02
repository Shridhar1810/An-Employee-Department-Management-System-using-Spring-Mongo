package com.io.SpringBootWeb.model;

import jdk.jfr.DataAmount;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.stereotype.Component;






//@Entity
@Document(indexName = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eid;

    @Field(type= FieldType.Text,name="ename")
    private String ename;
    
                                               
//    @ManyToOne(cascade=CascadeType.ALL)
//    @JoinField(name="did")
    @Field(type = FieldType.Nested, includeInParent = true)
    private Department  department;


	public Employee(int eid, String ename,Department department) {
		super();
		this.eid = eid;
		this.ename = ename;
		this.department = department;
	}


	public int getEid() {
		return eid;
	}


	public void setEid(int eid) {
		this.eid = eid;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}
	
    
	
    
   
	

   
}


