package com.react.redux.rest;

import com.react.redux.dao.entity.Company;
import com.react.redux.dao.entity.Person;
import com.react.redux.dao.entity.Resume;
import com.react.redux.dao.repository.CompanyRepository;
import com.react.redux.dao.repository.PersonRepository;
import com.react.redux.dao.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class CompanyController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(path = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getAll(){
        return companyRepository.findAll();
    }

    @PostMapping(path="/{resumeId}/company" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Company save(@PathVariable("resumeId") long resumeId, @RequestBody Company company){

        Company companyToSave= new Company();

        companyToSave.setName(company.getName());
        Resume resume= resumeRepository.findById(resumeId).get();
        companyToSave.setResume(resume);

        return companyRepository.save(companyToSave);
    }

    @PutMapping(path="/{resumeId}/company" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Company update(@PathVariable("resumeId") long resumeId,@RequestBody Company company){
        Company companyToUpdate= new Company();
        companyToUpdate.setId(company.getId());
        companyToUpdate.setName(company.getName());
        Resume resume= resumeRepository.findById(resumeId).get();
        companyToUpdate.setResume(resume);

        return companyRepository.save(companyToUpdate);
    }

    @GetMapping(path = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getById(@PathVariable("id") long id) throws Exception {
        System.out.println("Company to find Person for ID:- "+id);
        Optional<Company> optionalCompany= companyRepository.findById(id);

        if(optionalCompany.isPresent()){
            return optionalCompany.get();
        }else {
            throw new Exception("Company with id "+ id+" not found");
        }
    }
}
