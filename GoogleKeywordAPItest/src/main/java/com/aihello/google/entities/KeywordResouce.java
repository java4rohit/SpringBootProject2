package com.aihello.google.entities;

public class KeywordResouce {

	String keywordPlanResource;
	String planCampaignResource;
	String planAdGroupResource;

	public String getKeywordPlanResource() {
		return keywordPlanResource;
	}

	public void setKeywordPlanResource(String keywordPlanResource) {
		this.keywordPlanResource = keywordPlanResource;
	}

	public String getPlanCampaignResource() {
		return planCampaignResource;
	}

	public void setPlanCampaignResource(String planCampaignResource) {
		this.planCampaignResource = planCampaignResource;
	}

	public String getPlanAdGroupResource() {
		return planAdGroupResource;
	}

	public void setPlanAdGroupResource(String planAdGroupResource) {
		this.planAdGroupResource = planAdGroupResource;
	}

	@Override
	public String toString() {
		return "KeywordPlan [keywordPlanResource=" + keywordPlanResource + ", planCampaignResource="
				+ planCampaignResource + ", planAdGroupResource=" + planAdGroupResource + "]";
	}

}

	


