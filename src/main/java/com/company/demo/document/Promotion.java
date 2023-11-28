package com.company.demo.document;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Data
@Document
public class Promotion {
    @Id
    private String id;
    @Field
    private Integer salary;
    @Field
    private String currency;
    @Field
    private String gradeIncrease;
    @Field
    private ObjectId employeeId;
    @Field
    private String date;
}
