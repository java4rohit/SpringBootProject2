package com.aihello.aggragatorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aihello.amazon.entities.GoogleAndAmazoneResponse;
import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.Suggestions;
import com.aihello.amazon.service.AmazonScrapperService;
import com.aihello.google.entities.KeywordIdeas;
import com.aihello.google.entities.KeywordPlannerRequest;
import com.aihello.google.service.GenerateKeywordIdeasService;

@Service
public class KeywordSerachPlanService {

	@Autowired
	GenerateKeywordIdeasService generateKeywordIdeasService;

	@Autowired
	AmazonScrapperService amazonScrapperServicel;

	public List<GoogleAndAmazoneResponse> getResponse(KeywordPlannerRequest keywordPlannerRequest) throws IOException {

		KeywordResponse amazonResponse = amazonScrapperServicel.requestKeyWords(keywordPlannerRequest);

		List<KeywordIdeas> keywordIdeasGooglrList = generateKeywordIdeasService
				.searchKeywordsIdeas(keywordPlannerRequest);
		
		System.out.println("keywordIdeasGooglrList===============================================================================");
		 for (int i = 0; i < keywordIdeasGooglrList.size(); i++) {
			 
	            // Print all elements of List
	            System.out.println(keywordIdeasGooglrList.get(i));
	        }
		 
		List<Suggestions> suggestionsAmazonList = amazonResponse.getSuggestionsList();
		
         System.out.println("suggestionsAmazonList===============================================================================");
		 for (int i = 0; i < suggestionsAmazonList.size(); i++) {
			 
	            System.out.println(suggestionsAmazonList.get(i));
	        }
		 
		 
		Map<String, KeywordIdeas> googleKeywordIdeasMap = keywordIdeasGooglrList.stream()
				.collect(Collectors.toMap(KeywordIdeas::getKeywordIdeas, k -> k));

		Map<String, Suggestions> amazonSuggestionsMap = suggestionsAmazonList.stream()
				.collect(Collectors.toMap(Suggestions::getValue, s -> s));

		List<GoogleAndAmazoneResponse> googleAndAmazoneResponsesList = new ArrayList<>();

		googleKeywordIdeasMap.keySet().stream().forEach(k -> {
			if (amazonSuggestionsMap.containsKey(k)) {
				
				System.out.println("======================================amazonSuggestionsMap.containsKey(k)==========================================================================================");

				GoogleAndAmazoneResponse googleAndAmazoneResponse = new GoogleAndAmazoneResponse();

				KeywordIdeas keywordIdeas = googleKeywordIdeasMap.get(k);
				Suggestions suggestions = amazonSuggestionsMap.get(k);

				googleAndAmazoneResponse.setKeywordResponse(k);
				googleAndAmazoneResponse.setAverageCostPerClick(keywordIdeas.getAverageCostPerClick());
				googleAndAmazoneResponse.setCost(keywordIdeas.getCost());
				googleAndAmazoneResponse.setImpressions(keywordIdeas.getImpressions());
				googleAndAmazoneResponse.setMonthlySearches(keywordIdeas.getMonthlySearches());
				googleAndAmazoneResponse.setClicks(keywordIdeas.getClicks());
				googleAndAmazoneResponse.setCompetition(keywordIdeas.getCompetition());
				googleAndAmazoneResponse.setClickthroughRate(keywordIdeas.getClickthroughRate());
				googleAndAmazoneResponse.setTotalResultCount(suggestions.getTotalResultCount());
				googleAndAmazoneResponse.setSuggType(suggestions.getSuggType());
				googleAndAmazoneResponse.setStrategyId(suggestions.getStrategyId());
				googleAndAmazoneResponse.setSpellCorrected(suggestions.getSpellCorrected());

				googleAndAmazoneResponsesList.add(googleAndAmazoneResponse);
			}

			else {
				
				System.out.println("========================================else response google=======================================================");
				GoogleAndAmazoneResponse googleAndAmazoneResponse = new GoogleAndAmazoneResponse();
				KeywordIdeas keywordIdeas = googleKeywordIdeasMap.get(k);

				googleAndAmazoneResponse.setKeywordResponse(k);
				googleAndAmazoneResponse.setAverageCostPerClick(keywordIdeas.getAverageCostPerClick());
				googleAndAmazoneResponse.setCost(keywordIdeas.getCost());
				googleAndAmazoneResponse.setImpressions(keywordIdeas.getImpressions());
				googleAndAmazoneResponse.setMonthlySearches(keywordIdeas.getMonthlySearches());
				googleAndAmazoneResponse.setClicks(keywordIdeas.getClicks());
				googleAndAmazoneResponse.setCompetition(keywordIdeas.getCompetition());
				googleAndAmazoneResponse.setClickthroughRate(keywordIdeas.getClickthroughRate());

				googleAndAmazoneResponsesList.add(googleAndAmazoneResponse);
			}

		});
		amazonSuggestionsMap.keySet().stream().forEach(k -> {
			if (!googleKeywordIdeasMap.containsKey(k)) {
				GoogleAndAmazoneResponse googleAndAmazoneResponse = new GoogleAndAmazoneResponse();
 
				System.out.println("======================!googleKeywordIdeasMap.containsKey(k)======================");
				Suggestions suggestions = amazonSuggestionsMap.get(k);

				googleAndAmazoneResponse.setKeywordResponse(k);
				googleAndAmazoneResponse.setTotalResultCount(suggestions.getTotalResultCount());
				googleAndAmazoneResponse.setSuggType(suggestions.getSuggType());
				googleAndAmazoneResponse.setStrategyId(suggestions.getStrategyId());
				googleAndAmazoneResponse.setSpellCorrected(suggestions.getSpellCorrected());
				
				googleAndAmazoneResponsesList.add(googleAndAmazoneResponse);

			}
		});

		return googleAndAmazoneResponsesList;

	}

}
