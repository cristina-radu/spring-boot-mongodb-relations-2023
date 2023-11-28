package com.company.demo.repository;

import com.company.demo.document.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeMethodNameQueryRepository extends MongoRepository<Employee, String> {

    List<Employee> getEmployeesByGradeIncreaseIn(List<String> grades);

    List<Employee> getEmployeesByGradeIncreaseIn(List<String> grades, Sort sort);

    List<Employee> getEmployeesByGradeIncreaseIn(List<String> grades, Pageable pageable);

    List<Employee> getEmployeeBySalaryGreaterThan(Integer minSalary);

    List<Employee> getEmployeesBySalaryOrGradeName(Integer salary, String gradeName);


}
