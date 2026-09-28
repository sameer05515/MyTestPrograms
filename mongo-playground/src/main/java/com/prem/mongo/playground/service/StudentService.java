package com.prem.mongo.playground.service;

import com.prem.mongo.playground.entity.Student;
import com.prem.mongo.playground.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired StudentRepository studentRepository;

  public Student createStudent(Student student) {
    return studentRepository.save(student);
  }

  public Student getStudentbyId(String id) {
    return studentRepository.findById(id).get();
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student updateStudent(Student student) {
    return studentRepository.save(student);
  }

  public String deleteStudent(String id) {
    studentRepository.deleteById(id);
    return "Student has been deleted.";
  }

  public List<Student> getStudentsByName(String name) {
    return studentRepository.findByName(name);
  }

  public Student studentsByNameAndMail(String name, String email) {
    return studentRepository.findByEmailAndName(email, name);
  }

  public Student studentsByNameOrMail(String name, String email) {
    return studentRepository.findByNameOrEmail(name, email);
  }

  public List<Student> getAllWithPagination(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

    return studentRepository.findAll(pageable).getContent();
  }

  public List<Student> allWithSorting() {
    Sort sort = Sort.by(Sort.Direction.ASC, "name", "email");

    return studentRepository.findAll(sort);
  }

  public List<Student> allWithSortingNameAscEmailDesc() {
    Sort sort =
        Sort.by(
            //            Sort.Order.asc("name"),
            Sort.Order.desc("mail"), Sort.Order.asc("name"));

    return studentRepository.findAll(sort);
  }

  public Page<Student> getStudentsSortedPaginated(int page, int size) {

    Sort sort =
        Sort.by(
            //            Sort.Order.asc("name"),
            Sort.Order.desc("mail"), Sort.Order.asc("name"));
    Pageable pageable = PageRequest.of(page, size, sort);
    return studentRepository.findAll(pageable);
  }

  public List<Student> byDepartmentName(String deptName) {
    return studentRepository.findByDepartmentDepartmentName(deptName);
  }

  public List<Student> bySubjectName(String subName) {
    return studentRepository.findBySubjectsSubjectName(subName);
  }

  public List<Student> emailLike(String email) {
    return studentRepository.findByEmailIsLike(email);
  }

  public List<Student> nameStartsWith(String name) {
    return studentRepository.findByNameStartsWith(name);
  }
}
