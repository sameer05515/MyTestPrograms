package com.p.mongo.curd.repository;

import com.p.mongo.curd.domain.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepo extends MongoRepository<Word, String> {
}
