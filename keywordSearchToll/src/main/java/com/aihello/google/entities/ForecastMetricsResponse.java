package com.aihello.google.entities;

public class ForecastMetricsResponse {


	long id;
	double impressions;
	double clickthroughRate;
	long averageCostPerClick;
	double clicks;
	double cost;

	public double getImpressions() {
		return impressions;
	}

	public void setImpressions(double d) {
		this.impressions = d;
	}

	public double getClickthroughRate() {
		return clickthroughRate;
	}

	public void setClickthroughRate(double d) {
		this.clickthroughRate = d;
	}

	public long getAverageCostPerClick() {
		return averageCostPerClick;
	}

	public void setAverageCostPerClick(long l) {
		this.averageCostPerClick = l;
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

	public void setCost(double l) {
		this.cost = l;
	}

	@Override
	public String toString() {
		return "ForecastMetricsResponse [impressions=" + impressions + ", clickthroughRate=" + clickthroughRate
				+ ", averageCostPerClick=" + averageCostPerClick + ", clicks=" + clicks + ", cost=" + cost + "]";
	}


}
