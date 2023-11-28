package com.company.demo.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Data
public class Address {
    @Field
    private String street;
    @Field
    private Integer no;
    @Field
    private String building;
    @Field
    private Integer apartment;
}
