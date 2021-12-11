package com.aihello.google.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aihello.google.entities.GoogleSearchKeyword;

public interface SearchKeywordRepository extends CrudRepository<GoogleSearchKeyword, Long> {
	
	public Optional<GoogleSearchKeyword> findByKeyword(String keyword);
}
