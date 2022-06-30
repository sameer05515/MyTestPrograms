package com.p.test.curd.repository;

import com.p.test.curd.model.Books;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepository extends CrudRepository<Books, Integer> {
}
