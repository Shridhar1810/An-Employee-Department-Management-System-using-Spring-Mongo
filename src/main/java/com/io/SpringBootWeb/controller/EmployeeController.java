package com.io.SpringBootWeb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.SpringBootWeb.model.Department;
import com.io.SpringBootWeb.model.Employee;
import com.io.SpringBootWeb.repository.EmployeeRepository;
import com.io.SpringBootWeb.service.EmployeeService;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
//@RequestMapping("/api")
public class EmployeeController {

    
    @Autowired
    EmployeeService service;
    
    @Autowired
    EmployeeRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200 , "http")));
    
    //Testing purpose only
    @GetMapping("/test")
    public String test(){

        return "Success";
    }

    //Add
    @PostMapping(value="/emp/add")
    public String addEmployee(@RequestBody Employee employee)
    {
        service.add(employee);
        return "Record Added Successfully";
    }
    
    //GetAll
    @Nullable
    @GetMapping(value="/emp/all")
    public List<Employee> getAllEmployees()
    {
    	return service.getAllEmployees();
    }
    
    //GetById
    @GetMapping(value="/emp/{id}")
    public Optional<Employee> getEmployee(@PathVariable Integer id)
    {
    	return service.getEmployee(id);

    }
  
    @GetMapping(value="/empByDepartmentId/{did}")
    @ResponseBody
    public List<Employee> find(@PathVariable Integer did)
    {
    	List<Employee> empResponse = service.findByDepartmentId(did);
    	return empResponse;
    }
    
    @GetMapping(value="/empByDepartmentName/{dname}")
    @ResponseBody
    public List<Employee> find(@PathVariable Employee dname)
    {
    	List<Employee> empResponse = service.findByDepartmentName(dname);
    	return empResponse;
    }
    
    //Update
    @PutMapping(value = "emp/{id}")
    public String updateEmployee(@PathVariable Integer id,@RequestBody Employee employee )
    {
    	Optional<Employee> emp = service.getEmployee(id);
        if(emp.isPresent())
        {
        	service.updateEmployee(id,employee);
        	return "Data Updated Successfully";
        	
        }
        else
        	return "Data not found";
  
    }
    
    //Delete
    @DeleteMapping(value="/emp/{id}")
    public String deleteEmployee(@PathVariable int id)
    {
    	Optional<Employee> emp = service.getEmployee(id);
        if(emp.isPresent())
        {
        	service.deleteEmployee(id);
        	return "Document deleted";
        }
        else
        	return "Data not found";
    }

    
    
    @GetMapping(value = "employee/searchall")
    public List<Employee>searchAllEmployee()
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("employee");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        List<Employee> empList = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                //System.out.println("val"+searchResponse.getHits().getTotalHits().value);
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    empList.add(objectMapper.convertValue(map, Employee.class));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return empList;
    }
/*
    @GetMapping(value = "emp/searchById/{id}")
    public List<Employee> searchEmployeeByID(@PathVariable int id)
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("employee");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("sid",id)));
        searchRequest.source(searchSourceBuilder);
        List<Employee>employeeList =new ArrayList<>();
        SearchResponse searchResponse=null;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            if(searchResponse.getHits().getTotalHits().value > 0)
            {
                //System.out.println("val"+searchResponse.getHits().getTotalHits().value);
                SearchHit [] searchHits = searchResponse.getHits().getHits();
                for(SearchHit hit: searchHits)
                {
                    Map<String,Object> map = hit.getSourceAsMap();
                    employeeList.add(objectMapper.convertValue(map,Employee.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    return employeeList;
    }

    @GetMapping(value = "emp/searchByNH/{name}/{dname}")
    public List<Employee> searchEmployeeByNameAndDname(@PathVariable String name,@PathVariable String dname)
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("emp");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("ename",name)).must(QueryBuilders.matchQuery("dname",dname)));
        searchRequest.source(searchSourceBuilder);
        List<Employee>empList =new ArrayList<>();

        try {
            SearchResponse searchResponse=null;
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            if(searchResponse.getHits().getTotalHits().value > 0)
            {
                //System.out.println("val"+searchResponse.getHits().getTotalHits().value);
                SearchHit [] searchHits = searchResponse.getHits().getHits();
                for(SearchHit hit: searchHits)
                {
                    Map<String,Object> map = hit.getSourceAsMap();
                    empList.add(objectMapper.convertValue(map,Employee.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empList;

    }

    @GetMapping(value = "emp/searchByDname/{dname}")
    public List<Employee> searchEmployeeByDname(@PathVariable String hobby)
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("employee");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("shobby",hobby)));
        searchRequest.source(searchSourceBuilder);
        List<Employee>empList =new ArrayList<>();
        SearchResponse searchResponse=null;
        try {
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            if(searchResponse.getHits().getTotalHits().value > 0)
            {
                //System.out.println("val"+searchResponse.getHits().getTotalHits().value);
                SearchHit [] searchHits = searchResponse.getHits().getHits();
                for(SearchHit hit: searchHits)
                {
                    Map<String,Object> map = hit.getSourceAsMap();
                    empList.add(objectMapper.convertValue(map,Employee.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empList;
    }
*/
    @GetMapping(value = "emp/searchBy/{input}")
    public List<Employee> searchEmployeeByletter(@PathVariable String input)
    {
        String str="_all"+"*"+input+"*";
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("employee");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("sname.keyword", str);
        boolQueryBuilder.must(wildcardQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        List<Employee>empList =new ArrayList<>();

        try {
            SearchResponse searchResponse=null;
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
            if(searchResponse.getHits().getTotalHits().value > 0)
            {
                //System.out.println("val"+searchResponse.getHits().getTotalHits().value);
                SearchHit [] searchHits = searchResponse.getHits().getHits();
                for(SearchHit hit: searchHits)
                {
                    Map<String,Object> map = hit.getSourceAsMap();
                    empList.add(objectMapper.convertValue(map,Employee.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empList;

    }

}
