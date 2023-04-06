package com.react.redux.dao.repository;

import com.react.redux.dao.entity.Person;
import com.react.redux.dao.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("select r from Resume r where r.person.id= :personId and r.id= :resumeId")
    public Resume findBy(@Param("personId") long personId,@Param("resumeId") long resumeId);
}
