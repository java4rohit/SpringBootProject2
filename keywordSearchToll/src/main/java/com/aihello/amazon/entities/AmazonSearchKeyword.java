package com.aihello.amazon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class AmazonSearchKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String keyword;
	private Date date;
	private String keywordFrom;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "keyword_Id", referencedColumnName = "id")
	private List<AmazonKeywordSuggestion> amazonKeywordSuggestion;

	
	
	public AmazonSearchKeyword() {
		super();
	}

	public AmazonSearchKeyword(int id, String keyword, Date date, String keywordFrom,
			List<AmazonKeywordSuggestion> amazonKeywordSuggestion) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.date = date;
		this.keywordFrom = keywordFrom;
		this.amazonKeywordSuggestion = amazonKeywordSuggestion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getKeywordFrom() {
		return keywordFrom;
	}

	public void setKeywordFrom(String keywordFrom) {
		this.keywordFrom = keywordFrom;
	}

	public List<AmazonKeywordSuggestion> getAmazonKeywordSuggestion() {
		return amazonKeywordSuggestion;
	}

	public void setAmazonKeywordSuggestion(List<AmazonKeywordSuggestion> amazonKeywordSuggestion) {
		this.amazonKeywordSuggestion = amazonKeywordSuggestion;
	}

	@Override
	public String toString() {
		return "AmazonSearchKeyword [id=" + id + ", keyword=" + keyword + ", date=" + date + ", keywordFrom="
				+ keywordFrom + ", amazonKeywordSuggestion=" + amazonKeywordSuggestion + "]";
	}
	

	
}
