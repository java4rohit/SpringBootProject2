package com.aihello.amazon.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AmazonKeywordSuggestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String suggested_keyword;
	private String spellCorrected;
	private String strategyId;
	private String suggestionType;
	private String totalResultCount;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSuggested_keyword() {
		return suggested_keyword;
	}


	public void setSuggested_keyword(String suggested_keyword) {
		this.suggested_keyword = suggested_keyword;
	}


	public String getSpellCorrected() {
		return spellCorrected;
	}


	public void setSpellCorrected(String spellCorrected) {
		this.spellCorrected = spellCorrected;
	}


	public String getStrategyId() {
		return strategyId;
	}


	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}


	public String getSuggestionType() {
		return suggestionType;
	}


	public void setSuggestionType(String suggestionType) {
		this.suggestionType = suggestionType;
	}


	public String getTotalResultCount() {
		return totalResultCount;
	}


	public void setTotalResultCount(String totalResultCount) {
		this.totalResultCount = totalResultCount;
	}


	@Override
	public String toString() {
		return "KeywordSuggestion [id=" + id + ", suggested_keyword=" + suggested_keyword + ", spellcorrected="
				+ spellCorrected + ", strategyid=" + strategyId + ", suggestionType=" + suggestionType
				+ ", totalResultCount=" + totalResultCount + "]";
	}

}
