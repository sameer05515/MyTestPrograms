package com.react.redux.rest;

import com.react.redux.dao.entity.Person;
import com.react.redux.dao.entity.Resume;
import com.react.redux.dao.repository.PersonRepository;
import com.react.redux.dao.repository.ResumeRepository;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class ResumeController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    ResumeRepository resumeRepository;

    @GetMapping(path = "/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resume> getAll(){
        return resumeRepository.findAll();
    }

    @PostMapping(path="/resume" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Resume save(@RequestBody Resume resume){

        Long personId= resume.getPerson().getId();
        Person person= personRepository.findById(personId).get();
        resume.setPerson(person);

        return resumeRepository.save(resume);
    }

    @PutMapping(path="/resume" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Resume update(@RequestBody Resume resume){
        Long personId= resume.getPerson().getId();
        Person person= personRepository.findById(personId).get();
        resume.setPerson(person);
        return resumeRepository.save(resume);
    }

    @GetMapping(path = "/resume/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resume getById(@PathVariable("id") long id) throws Exception {
        System.out.println("Going to find Person for ID:- "+id);
        Optional<Resume> optionalResume= resumeRepository.findById(id);

        if(optionalResume.isPresent()){
            return optionalResume.get();
        }else {
            throw new Exception("Resume with id "+ id+" not found");
        }
    }

    @GetMapping(path = "/{persionId}/resume/{resumeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resume getById(@PathVariable("persionId") long persionId, @PathVariable("resumeId") long id) throws Exception {
        System.out.printf("Going to find Resume for persionId '%s' and resumeId '%s' %n", persionId, id);
        Resume optionalResume= resumeRepository.findBy(persionId,id);

        return optionalResume;

//        if(optionalResume.isPresent()){
//            return optionalResume.get();
//        }else {
//            throw new Exception("Resume with id "+ id+" not found");
//        }
    }

    @GetMapping(path = "/{persionId}/resume/{resumeId}/report")
    public ResponseEntity<byte[]> getReport(@PathVariable("persionId") long persionId, @PathVariable("resumeId") long id) throws Exception {
        System.out.printf("Going to find Resume for persionId '%s' and resumeId '%s' %n", persionId, id);
        JasperReportBuilder report = DynamicReports.report();//a new report
        Resume optionalResume= resumeRepository.findBy(persionId,id);

        /**
         //		 * Report from collection
         //		 * */

		List<String > nameList= Arrays.asList("Ram","Shyam");
		report.setDataSource(nameList);

        JasperPrint empReport = report.toJasperPrint();

        HttpHeaders headers = new HttpHeaders();
        //set the PDF format
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "employees-details.pdf");
        //create the report in PDF format
        return new ResponseEntity<byte[]>
                (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);


//        return optionalResume;

//        if(optionalResume.isPresent()){
//            return optionalResume.get();
//        }else {
//            throw new Exception("Resume with id "+ id+" not found");
//        }
    }
}
