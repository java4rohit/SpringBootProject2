package com.aihello.google.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aihello.google.entities.KeywordResouce;
import com.beust.jcommander.Parameter;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v8.enums.KeywordMatchTypeEnum.KeywordMatchType;
import com.google.ads.googleads.v8.enums.KeywordPlanForecastIntervalEnum.KeywordPlanForecastInterval;
import com.google.ads.googleads.v8.enums.KeywordPlanNetworkEnum.KeywordPlanNetwork;
import com.google.ads.googleads.v8.errors.GoogleAdsError;
import com.google.ads.googleads.v8.errors.GoogleAdsException;
import com.google.ads.googleads.v8.resources.KeywordPlan;
import com.google.ads.googleads.v8.resources.KeywordPlanAdGroup;
import com.google.ads.googleads.v8.resources.KeywordPlanAdGroupKeyword;
import com.google.ads.googleads.v8.resources.KeywordPlanCampaign;
import com.google.ads.googleads.v8.resources.KeywordPlanCampaignKeyword;
import com.google.ads.googleads.v8.resources.KeywordPlanForecastPeriod;
import com.google.ads.googleads.v8.resources.KeywordPlanGeoTarget;
import com.google.ads.googleads.v8.services.GenerateForecastMetricsResponse;
import com.google.ads.googleads.v8.services.KeywordPlanAdGroupKeywordOperation;
import com.google.ads.googleads.v8.services.KeywordPlanAdGroupKeywordServiceClient;
import com.google.ads.googleads.v8.services.KeywordPlanAdGroupOperation;
import com.google.ads.googleads.v8.services.KeywordPlanAdGroupServiceClient;
import com.google.ads.googleads.v8.services.KeywordPlanCampaignKeywordOperation;
import com.google.ads.googleads.v8.services.KeywordPlanCampaignKeywordServiceClient;
import com.google.ads.googleads.v8.services.KeywordPlanCampaignOperation;
import com.google.ads.googleads.v8.services.KeywordPlanCampaignServiceClient;
import com.google.ads.googleads.v8.services.KeywordPlanOperation;
import com.google.ads.googleads.v8.services.KeywordPlanServiceClient;
import com.google.ads.googleads.v8.services.MutateKeywordPlanAdGroupKeywordResult;
import com.google.ads.googleads.v8.services.MutateKeywordPlanAdGroupKeywordsResponse;
import com.google.ads.googleads.v8.services.MutateKeywordPlanAdGroupsResponse;
import com.google.ads.googleads.v8.services.MutateKeywordPlanCampaignKeywordsResponse;
import com.google.ads.googleads.v8.services.MutateKeywordPlanCampaignsResponse;
import com.google.ads.googleads.v8.services.MutateKeywordPlansResponse;
import com.google.ads.googleads.v8.utils.ResourceNames;

/**
 * Creates a keyword plan, which can be reused for retrieving forecast metrics
 * and historic metrics.
 */
public class AddKeywordPlan1 {

	private static class AddKeywordPlanParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, description = "The customer in which to create a new keyword plan.")
		public Long customerId;
	}

