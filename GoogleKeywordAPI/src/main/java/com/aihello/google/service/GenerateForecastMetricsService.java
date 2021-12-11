package com.aihello.google.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aihello.google.entities.ForecastMetricsResponse;
import com.aihello.google.entities.KeywordPlanResource;
import com.aihello.google.util.AddKeywordPlan;
import com.aihello.google.util.GenerateForecastMetrics;
import com.google.ads.googleads.v8.services.ForecastMetrics;
import com.google.ads.googleads.v8.services.GenerateForecastMetricsResponse;
import com.google.ads.googleads.v8.services.KeywordPlanKeywordForecast;

@Service
public class GenerateForecastMetricsService {


	private static final Logger LOG = LoggerFactory.getLogger(GenerateForecastMetricsService.class);

	
	public  ForecastMetricsResponse generatekeywordPlanResource(String idea, Properties properties, String customerId) {

		
		KeywordPlanResource keywordPlanResource = AddKeywordPlan.generateKeywordPlanResourcce(customerId, idea,properties);
		
		String plan1 = keywordPlanResource.getKeywordPlanResource();
		String[] arr1 = plan1.split("/");
		String PlanID = arr1[arr1.length - 1];

		GenerateForecastMetricsResponse response = GenerateForecastMetrics.generateForecastMetrics(PlanID,properties,customerId);
	 
		ForecastMetricsResponse forecastMetricsResponse = null;

		if (response != null) {
			
			for (KeywordPlanKeywordForecast forecast : response.getKeywordForecastsList()) {

				forecastMetricsResponse = new ForecastMetricsResponse();
				ForecastMetrics metrics = forecast.getKeywordForecast();
				forecastMetricsResponse.setAverageCostPerClick(metrics.getAverageCpc());
				forecastMetricsResponse.setImpressions(metrics.getImpressions());
				forecastMetricsResponse.setClickthroughRate(metrics.getCtr());
				forecastMetricsResponse.setClicks(metrics.getClicks());
				forecastMetricsResponse.setCost(metrics.getCostMicros());
				LOG.info("forecastMetricsResponse ",forecastMetricsResponse);
			
			}
			LOG.debug("forecastMetricsResponse list: {}",forecastMetricsResponse);
		}
	
		return forecastMetricsResponse;

	}
}
