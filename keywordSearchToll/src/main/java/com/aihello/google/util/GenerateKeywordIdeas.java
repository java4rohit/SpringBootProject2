package com.aihello.google.util;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Nullable;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v8.enums.KeywordPlanNetworkEnum.KeywordPlanNetwork;
import com.google.ads.googleads.v8.errors.GoogleAdsError;
import com.google.ads.googleads.v8.errors.GoogleAdsException;
import com.google.ads.googleads.v8.services.GenerateKeywordIdeasRequest;
import com.google.ads.googleads.v8.services.KeywordPlanIdeaServiceClient;
import com.google.ads.googleads.v8.services.KeywordPlanIdeaServiceClient.GenerateKeywordIdeasPagedResponse;
import com.google.ads.googleads.v8.utils.ResourceNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GenerateKeywordIdeas {

	
	private static class GenerateKeywordIdeasParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId;

		@Parameter(names = "21167", required = true, description = "Location criteria IDs. For example, specify 21167 for New York. For more information"
				+ " on determining this value, see: "
				+ " https://developers.google.com/google-ads/api/reference/data/geotargets.")
		private List<Long> locationIds;

		@Parameter(names = "1000", required = true, description = "A language criterion ID. For example, specify 1000 for English. For more information"
				+ " on determining this value, see: "
				+ " https://developers.google.com/adwords/api/docs/appendix/codes-formats#languages")
		private Long languageId;

		@Parameter(names = ArgumentNames.KEYWORD_TEXTS)
		private List<String> keywords = new ArrayList<>();

		@Parameter(names = ArgumentNames.PAGE_URL, description = "URL of a page related to your business")
		private String pageUrl;
	}

	// public static void main(String[] args) throws IOException {
	public  static GenerateKeywordIdeasPagedResponse KeywordIdeas(String searchKeyword,String customerId, Properties properties, String countryCodeId) throws IOException {
		GenerateKeywordIdeasPagedResponse response = null;
		
		GenerateKeywordIdeasParams params = new GenerateKeywordIdeasParams();

		params.customerId = Long.parseLong(customerId);
		params.locationIds = Arrays.asList(Long.parseLong(countryCodeId));
		// Long.parseLong("1"));
		params.languageId = Long.parseLong("1000");
		params.keywords = Arrays.asList(searchKeyword);
		// Optional: Use a URL related to your business to generate ideas.
		// params.pageUrl = null;

		GoogleAdsClient googleAdsClient = null;
		

		googleAdsClient = GoogleAdsClient.newBuilder().fromProperties(properties).build();

		try {
			response = 	new GenerateKeywordIdeas().runExample(googleAdsClient, params.customerId, params.languageId,
					params.locationIds, params.keywords, params.pageUrl);

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

	// [START generate_keyword_ideas]
	private GenerateKeywordIdeasPagedResponse runExample(GoogleAdsClient googleAdsClient, long customerId, long languageId, List<Long> locationIds,
			List<String> keywords, @Nullable String pageUrl) {
		
		GenerateKeywordIdeasPagedResponse response;
		
		try (KeywordPlanIdeaServiceClient keywordPlanServiceClient = googleAdsClient.getLatestVersion()
				.createKeywordPlanIdeaServiceClient()) {
			GenerateKeywordIdeasRequest.Builder requestBuilder = GenerateKeywordIdeasRequest.newBuilder()
					.setCustomerId(Long.toString(customerId))
					.setLanguage(ResourceNames.languageConstant(languageId))
					.setKeywordPlanNetwork(KeywordPlanNetwork.GOOGLE_SEARCH_AND_PARTNERS);

			for (Long locationId : locationIds) {
				requestBuilder.addGeoTargetConstants(ResourceNames.geoTargetConstant(locationId));
			}

			if (keywords.isEmpty() && pageUrl == null) {
				throw new IllegalArgumentException(
						"At least one of keywords or page URL is required, but neither was specified.");
			}

			if (keywords.isEmpty()) {
				// Only page URL was specified, so use a UrlSeed.
				requestBuilder.getUrlSeedBuilder().setUrl(pageUrl);
			} else if (pageUrl == null) {
				// Only keywords were specified, so use a KeywordSeed.
				requestBuilder.getKeywordSeedBuilder().addAllKeywords(keywords);
			} else {
				// Both page URL and keywords were specified, so use a KeywordAndUrlSeed.
				requestBuilder.getKeywordAndUrlSeedBuilder().setUrl(pageUrl).addAllKeywords(keywords);
			}

			// Sends the keyword ideas request.
			 response = keywordPlanServiceClient.generateKeywordIdeas(requestBuilder.build());
			 
			// Prints each result in the response.
		/*	for (GenerateKeywordIdeaResult result : response.iterateAll()) {
				System.out.printf("Keyword idea text '%s' has %d average monthly searches and '%s' competition.%n",
						result.getText(), result.getKeywordIdeaMetrics().getAvgMonthlySearches(),
						result.getKeywordIdeaMetrics().getCompetition());*/
			 
			
		}
		return response;
	}
}