//	public static void main(String[] args) {
	public static GenerateForecastMetricsResponse GenerateForecastMetricsResponse() {
		AddKeywordPlanParams params = new AddKeywordPlanParams();
		// if (!params.parseArguments(args)) {

		// Optional, specify the customer ID under which to create a new keyword plan.
		params.customerId = Long.valueOf("6765951321");
		// }
		GenerateForecastMetricsResponse response = null;
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

		try {
			response = new AddKeywordPlan1().runExample(googleAdsClient, params.customerId);
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
	 */
	// [START add_keyword_plan]
	private GenerateForecastMetricsResponse runExample(GoogleAdsClient googleAdsClient, Long customerId) {
		String keywordPlanResource = createKeywordPlan(googleAdsClient, customerId);
		String planCampaignResource = createKeywordPlanCampaign(googleAdsClient, customerId, keywordPlanResource);
		String planAdGroupResource = createKeywordPlanAdGroup(googleAdsClient, customerId, planCampaignResource);
		createKeywordPlanAdGroupKeywords(googleAdsClient, customerId, planAdGroupResource);
		createKeywordPlanCampaignKeywords(googleAdsClient, customerId, planCampaignResource);
		//////////////////////////////////////
		//////////////////////////////////////
		KeywordResouce keywordResouce = new KeywordResouce();
		keywordResouce.setKeywordPlanResource(keywordPlanResource);
		keywordResouce.setPlanAdGroupResource(planAdGroupResource);
		keywordResouce.setPlanCampaignResource(planCampaignResource);

		GenerateForecastMetricsResponse response = GenerateForecastMetrics(keywordResouce);
		return response;

	}

	/**
	 * Creates a keyword plan.
	 *
	 * @param googleAdsClient the Google Ads API client.
	 * @param customerId      the client customer ID.
	 */
	private static String createKeywordPlan(GoogleAdsClient googleAdsClient, Long customerId) {
		KeywordPlan plan = KeywordPlan.newBuilder()
				.setName("Keyword plan for traffic estimate #" + CodeSampleHelper.getPrintableDateTime())
				.setForecastPeriod(KeywordPlanForecastPeriod.newBuilder()
						.setDateInterval(KeywordPlanForecastInterval.NEXT_QUARTER).build())
				.build();

		KeywordPlanOperation op = KeywordPlanOperation.newBuilder().setCreate(plan).build();

		try (KeywordPlanServiceClient client = googleAdsClient.getLatestVersion().createKeywordPlanServiceClient()) {
			// Adds the keyword plan.
			MutateKeywordPlansResponse response = client.mutateKeywordPlans(String.valueOf(customerId),
					Arrays.asList(op));
			// Displays the results.
			String resourceName = response.getResults(0).getResourceName();
			System.out.printf("Created keyword plan: %s%n", resourceName);
			return resourceName;
		}
	}

	/**
	 * Creates a campaign for the keyword plan.
	 *
	 * @param googleAdsClient     the Google Ads API client.
	 * @param customerId          the client customer ID.
	 * @param keywordPlanResource the keyword plan resource name.
	 */
	private static String createKeywordPlanCampaign(GoogleAdsClient googleAdsClient, Long customerId,
			String keywordPlanResource) {
		// Creates a keyword plan campaign.
		KeywordPlanCampaign.Builder campaign = KeywordPlanCampaign.newBuilder()
				.setName("Keyword plan campaign #" + CodeSampleHelper.getPrintableDateTime())
				.setCpcBidMicros(1_000_000L).setKeywordPlanNetwork(KeywordPlanNetwork.GOOGLE_SEARCH)
				.setKeywordPlan(keywordPlanResource);

		// See https://developers.google.com/google-ads/api/reference/data/geotargets
		// for the list of geo target IDs.
		campaign.addGeoTargets(KeywordPlanGeoTarget.newBuilder()
				// Geo-target constant 2840 is for USA.
				.setGeoTargetConstant(ResourceNames.geoTargetConstant(2840)).build());

		// See
		// https://developers.google.com/google-ads/api/reference/data/codes-formats#languages
		// for the list of language criteria IDs.
		//
		// Language criteria 1000 is for English.
		campaign.addLanguageConstants(ResourceNames.languageConstant(1000));

		KeywordPlanCampaignOperation op = KeywordPlanCampaignOperation.newBuilder().setCreate(campaign).build();

		try (KeywordPlanCampaignServiceClient client = googleAdsClient.getLatestVersion()
				.createKeywordPlanCampaignServiceClient()) {
			// Adds the campaign.
			MutateKeywordPlanCampaignsResponse response = client.mutateKeywordPlanCampaigns(String.valueOf(customerId),
					Arrays.asList(op));
			// Displays the result.
			String resourceName = response.getResults(0).getResourceName();
			System.out.printf("Created campaign for keyword plan: %s%n", resourceName);
			return resourceName;
		}
	}

	/**
	 * Creates the ad group for the keyword plan.
	 *
	 * @param googleAdsClient      the Google Ads API client.
	 * @param customerId           the client customer ID.
	 * @param planCampaignResource plan campaign resource name.
	 */
	private static String createKeywordPlanAdGroup(GoogleAdsClient googleAdsClient, Long customerId,
			String planCampaignResource) {
		// Creates the keyword plan ad group.
		KeywordPlanAdGroup.Builder adGroup = KeywordPlanAdGroup.newBuilder()
				.setKeywordPlanCampaign(planCampaignResource)
				.setName("Keyword plan ad group #" + CodeSampleHelper.getPrintableDateTime())
				.setCpcBidMicros(2_500_000L);

		KeywordPlanAdGroupOperation op = KeywordPlanAdGroupOperation.newBuilder().setCreate(adGroup).build();
		try (KeywordPlanAdGroupServiceClient client = googleAdsClient.getLatestVersion()
				.createKeywordPlanAdGroupServiceClient()) {
			// Adds the ad group.
			MutateKeywordPlanAdGroupsResponse response = client.mutateKeywordPlanAdGroups(String.valueOf(customerId),
					Arrays.asList(op));

			// Displays the result.
			String resourceName = response.getResults(0).getResourceName();
			System.out.println("Created ad group for keyword plan: " + resourceName);
			return resourceName;
		}
	}

	/**
	 * Creates keywords for the keyword plan.
	 *
	 * @param googleAdsClient     the Google Ads API client.
	 * @param customerId          the client customer ID.
	 * @param planAdGroupResource plan ad group resource name.
	 */
	private static void createKeywordPlanAdGroupKeywords(GoogleAdsClient googleAdsClient, Long customerId,
			String planAdGroupResource) {
		// Creates the keywords for keyword plan.
		KeywordPlanAdGroupKeyword keyword1 = KeywordPlanAdGroupKeyword.newBuilder()
				.setKeywordPlanAdGroup(planAdGroupResource).setCpcBidMicros(2_000_000L)
				.setMatchType(KeywordMatchType.BROAD).setText("mars cruise").build();
		KeywordPlanAdGroupKeyword keyword2 = KeywordPlanAdGroupKeyword.newBuilder()
				.setKeywordPlanAdGroup(planAdGroupResource).setCpcBidMicros(1_500_000L)
				.setMatchType(KeywordMatchType.PHRASE).setText("cheap cruise").build();
		KeywordPlanAdGroupKeyword keyword3 = KeywordPlanAdGroupKeyword.newBuilder()
				.setKeywordPlanAdGroup(planAdGroupResource).setCpcBidMicros(1_990_000L)
				.setMatchType(KeywordMatchType.EXACT).setText("jupiter cruise").build();

		// Creates an operation for each plan keyword.
		List<KeywordPlanAdGroupKeywordOperation> operations = Stream.of(keyword1, keyword2, keyword3)
				.map(kw -> KeywordPlanAdGroupKeywordOperation.newBuilder().setCreate(kw).build())
				.collect(Collectors.toList());

		try (KeywordPlanAdGroupKeywordServiceClient client = googleAdsClient.getLatestVersion()
				.createKeywordPlanAdGroupKeywordServiceClient()) {
			// Adds the keywords.
			MutateKeywordPlanAdGroupKeywordsResponse response = client
					.mutateKeywordPlanAdGroupKeywords(String.valueOf(customerId), operations);

			// Displays the results.
			for (MutateKeywordPlanAdGroupKeywordResult result : response.getResultsList()) {
				System.out.printf("Created keyword for keyword plan: %s%n", result.getResourceName());
			}
		}
	}

	/**
	 * Creates negative keywords for the keyword plan.
	 *
	 * @param googleAdsClient      the Google Ads API client.
	 * @param customerId           the client customer ID.
	 * @param planCampaignResource plan campaign resource name.
	 */
	private void createKeywordPlanCampaignKeywords(GoogleAdsClient googleAdsClient, Long customerId,
			String planCampaignResource) {
		KeywordPlanCampaignKeyword negativeKeyword = KeywordPlanCampaignKeyword.newBuilder()
				.setKeywordPlanCampaign(planCampaignResource).setMatchType(KeywordMatchType.BROAD).setNegative(true)
				.setText("moon walk").build();
		KeywordPlanCampaignKeywordOperation op = KeywordPlanCampaignKeywordOperation.newBuilder()
				.setCreate(negativeKeyword).build();

		try (KeywordPlanCampaignKeywordServiceClient client = googleAdsClient.getLatestVersion()
				.createKeywordPlanCampaignKeywordServiceClient()) {
			// Adds the negative keyword.
			MutateKeywordPlanCampaignKeywordsResponse response = client
					.mutateKeywordPlanCampaignKeywords(String.valueOf(customerId), Arrays.asList(op));
			// Displays the result.
			String resourceName = response.getResults(0).getResourceName();
			System.out.printf("Created negative keyword for keyword plan: %s%n", resourceName);
		}
	}

	class GenerateForecastMetricParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, description = "The customer in which to create a new keyword plan.")
		public Long customerId;

		@Parameter(names = ArgumentNames.KEYWORD_PLAN_ID, description = "The keyword plan ID for which to generate metrics.")
		public Long keywordPlanId;

	}

	public GenerateForecastMetricsResponse GenerateForecastMetrics(KeywordResouce keywordResouce) {

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
	private GenerateForecastMetricsResponse runExample(GoogleAdsClient googleAdsClient, Long customerId, Long planId) {
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
