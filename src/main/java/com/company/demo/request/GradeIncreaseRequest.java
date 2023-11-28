package com.company.demo.request;

import lombok.Data;

@Data
public class GradeIncreaseRequest {
    private Integer salary;
    private String currency;
    private String grade;
    private String department;
    private Integer newSalary;
    private String newCurrency;
    private String newGrade;
}
