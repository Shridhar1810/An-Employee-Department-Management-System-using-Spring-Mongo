package com.io.SpringBootWeb.controller;

import java.util.List;
import java.util.Optional;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.SpringBootWeb.model.Department;
import com.io.SpringBootWeb.model.Employee;
import com.io.SpringBootWeb.service.DepartmentService;
import com.io.SpringBootWeb.service.EmployeeService;

@RestController
public class DepartmentController {


    @Autowired
    DepartmentService service;

     
 

    //Add
    @PostMapping(value="/dp/add")
    public String addDepartment(@RequestBody Department department)
    {
        service.add(department);
        return "Record Added Successfully";
    }
    
    //GetAll
    @Nullable
    @GetMapping(value="/dp/all")
    public List<Department> getAllDepartments()
    {
    	return service.getAllDepartments();
    }
    
    //GetById
    @GetMapping(value="/dp/{id}")
    public Optional<Department> getDepartment(@PathVariable Integer id)
    {
    	return service.getDepartment(id);

    }
    
    //Update
    @PutMapping(value = "dp/{id}")
    public String updateDepartment(@PathVariable Integer id,@RequestBody Department department)
    {
    	Optional<Department> dp = service.getDepartment(id);
        if(dp.isPresent())
        {
        	service.updateDepartment(id,department);
        	return "Data Updated Successfully";
        }
        else
        	return "Data not found";
  
    }
    
    //Delete
    @DeleteMapping(value="/dp/{id}")
    public String deleteDepartment(@PathVariable int id)
    {
    	Optional<Department> emp = service.getDepartment(id);
        if(emp.isPresent())
        {
        	service.deleteDepartment(id);
        	return "Document deleted";
        }
        else
        	return "Data not found";
    }
}
