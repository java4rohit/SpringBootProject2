package com.aihello.amazon.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.aihello.amazon.entities.AmazonKeywordSuggestion;
import com.aihello.amazon.entities.AmazonSearchKeyword;
import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.ScapperKeywordResponse;
import com.aihello.amazon.model.Suggestions;
import com.aihello.amazon.respository.KeywordSuggestionsRespository;
import com.aihello.amazon.respository.SearchKeywordRespository;
import com.aihello.amazon.service.AmazonScrapperService;
import com.aihello.google.entities.KeywordPlannerRequest;

@Service
public class AmazonScrapperServiceImpl implements AmazonScrapperService {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonScrapperServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	KeywordSuggestionsRespository keywordSuggestionsRespository;

	@Autowired
	private SearchKeywordRespository searchKeywordRespository;

	@Value("${amazon.scrapper.keyword.url}")
	private String keywordURL;

	@Value("${amazon.scrapper.metadata.url}")
	private String metadataUrl;

	@Value("${amazon.scrapper.keywordParam.mid}")
	private String mid;

	@Value("${amazon.scrapper.keywordParam.alias}")
	private String alias;

	@Value("${amazon.scrapper.keywordParam.fresh}")
	private String fresh;

	@Value("${amazon.scrapper.keywordParam.event}")
	private String event;

	@Value("${amazon.scrapper.keywordParam.limit}")
	private String limit;

	private Map<String, String> countryUrlMap;

	@PostConstruct
	private void initializeCountryUrlMap() {
		countryUrlMap = countryUrlMap();
	}

	@Override
	public KeywordResponse requestKeyWords(KeywordPlannerRequest keywordPlannerRequest) {

		String url = countryUrlMap.get(keywordPlannerRequest.getCountryCode());
		if (null == url) {
			throw new IllegalArgumentException(
					"Country name is invalid, use one of the following country: " + countryUrlMap.keySet());
		}
		/*
		 * Check if keyword present in database
		 */
		Optional<AmazonSearchKeyword> searchKeywordOptional = searchKeywordRespository.findByKeyword(keywordPlannerRequest.getSearchKeyword());

		if (!searchKeywordOptional.isPresent()) {

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Accept", org.springframework.http.MediaType.APPLICATION_JSON_VALUE);

			HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
					.queryParam("mid", mid)
					.queryParam("alias", alias)
					.queryParam("fresh", fresh)
					.queryParam("prefix", keywordPlannerRequest.getSearchKeyword())
					.queryParam("event", event)
					.queryParam("limit", limit);

			ResponseEntity<ScapperKeywordResponse> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
					HttpMethod.GET, requestEntity, ScapperKeywordResponse.class);

			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				LOG.info("response received: {}", responseEntity.getBody());
				
			}

			else {
				LOG.error("error occurred");
				LOG.info("StatusCode: {}", responseEntity.getStatusCode());
			}

			/*
			 * Prepare client Response
			 */
			KeywordResponse keywordResponse = new KeywordResponse();

			keywordResponse.setKeyword(responseEntity.getBody().getPrefix());

			List<Suggestions> keywordSuggestionList = new ArrayList<>();

			keywordSuggestionList.addAll(responseEntity.getBody().getSuggestions());
			keywordResponse.setSuggestionsList(keywordSuggestionList);

		//	AmazonSearchKeyword amazonSearchKeyword = new AmazonSearchKeyword();

			for (Suggestions suggestions : keywordSuggestionList) {
				try {
					keywordMetadata(suggestions);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// Persist data in DB
			persistKeywardSuggestion(keywordResponse);

			 
			return keywordResponse;

		}
		/*
		 * This block prepares client response
		 */
		  else {
	            AmazonSearchKeyword searchKeywordFromDB = searchKeywordOptional.get();

	            // suggestionList for client
	            ArrayList<Suggestions> suggestions = new ArrayList<>();

	            List<AmazonKeywordSuggestion> keywordSuggestionListFromDB = searchKeywordFromDB.getAmazonKeywordSuggestion();

	            for (AmazonKeywordSuggestion s : keywordSuggestionListFromDB) {
	                Suggestions suggestion = new Suggestions();

	                suggestion.setId(s.getId());
	                suggestion.setStrategyId(s.getStrategyId());
	                suggestion.setSpellCorrected(s.getSpellCorrected());
	                suggestion.setSuggType(s.getSuggestionType());
	                suggestion.setValue(s.getSuggested_keyword());
	                suggestion.setTotalResultCount(s.getTotalResultCount());

	                suggestions.add(suggestion);

	            }

	            KeywordResponse keywordResponse = new KeywordResponse();
	            keywordResponse.setId(searchKeywordFromDB.getId());
	            keywordResponse.setKeyword(searchKeywordFromDB.getKeyword());
	            keywordResponse.setSuggestionsList(suggestions);

	            return keywordResponse;

	        }

	    }

