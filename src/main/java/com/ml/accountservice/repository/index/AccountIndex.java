package com.ml.accountservice.repository.index;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
public class AccountIndex {

    private final MongoTemplate mongoTemplate;

    public AccountIndex(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void createIndexesIfNotExists() {
        // Create unique index on email field
        createUniqueIndex("email");

        // Create unique index on phoneNumber field
        createUniqueIndex("phoneNumber");
    }

    private void createUniqueIndex(String fieldName) {
        Index indexDefinition = new Index()
                .on(fieldName, Sort.Direction.ASC)
                .unique()
                .background()
                .sparse();

        mongoTemplate.indexOps("account").ensureIndex(indexDefinition);
    }
}
