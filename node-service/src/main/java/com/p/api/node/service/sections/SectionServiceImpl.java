package com.p.api.node.service.sections;

import com.p.api.node.service.base.exception.SectionModuleException;
import com.p.api.node.service.nodes.Node;
import com.p.api.node.service.nodes.NodeService;
import com.p.api.node.service.nodes.NodeTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

  private final Converter converter;
  //  private final NodeRepository nodeRepository;
  private final NodeService nodeService;

  private void validateObject(Object obj, String errorMessage) {
    if (obj == null) {
      throw new SectionModuleException(errorMessage);
    }
  }

  private void validateString(String str, String errorMessage) {
    if (str == null || str.trim().isEmpty()) {
      throw new SectionModuleException(errorMessage);
    }
  }

  private void validateNodeExists(String uniqueId, String errorMessage) {
    if (uniqueId != null && !uniqueId.trim().isEmpty()) {
      nodeService.getNodeById(uniqueId);
    }
  }

  private void validateUniqueId(String uniqueId) {
    nodeService.getNodeById(uniqueId);
  }

  /**
   * 1. section object should be non-null
   *
   * <p>2. if section has parent id, then it must exist in database
   *
   * <p>3. name should be non-null and non-empty
   */
  @Override
  public Section saveSection(Section section) {
    validateObject(section, "Section object is null");
    section.setUniqueId(UUID.randomUUID().toString());
    validateString(section.getName(), "Name should have a valid text");
    validateNodeExists(
        section.getParentId(),
        "No section found for given section parent id: " + section.getParentId());

    Node node = converter.convertSectionToNode(section);
    node = nodeService.saveNode(node);
    return converter.convertNodeToSection(node);
  }

  /**
   * 1. section object should be non-null
   *
   * <p>2. section id should non-null, non-empty and existing in database
   *
   * <p>3. if section has parent id, then it must exist in database
   */
  @Override
  public Section updateSection(Section newSection) {
    validateObject(newSection, "Section object is null");
    validateString(newSection.getUniqueId(), "Section ID is missing");
    validateNodeExists(
        newSection.getParentId(),
        "No section found for given section parent id: " + newSection.getParentId());

    Section existing =
        converter.convertNodeToSection(nodeService.getNodeById(newSection.getUniqueId()));
    Node node = converter.convertSectionToNode(converter.updateSection(existing, newSection));

    return converter.convertNodeToSection(nodeService.updateNode(node));
  }

  /**
   * 1. question object should be non-null
   *
   * <p>2. question should have an existing section id in database
   *
   * <p>3. name should be non-null and non-empty
   */
  @Override
  public Question saveQuestion(Question question) {
    validateObject(question, "Question object is null");
    validateString(question.getSectionId(), "Question should have a valid section ID");
    validateString(question.getName(), "Name should have a valid text");
    //    validateParentExists(question.getSectionId());
    validateNodeExists(
        question.getSectionId(), "No section found for given id: " + question.getSectionId());

    question.setUniqueId(UUID.randomUUID().toString());
    Node node = converter.convertQuestionToNode(question);
    //    validateUniqueId(node.getUniqueId());
    node = nodeService.saveNode(node);
    return converter.convertNodeToQuestion(node);
  }

  /**
   * 1. question object should be non-null
   *
   * <p>2. section id should non-null, non-empty and existing in database
   *
   * <p>3. if section has parent id, then it must exist in database
   */
  @Override
  public Question updateQuestion(Question newQuestion) {
    validateObject(newQuestion, "Question object is null");
    validateString(newQuestion.getUniqueId(), "Question ID is missing");
    validateString(newQuestion.getSectionId(), "Question should have a valid section ID");
    validateNodeExists(
        newQuestion.getSectionId(), "No section found for given id: " + newQuestion.getSectionId());

    Question existing =
        converter.convertNodeToQuestion(nodeService.getNodeById(newQuestion.getUniqueId()));
    Node node = converter.convertQuestionToNode(converter.updateQuestion(existing, newQuestion));
    return converter.convertNodeToQuestion(nodeService.updateNode(node));
  }

  /**
   * 1. answer object should be non-null
   *
   * <p>2. answer should have an existing question id in database
   *
   * <p>3. text should be non-null and non-empty
   */
  @Override
  public Answer saveAnswer(Answer answer) {
    validateObject(answer, "Answer object is null");
    validateString(answer.getQuestionId(), "Answer should have a valid question ID");
    validateString(answer.getText(), "Answer should have a valid text");
    validateNodeExists(
        answer.getQuestionId(), "No question found for given id: " + answer.getQuestionId());

    answer.setUniqueId(UUID.randomUUID().toString());
    Node node = converter.convertAnswerToNode(answer);
    node = nodeService.saveNode(node);
    return converter.convertNodeToAnswer(node);
  }

  @Override
  public Answer updateAnswer(Answer answer) {
    validateObject(answer, "Answer object is null");
    validateString(answer.getUniqueId(), "Answer ID is missing");
    validateString(answer.getQuestionId(), "Answer should have a valid question ID");

    Answer existing = converter.convertNodeToAnswer(nodeService.getNodeById(answer.getUniqueId()));
    Node node = converter.convertAnswerToNode(converter.updateAnswer(existing, answer));
    return converter.convertNodeToAnswer(nodeService.updateNode(node));
  }

  @Override
  public List<? extends Section> getAllSections() {
    List<Section> sectionNodes =
        nodeService.getAllNodesByType(NodeTypes.SECTION).stream()
            .map(converter::convertNodeToSection)
            .toList();
    Map<String, List<Question>> questionNodes =
        nodeService.getAllNodesByType(NodeTypes.QUESTION).stream()
            .map(converter::convertNodeToQuestion)
            .collect(Collectors.groupingBy(Question::getSectionId));

    return sectionNodes.stream()
        .map(
            section ->
                converter.convertToSectionDetails(
                    section, questionNodes.getOrDefault(section.getUniqueId(), new ArrayList<>())))
        .toList();
  }

  @Override
  public Question getQuestionDetailsById(String id) {
    Question question = converter.convertNodeToQuestion(nodeService.getNodeById(id));
    List<Answer> answers =
        nodeService.getAllNodesByMetadataKeyValue(Constants.LINKED_QUESTION_ID, id).stream()
            .map(converter::convertNodeToAnswer)
            .toList();
    return converter.convertToQDetails(question, answers);
  }
}
