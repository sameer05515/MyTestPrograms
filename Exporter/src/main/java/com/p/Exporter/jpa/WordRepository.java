package com.p.Exporter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p.Exporter.pojo.Word;

public interface WordRepository extends JpaRepository<Word, Integer> {
	
}