package com.company.demo.repository;

import com.company.demo.document.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends MongoRepository<Promotion, String> {
}
