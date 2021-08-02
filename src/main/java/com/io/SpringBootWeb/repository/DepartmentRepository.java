package com.io.SpringBootWeb.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.io.SpringBootWeb.model.Department;

@Repository
public interface DepartmentRepository extends ElasticsearchRepository<Department, Integer> {





	
}
