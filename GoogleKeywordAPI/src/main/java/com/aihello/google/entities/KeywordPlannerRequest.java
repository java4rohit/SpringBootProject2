package com.aihello.google.entities;

public class KeywordPlannerRequest {

	String searchKeyword;
	String countryCode;

	
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "KeywordPlannerRequest [searchKeyword=" + searchKeyword + ", countryCode=" + countryCode + "]";
	}

	

	

}
