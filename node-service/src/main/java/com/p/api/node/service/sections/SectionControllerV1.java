package com.p.api.node.service.sections;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sections/v1")
@RequiredArgsConstructor
public class SectionControllerV1 {
  private final SectionService sectionService;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create-section")
  public ResponseEntity<Section> createSection(@RequestBody Section section) {
    return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.saveSection(section));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/update-section")
  public ResponseEntity<Section> updateSection(@RequestBody Section section) {
    return ResponseEntity.status(HttpStatus.OK).body(sectionService.updateSection(section));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-all-sections")
  public ResponseEntity<List<? extends Section>> getAllSections() {
    return ResponseEntity.status(HttpStatus.OK).body(sectionService.getAllSections());
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create-question")
  public ResponseEntity<Question> createQuestion(@RequestBody Question node) {
    return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.saveQuestion(node));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/update-question")
  public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
    return ResponseEntity.status(HttpStatus.OK).body(sectionService.updateQuestion(question));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/get-question-details/{id}")
  public ResponseEntity<? extends Question> getQuestionDetailsById(@PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK).body(sectionService.getQuestionDetailsById(id));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/create-answer")
  public ResponseEntity<Answer> createAnswer(@RequestBody Answer node) {
    return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.saveAnswer(node));
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/update-answer")
  public ResponseEntity<Answer> updateAnswer(@RequestBody Answer answer) {
    return ResponseEntity.status(HttpStatus.OK).body(sectionService.updateAnswer(answer));
  }
}
