package com.p.api.node.service.sections;

import com.p.api.node.service.nodes.Node;
import com.p.api.node.service.nodes.NodeTypes;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class Converter {

  public static final Function<String, Boolean> isValidString =
      str -> str != null && !str.trim().isEmpty();

  public static final BiFunction<String, String, String> updateValidString =
      (existing, newValue) -> isValidString.apply(newValue) ? newValue : existing;

  /**
   * ======================================== Question
   * ======================================================
   */
  public Node convertSectionToNode(Section section) {
    return Node.builder()
        .name(section.getName())
        .uniqueId(section.getUniqueId())
        .type(NodeTypes.SECTION)
        .metadata(
            Map.of(
                Constants.PARENT_ID,
                Optional.ofNullable(section.getParentId()).orElse(""),
                Constants.SOFT_DELETE,
                Optional.ofNullable(section.getSoftDelete()).orElse("no")))
        .build();
  }

  public Section convertNodeToSection(Node node) {

    String softDelete = "no";
    String parentId = "";
    Map<String, String> metadata = node.getMetadata();
    if (metadata != null) {
      softDelete = metadata.getOrDefault(Constants.SOFT_DELETE, "no");
      parentId = metadata.getOrDefault(Constants.PARENT_ID, "");
    }

    return Section.builder()
        .uniqueId(node.getUniqueId())
        .name(node.getName())
        .softDelete(softDelete)
        .parentId(parentId)
        .build();
  }

  public Section updateSection(Section existing, Section newValue) {
    //    existing.setName(
    //        isValidString.apply(newValue.getName()) ? newValue.getName() : existing.getName());
    //    existing.setParentId(
    //        isValidString.apply(newValue.getParentId())
    //            ? newValue.getParentId()
    //            : existing.getParentId());
    //    existing.setSoftDelete(
    //        isValidString.apply(newValue.getSoftDelete())
    //            ? newValue.getSoftDelete()
    //            : existing.getSoftDelete());

    existing.setName(updateValidString.apply(existing.getName(), newValue.getName()));
    existing.setParentId(updateValidString.apply(existing.getParentId(), newValue.getParentId()));
    existing.setSoftDelete(
        updateValidString.apply(existing.getSoftDelete(), newValue.getSoftDelete()));
    return existing;
  }

  public SectionDetails convertToSectionDetails(Section section, List<Question> questions) {
    return SectionDetails.sectionDetailsBuilder()
        .uniqueId(section.getUniqueId())
        .parentId(section.getParentId())
        .softDelete(section.getSoftDelete())
        .name(section.getName())
        .questions(questions)
        .build();
  }

  /**
   * ======================================== Question
   * ======================================================
   */
  public Node convertQuestionToNode(Question question) {
    return Node.builder()
        .name(question.getName())
        .uniqueId(question.getUniqueId())
        .type(NodeTypes.QUESTION)
        .metadata(
            Map.of(
                Constants.LINKED_SECTION_ID,
                Optional.ofNullable(question.getSectionId())
                    .orElseThrow(() -> new ValidationException("section id is not present")),
                Constants.SOFT_DELETE,
                Optional.ofNullable(question.getSoftDelete()).orElse("no")))
        .build();
  }

  public Question convertNodeToQuestion(Node node) {

    String softDelete = "no";
    String sectionId = "";
    Map<String, String> metadata = node.getMetadata();
    if (metadata != null) {
      softDelete = metadata.getOrDefault(Constants.SOFT_DELETE, "no");
      sectionId = metadata.getOrDefault(Constants.LINKED_SECTION_ID, "");
    }

    return Question.builder()
        .uniqueId(node.getUniqueId())
        .name(node.getName())
        .softDelete(softDelete)
        .sectionId(sectionId)
        .build();
  }

  public Question updateQuestion(Question existing, Question newValue) {

    existing.setName(updateValidString.apply(existing.getName(), newValue.getName()));

    existing.setSectionId(
        updateValidString.apply(existing.getSectionId(), newValue.getSectionId()));

    existing.setSoftDelete(
        updateValidString.apply(existing.getSoftDelete(), newValue.getSoftDelete()));

    return existing;
  }

  public QuestionDetails convertToQDetails(Question question, List<Answer> answers) {
    return QuestionDetails.questionDetailsBuilder()
        .name(question.getName())
        .sectionId(question.getSectionId())
        .softDelete(question.getSoftDelete())
        .uniqueId(question.getUniqueId())
        .answers(answers)
        .build();
  }

  /**
   * ======================================== Answer
   * ======================================================
   */
  public Node convertAnswerToNode(Answer answer) {
    return Node.builder()
        .name(answer.getName())
        .uniqueId(answer.getUniqueId())
        .type(NodeTypes.ANSWER)
        .metadata(
            Map.of(
                Constants.LINKED_QUESTION_ID,
                Optional.ofNullable(answer.getQuestionId())
                    .orElseThrow(() -> new ValidationException("question id is not present")),
                Constants.SOFT_DELETE,
                Optional.ofNullable(answer.getSoftDelete()).orElse("no")))
        .build();
  }

  public Answer convertNodeToAnswer(Node node) {

    String softDelete = "no";
    String sectionId = "";
    Map<String, String> metadata = node.getMetadata();
    if (metadata != null) {
      softDelete = metadata.getOrDefault(Constants.SOFT_DELETE, "no");
      sectionId = metadata.getOrDefault(Constants.LINKED_QUESTION_ID, "");
    }

    return Answer.builder()
        .uniqueId(node.getUniqueId())
        .name(node.getName())
        .softDelete(softDelete)
        .questionId(sectionId)
        .build();
  }

  public Answer updateAnswer(Answer existing, Answer newValue) {
    existing.setName(updateValidString.apply(existing.getName(), newValue.getName()));

    existing.setText(updateValidString.apply(existing.getText(), newValue.getText()));

    existing.setQuestionId(
        updateValidString.apply(existing.getQuestionId(), newValue.getQuestionId()));

    existing.setSoftDelete(
        updateValidString.apply(existing.getSoftDelete(), newValue.getSoftDelete()));
    return existing;
  }
}
