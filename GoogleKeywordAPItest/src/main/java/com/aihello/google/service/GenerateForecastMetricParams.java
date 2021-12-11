package com.aihello.google.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.aihello.google.entities.KeywordResouce;
import com.beust.jcommander.Parameter;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v8.errors.GoogleAdsError;
import com.google.ads.googleads.v8.errors.GoogleAdsException;
import com.google.ads.googleads.v8.services.GenerateForecastMetricsResponse;
import com.google.ads.googleads.v8.services.KeywordPlanServiceClient;
import com.google.ads.googleads.v8.utils.ResourceNames;

class  GenerateForecastMetricParams {

	@Parameter(names = ArgumentNames.CUSTOMER_ID, description = "The customer in which to create a new keyword plan.")
	public Long customerId;

	@Parameter(names = ArgumentNames.KEYWORD_PLAN_ID, description = "The keyword plan ID for which to generate metrics.")
	public Long keywordPlanId;

	public static GenerateForecastMetricsResponse GenerateForecastMetrics(KeywordResouce keywordResouce) {

		GenerateForecastMetricParams params = new GenerateForecastMetricParams();
		// if (!params.parseArguments(args)) {

		// Optional, specify the customer ID for which the call is made.
		params.customerId = Long.valueOf("6765951321");

		String plan1 = keywordResouce.getKeywordPlanResource();
		String plan2 = keywordResouce.getPlanAdGroupResource();
		String plan3 = keywordResouce.getPlanCampaignResource();
		System.out.println(plan1);
		System.out.println(plan2);
		System.out.println(plan3);

		String[] arr1 = plan1.split("/");
		String n1 = arr1[arr1.length - 1];

		params.keywordPlanId = Long.valueOf(n1);
		// }
		GoogleAdsClient googleAdsClient = null;
		try {
			googleAdsClient = GoogleAdsClient.newBuilder().fromPropertiesFile().build();
		} catch (FileNotFoundException fnfe) {
			System.err.printf("Failed to load GoogleAdsClient configuration from file. Exception: %s%n", fnfe);
			System.exit(1);
		} catch (IOException ioe) {
			System.err.printf("Failed to create GoogleAdsClient. Exception: %s%n", ioe);
			System.exit(1);
		}

		GenerateForecastMetricsResponse response = null;
		try {
			response = runExample(googleAdsClient, params.customerId, params.keywordPlanId);
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
				System.err.printf("  Error %d: %s%n", i++, googleAdsError);
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
	private static GenerateForecastMetricsResponse runExample(GoogleAdsClient googleAdsClient, Long customerId, Long planId) {
		String planResourceName = ResourceNames.keywordPlan(customerId, planId);

		GenerateForecastMetricsResponse response;
		try (KeywordPlanServiceClient client = googleAdsClient.getLatestVersion().createKeywordPlanServiceClient()) {
			response = client.generateForecastMetrics(planResourceName);

		}
		return response;
	}
	// [END generate_forecast_metrics]

	// [END add_keyword_plan]}}
}
