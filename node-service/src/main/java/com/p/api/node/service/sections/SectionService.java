package com.p.api.node.service.sections;

import java.util.List;

public interface SectionService {
  public Section saveSection(Section section);

  public Section updateSection(Section section);

  public Question saveQuestion(Question question);

  public Question updateQuestion(Question question);

  public Answer saveAnswer(Answer answer);

  public Answer updateAnswer(Answer answer);

  public List<? extends Section> getAllSections();

  public Question getQuestionDetailsById(String id);
}
