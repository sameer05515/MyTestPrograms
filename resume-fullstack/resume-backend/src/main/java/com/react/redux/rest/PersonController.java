package com.react.redux.rest;

import com.react.redux.dao.entity.Person;
import com.react.redux.dao.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @PostMapping(path="/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Person save(@RequestBody Person person){
        return personRepository.save(person);
    }

    @PutMapping(path="/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getById(@PathVariable("id") long id) throws Exception {
        System.out.println("Going to find Person for ID:- "+id);
        Optional<Person> optionalPerson= personRepository.findById(id);

        if(optionalPerson.isPresent()){
            return optionalPerson.get();
        }else {
            throw new Exception("Person with id "+ id+" not found");
        }
    }
}
