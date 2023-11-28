package com.company.demo.service;

import com.company.demo.document.Employee;
import com.company.demo.document.Promotion;
import com.company.demo.repository.EmployeeMethodNameQueryRepository;
import com.company.demo.repository.PromotionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeMethodNameQueryService {
    private final PromotionsRepository promotionsRepository;
    private final EmployeeMethodNameQueryRepository employeeRepository;

    public List<Employee> getEmployeeList(){
        List<Promotion> promotions = promotionsRepository.findAll();
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeeListSorted(){
        Sort sort = Sort.by(Sort.Direction.DESC, "salary").and(Sort.by(Sort.Direction.DESC, "firstName"));
        return employeeRepository.findAll(sort);
    }

    public List<Employee> getEmployeeListSorted(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "salary").
                        and(Sort.by(Sort.Direction.DESC, "firstName")));
        return employeeRepository.findAll(pageable).getContent();
    }

    public List<Employee> getEmployeeList(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1,  pageSize);
        return employeeRepository.findAll(pageable).getContent();
    }

    public Employee saveEmployee(Employee employee){
        Employee dbEmployee = employeeRepository.save(employee);
        if(employee.getPromotions() != null && employee.getPromotions().size()>0){
            employee.getPromotions().forEach(promotion -> promotion.setEmployeeId(dbEmployee.getId()));
            promotionsRepository.saveAll(employee.getPromotions());
        }
        return dbEmployee;
    }

    public List<Employee> findByGrades(List<String> grades){
        return employeeRepository.getEmployeesByGradeIncreaseIn(grades);
    }

    public List<Employee> findBySalaryGraterThan(Integer minSalary){
        return employeeRepository.getEmployeeBySalaryGreaterThan(minSalary);
    }

    public List<Employee> findBySalaryOrGrade(Integer salary, String gradeName){
        return employeeRepository.getEmployeesBySalaryOrGradeName(salary, gradeName);
    }


}
