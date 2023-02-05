package com.p.mongo.curd.controller;


/**
 * Class
 */

import com.p.mongo.curd.domain.Word;
import com.p.mongo.curd.repository.WordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Annotation
 */
@RestController
@RequestMapping("words")
public class WordController {
    @Autowired
    private WordRepo repo;

    @PostMapping("/add")
    public String saveWord(@RequestBody Word word) {
        repo.save(word);
        return "Added Successfully";
    }

    @GetMapping("/findAll")
    @CrossOrigin
    public List<Word> getWords() {
        return repo.findAll();
    }

    @GetMapping("/find/{id}")
    public Word getWord(@PathVariable String id) {
        Optional<Word> wordOptional = repo.findById(id);
        if (wordOptional.isPresent())
            return wordOptional.get();
        else
            return null;
    }

    @GetMapping("/count")
    public long getCount(){
        return repo.count();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteWord(@PathVariable String id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }
}
