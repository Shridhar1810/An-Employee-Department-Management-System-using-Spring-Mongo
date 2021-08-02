package com.io.SpringBootWeb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.SpringBootWeb.model.Department;
import com.io.SpringBootWeb.repository.DepartmentRepository;



@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	

	public void add(Department department) {
		
		departmentRepository.save(department);
		
	}
	
	public Optional<Department> getDepartment(int id){
		//return employees.stream().filter(e->e.getEmployeeId()==(id)).findFirst().get();
		return departmentRepository.findById(id);
		
	}

	public void updateDepartment(int id, Department department) {
	      		Optional<Department> dp= departmentRepository.findById(id);
	      		Department d=dp.get();
	            d.setDepartmentName(department.getDepartmentName());
	            d.setDepartmentId(department.getDepartmentId());
	          
	            departmentRepository.save(d);
	
	}
	
	public List<Department> getAllDepartments() {
		List<Department> dpList = new ArrayList<>();
	    
    	if(departmentRepository.findAll() != null) {
    		
        Iterator<Department> iterator = departmentRepository.findAll().iterator();
       
        	while(iterator.hasNext())
        	{
        		dpList.add(iterator.next());
        	}
    	
    	}
        return  dpList;
    	
	}
	public void deleteDepartment(int id) {
		departmentRepository.deleteById(id);
		
	}
}
