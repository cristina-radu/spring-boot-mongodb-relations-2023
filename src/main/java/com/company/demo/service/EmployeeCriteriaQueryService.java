package com.company.demo.service;

import com.company.demo.document.Employee;
import com.company.demo.document.Promotion;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeCriteriaQueryService {
    private final MongoTemplate mongoTemplate;

    public void deleteBy(String city, String department){
        Query query = new Query();
        Criteria criteria = Criteria
                .where("city").is(city)
                .and("department.name").is(department);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, Employee.class);
    }

    public void gradeIncrease(Integer maxSalary, String currency, String grade, String department,
                              Integer newSalaryIncrease, String newCurrency, String newGrade){
        Query query = new Query();
        Criteria criteria = Criteria.where("salary").gte(maxSalary)
                .and("currency").is(currency)
                .and("grade.name").is(grade)
                .and("department.name").is(department);
        query.addCriteria(criteria);

        Promotion promotion = Promotion.builder()
                .salary(newSalaryIncrease)
                .currency(newCurrency)
                .gradeIncrease(newGrade)
                .date(new Date().toString()).build();
        Update update = new Update();
        update.inc("salary", newSalaryIncrease);
        update.set("currency", newCurrency);
        update.set("grade.name", newGrade);
        update.addToSet("gradeIncrease", newGrade);
        update.push("promotions", promotion);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Employee.class);
        System.out.println("Modified Count :"+ updateResult.getModifiedCount());
        System.out.println("Matched Count :"+ updateResult.getMatchedCount());
        System.out.println("WasAcknowledged :"+ updateResult.wasAcknowledged());
        System.out.println("UpsertedId :"+ updateResult.getUpsertedId());
    }

    public void executeBulk(List<Employee> newEmployees, Integer maxSalary, String currency, String grade, String department,
                            Integer newSalaryIncrease, String newCurrency, String newGrade){
        Query query = new Query();
        Criteria criteria = Criteria.where("salary").gte(maxSalary)
                .and("currency").is(currency)
                .and("grade.name").is(grade)
                .and("department.name").is(department);
        query.addCriteria(criteria);

        Promotion promotion = Promotion.builder()
                .salary(newSalaryIncrease)
                .currency(newCurrency)
                .gradeIncrease(newGrade)
                .date(new Date().toString()).build();
        Update update = new Update();
        update.inc("salary", newSalaryIncrease);
        update.set("currency", newCurrency);
        update.set("grade.name", newGrade);
        update.addToSet("gradeIncrease", newGrade);
        update.push("promotions", promotion);

        Query queryForDelete = new Query();
        Criteria criteriaForDelete = Criteria
                .where("city").is("Bucharest")
                .and("department.name").is(department);
        queryForDelete.addCriteria(criteria);
        mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Employee.class)
                .insert(newEmployees)
                .updateMulti(query, update)
                .remove(queryForDelete);
    }

    public void getSumByDepartment(List<String> departments){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("department.name").in(departments)),
                Aggregation.group("department.name").sum("salary").as("salariesPerDomain")
        );

        AggregationResults<String> response = mongoTemplate.aggregate(aggregation, Employee.class, String.class);

    }

    public List<String> getLookUpAggregation(){
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("department")
                .localField("department")
                .foreignField("_id")
                .as("departmentJoinOutput");
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        AggregationResults<String> result = mongoTemplate.aggregate(aggregation, Employee.class, String.class);
        return result.getMappedResults();
    }

    public List<String> getLookUpAggregationGradeIncrease(){
/*        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("grade")
                .localField("gradeIncrease")
                .foreignField("_id")
                .as("gradeIncreaseJoinOutput");*/
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("grade")
                .localField("gradeIncrease")
                .foreignField("_id")
                .as("testJoinOutput");
        UnwindOperation unwindOperation = Aggregation.unwind("$gradeIncreaseJoinOutput");

        Aggregation aggregation = Aggregation.newAggregation(lookupOperation);
        AggregationResults<String> result = mongoTemplate.aggregate(aggregation, Employee.class, String.class);
        return result.getMappedResults();
    }

}
