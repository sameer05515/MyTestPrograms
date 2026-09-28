package com.example.entity;

import com.example.request.CreateStudentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToMany(mappedBy = "student")
  private List<Subject> learningSubjects;

  public Student(CreateStudentRequest createStudentRequest) {
    this.firstName = createStudentRequest.getFirstName();
    this.lastName = createStudentRequest.getLastName();
    this.email = createStudentRequest.getEmail();
  }
}
