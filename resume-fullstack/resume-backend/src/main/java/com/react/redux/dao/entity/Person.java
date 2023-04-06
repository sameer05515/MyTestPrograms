package com.react.redux.dao.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
//@EqualsAndHashCode
//@ToString
public class Person {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinTable(name = "person_resume",
//            joinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "resume_id", referencedColumnName = "id")})
//    @JoinColumn(name="person_id")
    @JsonManagedReference
    private Set<Resume> resumes;

}
