package com.aihello.google.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aihello.google.entities.ForecastMetricsResponse;
import com.aihello.google.entities.GoogleSearchKeyword;
import com.aihello.google.entities.KeywordIdeas;
import com.aihello.google.entities.KeywordPlannerRequest;
import com.aihello.google.repository.KeywordIdeasRepository;
import com.aihello.google.repository.SearchKeywordRepository;
import com.aihello.google.util.GenerateKeywordIdeas;
import com.google.ads.googleads.v8.services.GenerateKeywordIdeaResult;
import com.google.ads.googleads.v8.services.KeywordPlanIdeaServiceClient.GenerateKeywordIdeasPagedResponse;

@Service
public class GenerateKeywordIdeasService {

	private static final Logger LOG = LoggerFactory.getLogger(GenerateKeywordIdeasService.class);

	@Autowired
	GenerateForecastMetricsService generateForecastMetricsService;

	@Autowired
	public KeywordIdeasRepository keywordIdeasRepository;
	@Autowired
	public SearchKeywordRepository searchKeywordRepository;

	@Value("${api.googleads.clientId}")
	private String clientIdValue;

	@Value("${api.googleads.clientSecret}")
	private String clientSecretValue;

	@Value("${api.googleads.developerToken}")
	private String developerTokenValue;

	@Value("${api.googleads.refreshToken}")
	private String refreshTokenValue;
	

	@Value("${api.googleads.loginCustomerId}")
	private String loginCustomerId;
	

	private Map<String, String> countryCode;

	@PostConstruct
	private void initializeCountryCodeIdMap() {
		countryCode = countryCode();
	}

	private Properties properties;

	// creating ads properties
	@PostConstruct
	public void initProperty() {
		properties = new Properties();
		properties.put("api.googleads.clientId", clientIdValue);
		properties.put("api.googleads.clientSecret", clientSecretValue);
		properties.put("api.googleads.developerToken", developerTokenValue);
		properties.put("api.googleads.refreshToken", refreshTokenValue);
	}

	/*
	 * FIXME the logic for saving and getting from DB is wrong. What we are trying
	 * to do is to avoid calling google api all the time and get from DB. So first
	 * lookup from DB for keyword ideas and forecast metrics. If they are in DB then
	 * load from DB Else call google remote api and get the data and then store it
	 * in DB
	 */

	public List<KeywordIdeas> searchKeywordsIdeas(KeywordPlannerRequest keywordPlannerRequest) throws IOException {

		Optional<GoogleSearchKeyword> searchKeywordOptional = searchKeywordRepository
				.findByKeyword(keywordPlannerRequest.getSearchKeyword());

		List<KeywordIdeas> keywordIdeasAndForcastList = new ArrayList<>();

		List<KeywordIdeas> keywordIdeasList = new ArrayList<>();
		KeywordIdeas ideas = null;
		String customerId = loginCustomerId;

		LOG.debug("Is the keyword already present in database: {}", searchKeywordOptional.isPresent());

		String countryCodeId = countryCode.get(keywordPlannerRequest.getCountryCode());
		if (null == countryCodeId) {
			throw new IllegalArgumentException(
					"Country name is invalid, use one of the following country: " + countryCode.keySet());
		}

		if (!searchKeywordOptional.isPresent()) {

			GenerateKeywordIdeasPagedResponse response = GenerateKeywordIdeas
					.KeywordIdeas(keywordPlannerRequest.getSearchKeyword(), customerId, properties, countryCodeId);

			LOG.info("Response {}", response);
			for (GenerateKeywordIdeaResult result : response.iterateAll()) {

				ideas = new KeywordIdeas();
				ideas.setKeywordIdeas(result.getText());
				// ideas.setMonthlySearches(result.getKeywordIdeaMetrics().getAvgMonthlySearches());
				ideas.setMonthlySearches(result.getKeywordIdeaMetrics().getAvgMonthlySearches());
				ideas.setCompetition(result.getKeywordIdeaMetrics().getCompetition());
				ideas.setKeywordFrom("Google");
				keywordIdeasList.add(ideas);
			}

			LOG.info("============================Creating the keyword Idea{}:", keywordIdeasList.size());
			GoogleSearchKeyword keyword = new GoogleSearchKeyword();
			keyword.setKeyword(keywordPlannerRequest.getSearchKeyword());
			keyword.setDate(new Date());
			keyword.setKeywordIdeas(keywordIdeasList);
			keyword.setKeywordFrom("Google");

			for (KeywordIdeas idea : keywordIdeasList) {

				ForecastMetricsResponse responseplanner = generateForecastMetricsService
						.generatekeywordPlanResource(idea.getKeywordIdeas(), properties,customerId);

				idea.setImpressions(responseplanner.getImpressions());
				idea.setClickthroughRate(responseplanner.getClickthroughRate());
				idea.setAverageCostPerClick(responseplanner.getAverageCostPerClick());
				idea.setClicks(responseplanner.getClicks());
				idea.setCost(responseplanner.getCost());

				keywordIdeasAndForcastList.add(idea);
				LOG.debug(responseplanner.toString());
			}
			keyword.setKeywordIdeas(keywordIdeasAndForcastList);
			searchKeywordRepository.save(keyword);
			return keywordIdeasList;
		} 
		else {

			GoogleSearchKeyword googleSearchKeywordFromDB = searchKeywordOptional.get();
			List<KeywordIdeas> KeywordIdeasList = googleSearchKeywordFromDB.getKeywordIdeas();

			return KeywordIdeasList;

		}

	}

	private Map<String, String> countryCode() {
		Map<String, String> map = new HashMap<>();
		map.put("IN", "2356");
		map.put("AI", "2660");
		map.put("SX", "2534");
		map.put("CW", "2531");
		map.put("AT", "2040");
		map.put("BE", "2056");
		map.put("BR", "2052");
		map.put("US", "21167");
		map.put("CA", "20121");
		map.put("BR", "21232");
		map.put("CH", "20133");
		map.put("CN", "20172");
		map.put("CO", "20198");
		map.put("DE", "20227");
		map.put("DK", "20245");
		map.put("ES", "20289");
		map.put("FI", "2246");
		map.put("FR", "20316");
		map.put("FO", "2234");
		map.put("FM", "2583");
		map.put("GB", "20339");
		map.put("IE", "20496");
		map.put("IL", "20519");
		map.put("IT", "2380");
		map.put("JP", "20630");
		map.put("MX", "20715");
		map.put("NL", "20765");
		map.put("NO", "20784");
		map.put("NZ", "20792");
		map.put("PH", "20835");
		map.put("PL", "20847");
		map.put("PT", "20867");
		return map;
	}

}
