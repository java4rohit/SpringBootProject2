package com.aihello.google.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aihello.google.entities.KeywordIdeas;

@Repository
public interface KeywordIdeasRepository extends CrudRepository<KeywordIdeas, Long>{
	

	public Optional<KeywordIdeas> findById(String keyword);
	
	@Transactional
	@Modifying
	@Query("delete from KeywordIdeas idea where idea.id in (:ids)")
	public void deleteIdeas(@Param("ids") List<Long> ids);	

}