	/*
	 * Save keyword and suggesionList to DB
	 */
	private void persistKeywardSuggestion(KeywordResponse keywordResponse) {

		AmazonSearchKeyword amazonSearchKeyword = new AmazonSearchKeyword();
		amazonSearchKeyword.setKeywordFrom("amazon");
		amazonSearchKeyword.setKeyword(keywordResponse.getKeyword());
		amazonSearchKeyword.setDate(new Date());

		List<Suggestions> suggestionList = keywordResponse.getSuggestionsList();
		// Copy above list into below
		List<AmazonKeywordSuggestion> keywordSuggestionList = new ArrayList<>();

		suggestionList.stream().forEach(s -> {
			AmazonKeywordSuggestion ks = new AmazonKeywordSuggestion();

			ks.setSpellCorrected(s.getSpellCorrected());
			ks.setStrategyId(s.getStrategyId());
			ks.setSuggested_keyword(s.getValue());
			ks.setSuggestionType(s.getSuggType());
			ks.setTotalResultCount(s.getTotalResultCount());

			keywordSuggestionList.add(ks);
		});

		amazonSearchKeyword.setAmazonKeywordSuggestion(keywordSuggestionList);
		searchKeywordRespository.save(amazonSearchKeyword);
		
		List<Suggestions> suggestionListResponse = new ArrayList<>();
		
		amazonSearchKeyword.getAmazonKeywordSuggestion().forEach(s -> {
			Suggestions sug = new Suggestions();
			sug.setId(s.getId());
			sug.setSpellCorrected(s.getSpellCorrected());
			sug.setValue(s.getSuggested_keyword());
			sug.setTotalResultCount(s.getTotalResultCount());
			sug.setStrategyId(s.getStrategyId());
			sug.setSuggType(s.getSuggestionType());
			suggestionListResponse.add(sug);
			
		});
		
		keywordResponse.setSuggestionsList(suggestionListResponse);
	}

	@Override
	public void keywordMetadata(Suggestions suggestions) throws IOException {

		// Optional<KeywordSuggestion> keywordSuggestionsRespositoryOptional =
		// keywordSuggestionsRespository.findById(id);

		String output = "";

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://www.amazon.com/s")
				.queryParam("i", "aps").queryParam("k", suggestions.getValue()).queryParam("ref", "nb_sb_noss")
				.queryParam("url", "search-alias=aps");

		URL myURL = new URL(uriBuilder.toUriString());
		HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();

		myURLConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_" + ThreadLocalRandom.current().nextInt(9, 15 + 1)
						+ "_1) AppleWebKit/531.36 (KHTML, like Gecko) Chrome/"
						+ ThreadLocalRandom.current().nextInt(70, 79 + 1) + ".0.3945.130 Safari/531.36");
		myURLConnection.setRequestProperty("Origin", "https://www.amazon.com");
		myURLConnection.setRequestProperty("Referer", "https://www.amazon.com/");
		myURLConnection.setRequestProperty("Accept-Encoding", "gzip");
		myURLConnection.setRequestProperty("Accept-Language", "en-US");
		myURLConnection.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		/*
		 * try (BufferedReader bufferedReader = new BufferedReader(new
		 * InputStreamReader(myURLConnection.getInputStream()))) { String line; while
		 * ((line = bufferedReader.readLine()) != null) { output += line; } }
		 */
		InputStream is = null;
		try {
			is = new GZIPInputStream(myURLConnection.getInputStream());
			byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
			int n;

			while ((n = is.read(byteChunk)) > 0) {
				baos.write(byteChunk, 0, n);
			}
		} catch (IOException e) {
			System.err.printf("Failed while reading bytes from %s: %s", myURL, e.getMessage());
			e.printStackTrace();
			// Perform any other exception handling that's appropriate.
		} finally {
			if (is != null) {
				is.close();
			}
		}
		byte[] allbytes = baos.toByteArray();

		output = new String(allbytes, StandardCharsets.UTF_8);

		Pattern p = Pattern.compile("(\\w*\"totalResultCount\":\\w*.[0-9])");
		Matcher matcher = p.matcher(output);

		matcher.find();

		suggestions.setTotalResultCount(matcher.group(1) != null ? matcher.group(1).split(":")[1] : null);
	}

	private Map<String, String> countryUrlMap() {
		Map<String, String> map = new HashMap<>();
		map.put("IN", "https://completion.amazon.com/api/2017/suggestions");
		map.put("UK", "https://completion.amazon.co.uk/api/2017/suggestions");
		map.put("BR ", "https://completion.amazon.com.br/api/2017/suggestions");
		map.put("CA", "https://completion.amazon.ca/api/2017/suggestions");
		map.put("AE", "https://completion.amazon.ae/api/2017/suggestions");
		map.put("NL", "https://completion.amazon.nl/api/2017/suggestions");
		map.put("ES", "https://completion.amazon.es/api/2017/suggestions");
		map.put("JP", "https://completion.amazon.jp/api/2017/suggestions");
		map.put("FR", "https://completion.amazon.fr/api/2017/suggestions");
		map.put("IT", "https://completion.amazon.it/api/2017/suggestions");
		map.put("AU", "https://completion.amazon.com.au/api/2017/suggestions");
		map.put("SG", "https://completion.amazon.com.sg/api/2017/suggestions");
		return map;
	}


}
