package com.company.demo.endpoint;

import com.company.demo.document.Employee;
import com.company.demo.request.GradeIncreaseDomainRequest;
import com.company.demo.request.GradeRequest;
import com.company.demo.service.EmployeeCriteriaQueryService;
import com.company.demo.service.EmployeeMethodNameQueryService;
import com.company.demo.service.EmployeeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("employee")
@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final EmployeeMethodNameQueryService employeeService;
    private final EmployeeQueryService employeeQueryService;
    private final EmployeeCriteriaQueryService employeeCriteriaQueryService;

    @GetMapping
    public List<Employee> getEmployeeList(){
        return employeeService.getEmployeeList();
    }

    @GetMapping("/page")
    public List<Employee> getEmployeeList(@RequestParam int pageNo, @RequestParam int pageSize){
        return employeeService.getEmployeeList(pageNo, pageSize);
    }

    @GetMapping("/sorted")
    public List<Employee> getEmployeeListSorted(){
        return employeeService.getEmployeeListSorted();
    }

    @GetMapping("/page-sorted")
    public List<Employee> getEmployeeListPagedSorted(@RequestParam int pageNo, @RequestParam int pageSize){
        return employeeService.getEmployeeListSorted(pageNo, pageSize);
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @PostMapping(value = "/grades")
    public List<Employee> findGrades(@RequestBody GradeRequest gradeRequest){
/*        List<String> grades = new ArrayList<>();
        grades.add("Senior");
        grades.add("Consultant");*/
        return employeeService.findByGrades(gradeRequest.getGrades());
    }

    @GetMapping("/salary")
    public List<Employee> findByMinSalary(@RequestParam Integer salary){
      //  Integer salary = 3000;
        return employeeService.findBySalaryGraterThan(salary);
    }

    @GetMapping("/salary-grade")
    public List<Employee> findBySalaryOrGrade(@RequestParam Integer salary, @RequestParam String grade){
/*        Integer salary = 2000;
        String gradeName = "Consultant";*/
        return employeeService.findBySalaryOrGrade(salary, grade);
    }

    @GetMapping("/query/address")
    public List<Employee> getByAddressNo(@RequestParam Integer addressNo){
        return employeeQueryService.getByAddressNo(addressNo);
    }

    @GetMapping("/query/department-grade")
    public List<Employee> getByDepartmentAndGrade(@RequestParam String department,
                                                  @RequestParam String grade){
        return employeeQueryService.getByDepartmentAndGrade(department, grade);
    }

    @GetMapping("/query/department-grade-pageable")
    public List<Employee> getByDepartmentAndGradePageable(@RequestParam String department,
                                                  @RequestParam String grade, @RequestParam int pageNo,
                                                          @RequestParam int pageSize){
        return employeeQueryService.getByDepartmentAndGradePageable(department, grade, pageNo, pageSize);
    }

    @GetMapping("/criteria-query/city-department")
    public void deleteByCityAndDepartment(@RequestParam String city,
                                                  @RequestParam String department){
         employeeCriteriaQueryService.deleteBy(city, department);
    }

    @PutMapping("/criteria-query")
    public void gradeIncrease(@RequestBody GradeIncreaseDomainRequest request) {
        employeeCriteriaQueryService.gradeIncrease(request.getSalary(),
                request.getCurrency(), request.getGrade(), request.getDepartment(), request.getNewSalaryIncrease(),
                request.getNewCurrency(), request.getNewGrade());
    }

    @GetMapping("/aggregate")
    public  void test(){
        List<String> deps = new ArrayList<>();
        deps.add("Development");
        deps.add("DevOps");
        employeeCriteriaQueryService.getSumByDepartment(deps);
    }

    @GetMapping("/aggregate-lookup")
    public  List<String> aggregateLookup(){
        return employeeCriteriaQueryService.getLookUpAggregation();
    }


    @GetMapping("/aggregate-lookup-unwind")
    public  List<String> aggregateLookupUnwind(){
        return employeeCriteriaQueryService.getLookUpAggregationGradeIncrease();
    }
}
