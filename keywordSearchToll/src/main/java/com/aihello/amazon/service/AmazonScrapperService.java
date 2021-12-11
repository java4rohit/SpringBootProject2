package com.aihello.amazon.service;

import java.io.IOException;

import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.Suggestions;
import com.aihello.google.entities.KeywordPlannerRequest;

public interface AmazonScrapperService {

	public KeywordResponse requestKeyWords(KeywordPlannerRequest keywordPlannerRequest);

	public void keywordMetadata(Suggestions suggestions) throws IOException;

}
