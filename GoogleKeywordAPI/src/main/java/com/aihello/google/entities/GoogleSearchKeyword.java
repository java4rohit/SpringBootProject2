package com.aihello.google.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class GoogleSearchKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String keyword;
	private Date date;
	@JsonIgnore
	@JsonProperty(value = "keywordFrom")
	private String keywordFrom;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "keyword_Id", referencedColumnName = "id")
	private List<KeywordIdeas> keywordIdeas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<KeywordIdeas> getKeywordIdeas() {
		return keywordIdeas;
	}

	public void setKeywordIdeas(List<KeywordIdeas> keywordIdeas) {
		this.keywordIdeas = keywordIdeas;
	}

	public Date getDate() {
		return date;
	}

	public GoogleSearchKeyword setDate(Date date) {
		this.date = date;
		return this;
	}

	@JsonIgnore
	@JsonProperty(value = "keywordFrom")
	public String getKeywordFrom() {
		return keywordFrom;
	}

	@JsonIgnore
	@JsonProperty(value = "keywordFrom")
	public void setKeywordFrom(String keywordFrom) {
		this.keywordFrom = keywordFrom;
	}

	@Override
	public String toString() {
		return "SearchKeyword [id=" + id + ", keyword=" + keyword + ", keywordIdeas=" + keywordIdeas + "]";
	}

}
