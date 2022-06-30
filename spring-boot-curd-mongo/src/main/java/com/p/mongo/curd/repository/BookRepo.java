package com.p.mongo.curd.repository;// Java Program to Illustrate BookRepo File

import com.p.mongo.curd.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepo
	extends MongoRepository<Book, Integer> {
}
