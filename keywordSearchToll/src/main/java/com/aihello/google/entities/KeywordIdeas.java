package com.aihello.google.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.ads.googleads.v8.enums.KeywordPlanCompetitionLevelEnum.KeywordPlanCompetitionLevel;

@Entity
public class KeywordIdeas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String keywordIdea;
	long monthlySearches;
	
	KeywordPlanCompetitionLevel competition;
	@JsonIgnore
	@JsonProperty(value = "keywordFrom")
 	private String keywordFrom;
	
	double impressions;
	double clickthroughRate;
	long averageCostPerClick;
	double clicks;
	double cost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeywordIdeas() {
		return keywordIdea;
	}

	public void setKeywordIdeas(String keywordIdeas) {
		this.keywordIdea = keywordIdeas;
	}

	public long getMonthlySearches() {
		return monthlySearches;
	}

	public void setMonthlySearches(long monthlySearches) {
		this.monthlySearches = monthlySearches;
	}

	public KeywordPlanCompetitionLevel getCompetition() {
		return competition;
	}

	public void setCompetition(KeywordPlanCompetitionLevel competition) {
		this.competition = competition;
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

	public double getImpressions() {
		return impressions;
	}

	public void setImpressions(double impressions) {
		this.impressions = impressions;
	}

	public double getClickthroughRate() {
		return clickthroughRate;
	}

	public void setClickthroughRate(double clickthroughRate) {
		this.clickthroughRate = clickthroughRate;
	}

	public long getAverageCostPerClick() {
		return averageCostPerClick;
	}

	public void setAverageCostPerClick(long averageCostPerClick) {
		this.averageCostPerClick = averageCostPerClick;
	}

	public double getClicks() {
		return clicks;
	}

	public void setClicks(double clicks) {
		this.clicks = clicks;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "KeywordIdeas [id=" + id + ", keywordIdeas=" + keywordIdea + ", monthlySearches=" + monthlySearches
				+ ", competition=" + competition + ", keywordFrom=" + keywordFrom + ", impressions=" + impressions
				+ ", clickthroughRate=" + clickthroughRate + ", averageCostPerClick=" + averageCostPerClick
				+ ", clicks=" + clicks + ", cost=" + cost + "]";
	}

}
