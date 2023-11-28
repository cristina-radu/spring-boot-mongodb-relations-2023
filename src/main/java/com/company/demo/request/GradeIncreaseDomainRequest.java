package com.company.demo.request;

import lombok.Data;

@Data
public class GradeIncreaseDomainRequest {
    private Integer salary;
    private String currency;
    private String grade;
    private String department;
    private Integer newSalaryIncrease;
    private String newCurrency;
    private String newGrade;
}
