package com.aihello.amazon.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aihello.amazon.entities.AmazonSearchKeyword;


@Repository
public interface SearchKeywordRespository  extends CrudRepository<AmazonSearchKeyword, Integer> {
	
	public Optional<AmazonSearchKeyword> findByKeyword(String keyword);
}
