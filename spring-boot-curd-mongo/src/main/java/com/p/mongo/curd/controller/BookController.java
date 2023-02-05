package com.p.mongo.curd.controller;


import com.p.mongo.curd.domain.Book;
import com.p.mongo.curd.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class
 */
/**
 * Annotation
 */
@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepo repo;

    @PostMapping("/add")
    public String saveBook(@RequestBody Book book) {
        repo.save(book);
        return "Added Successfully";
    }

    @GetMapping("/findAll")
    public List<Book> getBooks() {
        return repo.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }

}
