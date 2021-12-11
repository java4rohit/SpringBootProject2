package com.aihello.amazon.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Suggestions implements Serializable {

	private static final long serialVersionUID = 1L;
	Long id;
	String suggType;
	String strategyId;
	String value;
	String totalResultCount;
	String spellCorrected;

	@JsonIgnore
	String type;
	@JsonIgnore
	String refTag;
	@JsonIgnore
	String candidateSources;
	@JsonIgnore
	String prior;
	@JsonIgnore
	String ghost;
	@JsonIgnore
	String help;
	@JsonIgnore
	String fallback;
	@JsonIgnore
	String blackListed;

	@JsonIgnore
	String xcatOnly;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	@JsonIgnore
	public String getType() {
		return type;
	}

	@JsonIgnore
	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public String getRefTag() {
		return refTag;
	}

	@JsonIgnore
	public void setRefTag(String refTag) {
		this.refTag = refTag;
	}

	@JsonIgnore
	public String getCandidateSources() {
		return candidateSources;
	}

	@JsonIgnore
	public void setCandidateSources(String candidateSources) {
		this.candidateSources = candidateSources;
	}

	@JsonIgnore
	public String getPrior() {
		return prior;
	}

	@JsonIgnore
	public void setPrior(String prior) {
		this.prior = prior;
	}

	@JsonIgnore
	public String getGhost() {
		return ghost;
	}

	@JsonIgnore
	public void setGhost(String ghost) {
		this.ghost = ghost;
	}

	@JsonIgnore
	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	@JsonIgnore
	public String getFallback() {
		return fallback;
	}

	@JsonIgnore
	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	@JsonIgnore
	public String getBlackListed() {
		return blackListed;
	}

	@JsonIgnore
	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}

	@JsonIgnore
	public String getXcatOnly() {
		return xcatOnly;
	}

	public void setXcatOnly(String xcatOnly) {
		this.xcatOnly = xcatOnly;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Suggestions [suggType=" + suggType + ", type=" + type + ", value=" + value + ", refTag=" + refTag
				+ ", candidateSources=" + candidateSources + ", strategyId=" + strategyId + ", prior=" + prior
				+ ", ghost=" + ghost + ", help=" + help + ", fallback=" + fallback + ", blackListed=" + blackListed
				+ ", spellCorrected=" + spellCorrected + ", xcatOnly=" + xcatOnly + ", totalCount=" + totalResultCount + "]";
	}


}
