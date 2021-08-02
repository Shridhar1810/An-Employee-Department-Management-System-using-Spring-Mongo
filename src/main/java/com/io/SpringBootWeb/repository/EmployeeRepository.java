package com.io.SpringBootWeb.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.io.SpringBootWeb.model.Employee;


@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Integer> {

//	List findById(String dname);

	@Query(value = "", name = "")
	List<Employee> findByDepartmentName(String string);

	


	
}
