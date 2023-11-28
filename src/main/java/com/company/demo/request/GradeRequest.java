package com.company.demo.request;

import lombok.Data;

import java.util.List;

@Data
public class GradeRequest {
    private List<String> grades;
}
