package com.aihello.amazon.entities;

import java.io.Serializable;

import com.google.ads.googleads.v8.enums.KeywordPlanCompetitionLevelEnum.KeywordPlanCompetitionLevel;


public class GoogleAndAmazoneResponse implements Serializable{

	
	String keywordResponse;
	long monthlySearches;
	KeywordPlanCompetitionLevel competition;
	double impressions;
	double clickthroughRate;
	long averageCostPerClick;
	double clicks;
	double cost;
	String suggType;
	String strategyId;
	String totalResultCount;
	String spellCorrected;
	public String getKeywordResponse() {
		return keywordResponse;
	}
	public void setKeywordResponse(String keywordResponse) {
		this.keywordResponse = keywordResponse;
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
	public String getSuggType() {
		return suggType;
	}
	public void setSuggType(String suggType) {
		this.suggType = suggType;
	}
	public String getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}
	public String getTotalResultCount() {
		return totalResultCount;
	}
	public void setTotalResultCount(String totalResultCount) {
		this.totalResultCount = totalResultCount;
	}
	public String getSpellCorrected() {
		return spellCorrected;
	}
	public void setSpellCorrected(String spellCorrected) {
		this.spellCorrected = spellCorrected;
	}
	
	
}
