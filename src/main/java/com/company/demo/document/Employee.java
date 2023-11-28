package com.company.demo.document;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Data
@Document
public class Employee {
    @Id
    private ObjectId id;
    @Field
    private String firstName;
    @Field
    private String lastName;
    @DocumentReference
    private Department department;
    @DocumentReference
    private Grade grade;
    @Field
    private Integer salary;
    @Field
    private String currency;
    @Field
    private Address address;
    @Field
    private String employmentDate;
    @DocumentReference
    private Grade[] gradeIncrease;
    @ReadOnlyProperty
    @DocumentReference(lookup="{'employeeId':?#{#self._id}}")
    private List<Promotion> promotions;
    @Field(name = "city")
    private String location;
}
