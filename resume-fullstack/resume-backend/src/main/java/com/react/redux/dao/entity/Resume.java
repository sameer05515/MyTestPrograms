package com.react.redux.dao.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "resume")
@Getter
@Setter
@AllArgsConstructor
//@EqualsAndHashCode
//@ToString
@NoArgsConstructor(force = true)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NonNull
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "person_resume",
//            joinColumns = {@JoinColumn(name = "resume_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")})
    //(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
    //            CascadeType.DETACH, CascadeType.REFRESH})
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id", referencedColumnName = "id")
    @JsonBackReference
    private Person person;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    @JsonManagedReference
    private Set<Company> companies;
}
