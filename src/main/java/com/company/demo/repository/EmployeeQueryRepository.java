package com.company.demo.repository;

import com.company.demo.document.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeQueryRepository extends MongoRepository<Employee, String> {
    @Query("{'address.no': :#{#addressNo}}")
    List<Employee> getBy(Integer addressNo);


    @Query("{" +
            "'department.name':  ?0," +
            " 'grade.name': ?1 " +
            "}")
    List<Employee> getBY(String department, String grade);

    @Query("{" +
            "'department.name':  ?0," +
            " 'grade.name': ?1 " +
            "}")
    List<Employee> getByPageable(String department, String grade, Pageable pageable);
}
