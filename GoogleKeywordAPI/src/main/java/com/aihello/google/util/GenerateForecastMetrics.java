// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.aihello.google.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v8.errors.GoogleAdsError;
import com.google.ads.googleads.v8.errors.GoogleAdsException;
import com.google.ads.googleads.v8.services.ForecastMetrics;
import com.google.ads.googleads.v8.services.GenerateForecastMetricsResponse;
import com.google.ads.googleads.v8.services.KeywordPlanKeywordForecast;
import com.google.ads.googleads.v8.services.KeywordPlanServiceClient;
import com.google.ads.googleads.v8.utils.ResourceNames;

/**
 * Creates a keyword plan, which can be reused for retrieving forecast metrics
 * and historic metrics.
 */
public class GenerateForecastMetrics {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateForecastMetrics.class);

	private static class GenerateForecastMetricParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, description = "The customer in which to create a new keyword plan.")
		public Long customerId;

		@Parameter(names = ArgumentNames.KEYWORD_PLAN_ID, description = "The keyword plan ID for which to generate metrics.")
		public Long keywordPlanId;
	}

	// public static void main(String[] args) {
	public static  GenerateForecastMetricsResponse generateForecastMetrics(String keywordPlanId, Properties properties, String customerId) {
		GenerateForecastMetricParams params = new GenerateForecastMetricParams();
		// if (!params.parseArguments(args)) {

		// Optional, specify the customer ID for which the call is made.
		params.customerId = Long.valueOf(customerId);

		params.keywordPlanId = Long.valueOf(keywordPlanId);

		GenerateForecastMetricsResponse response=null;
		
		GoogleAdsClient googleAdsClient = null;
		googleAdsClient = GoogleAdsClient.newBuilder().fromProperties(properties).build();

		try {
			response =new GenerateForecastMetrics().runExample(googleAdsClient, params.customerId, params.keywordPlanId);
		
		} catch (GoogleAdsException gae) {
			// GoogleAdsException is the base class for most exceptions thrown by an API
			// request.
			// Instances of this exception have a message and a GoogleAdsFailure that
			// contains a
			// collection of GoogleAdsErrors that indicate the underlying causes of the
			// GoogleAdsException.
			System.err.printf("Request ID %s failed due to GoogleAdsException. Underlying errors:%n",
					gae.getRequestId());
			int i = 0;
			for (GoogleAdsError googleAdsError : gae.getGoogleAdsFailure().getErrorsList()) {
				LOG.error("  Error %d: %s%n", i++, googleAdsError);
			}
			System.exit(1);
		}
		return response;
	}

	/**
	 * Runs the code example.
	 *
	 * @param googleAdsClient the Google Ads API client.
	 * @param customerId      the client customer ID.
	 * @param planId          the plan ID.
	 */
	// [START generate_forecast_metrics]
	public GenerateForecastMetricsResponse runExample(GoogleAdsClient googleAdsClient, Long customerId, Long planId) {
		String planResourceName = ResourceNames.keywordPlan(customerId, planId);
		
		GenerateForecastMetricsResponse response;
		
		try (KeywordPlanServiceClient client = googleAdsClient.getLatestVersion().createKeywordPlanServiceClient()) {
			response = client.generateForecastMetrics(planResourceName);
			int i = 0;
			for (KeywordPlanKeywordForecast forecast : response.getKeywordForecastsList()) {
				ForecastMetrics metrics = forecast.getKeywordForecast();
				LOG.info("%d Keyword ID: %s%n", ++i, forecast.getKeywordPlanAdGroupKeyword());
				LOG.info("Estimated daily clicks: %f%n", metrics.getClicks());
				LOG.info("Estimated daily impressions: %f%n", metrics.getImpressions());
				LOG.info("Estimated average cpc (micros): %d%n%n", metrics.getAverageCpc());
			}
			return response;
		}

	}
	// [END generate_forecast_metrics]
}