package com.io.SpringBootWeb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.SpringBootWeb.model.Department;
import com.io.SpringBootWeb.model.Employee;
import com.io.SpringBootWeb.repository.DepartmentRepository;
import com.io.SpringBootWeb.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	

	

	public void add(Employee employee) {
		
		employeeRepository.save(employee);
		departmentRepository.save(employee.getDepartment());
		
		
		
	}
	
	public Optional<Employee> getEmployee(int id){
//		return employees.stream().filter(e->e.getEmployeeId()==(id)).findFirst().get();
		
		return employeeRepository.findById(id);
		
	}

	public void updateEmployee(int id, Employee employee  ) {
	      		Optional<Employee> emp= employeeRepository.findById(id);
	      		Optional<Department> dp = departmentRepository.findById(employee.getDepartment().getDepartmentId());
	      		
	      		Employee e =emp.get();
//	      		Department d=dp.get();
//	      		d.setDepartmentId(employee.getDepartment().getDepartmentId());
//	      		d.setDepartmentName(employee.getDepartment().getDepartmentName());
	            e.setEname(employee.getEname());
	            e.setDepartment(employee.getDepartment());
	            employeeRepository.save(e);
	            
	            departmentRepository.save(employee.getDepartment());
	            
	
	}
	

	public List<Employee> getAllEmployees() {
		List<Employee> empList = new ArrayList<>();
	    
    	if(employeeRepository.findAll() != null) {
    		
        Iterator<Employee> iterator = employeeRepository.findAll().iterator();
       
        	while(iterator.hasNext())
        	{
        		empList.add(iterator.next());
        	}
    	
    	}
        return  empList;
    	
	}
	public void deleteEmployee(int id) {
		employeeRepository.deleteById(id);
		
	}

	public List<Employee> findByDepartmentId(Integer did) {
		
		List<Employee> empList = new ArrayList<>();
	
    	if(employeeRepository.findById(did) != null) {
    		
        Iterator<Employee> iterator = employeeRepository.findAll().iterator();
       
        	while(iterator.hasNext())
        	{
        		empList.add(iterator.next());
        	}
    	
    	}
        return  empList;
		
		
	}

//	public List<Employee> findByDepartmentName(String dname) {
//		List<Employee> empList = new ArrayList<>();
//		
//    	if(employeeRepository.findById(dname) != null) {
//    		
//        Iterator<Employee> iterator = employeeRepository.findAll().iterator();
//       
//        	while(iterator.hasNext())
//        	{
//        		empList.add(iterator.next());
//        	}
//    	
//    	}
//        return  empList;
//	}



	public List<Employee> findByDepartmentName(Employee dname) {
		List<Employee> response =(List<Employee>) employeeRepository.findByDepartmentName(dname.getDepartment().getDepartmentName());
		return response;
		
	}



}
