package com.aihello.amazon.respository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aihello.amazon.entities.AmazonSearchKeyword;
@Repository
public interface KeywordSuggestionsRespository extends CrudRepository<AmazonSearchKeyword,Integer> {

 

	}
