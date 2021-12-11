package com.aihello.amazon.model;

import java.util.List;


import com.aihello.google.entities.KeywordIdeas;


public class AggragatorResponse {
	KeywordResponse amazonResponse;
	List<List<KeywordIdeas>> googleResponse;

	public KeywordResponse getAmazonResponse() {
		return amazonResponse;
	}

	public void setAmazonResponse(KeywordResponse amazonResponse) {
		this.amazonResponse = amazonResponse;
	}

	public List<List<KeywordIdeas>> getGoogleResponse() {
		return googleResponse;
	}

	public void setGoogleResponse(List<List<KeywordIdeas>> googleResponse) {
		this.googleResponse = googleResponse;
	}

	
	

}
