package com.react.redux.dao.repository;

import com.react.redux.dao.entity.Company;
import com.react.redux.dao.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
