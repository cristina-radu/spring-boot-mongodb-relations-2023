package com.company.demo.service;

import com.company.demo.document.Employee;
import com.company.demo.repository.EmployeeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeQueryService {
    private final EmployeeQueryRepository employeeQueryRepository;

    public List<Employee> getByAddressNo(Integer addressNo){
        return employeeQueryRepository.getBy(addressNo);
    }

    public List<Employee> getByDepartmentAndGrade(String department, String grade){
        return employeeQueryRepository.getBY(department, grade);
    }

    public List<Employee> getByDepartmentAndGradePageable(
            String department, String grade, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        return employeeQueryRepository.getByPageable(department, grade, pageable);
    }
}
