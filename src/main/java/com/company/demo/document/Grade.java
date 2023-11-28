package com.company.demo.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Data
@Document
public class Grade {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private String description;
    @Field
    private String requirement;
}
