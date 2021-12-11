package com.aihello.google.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aihello.google.entities.KeywordIdeas;
import com.aihello.google.entities.KeywordPlannerRequest;
import com.aihello.google.service.GenerateKeywordIdeasService;

@RestController
@RequestMapping("/googlekeyword")
public class KeywordIdeasController {

	@Autowired
	GenerateKeywordIdeasService generateKeywordIdeasService;

	@PostMapping(value = "/getKeywordIdeas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KeywordIdeas>> getKeywordIdeas(@RequestBody KeywordPlannerRequest keywordPlannerRequest) throws IOException {
		List<KeywordIdeas> response = generateKeywordIdeasService.searchKeywordsIdeas( keywordPlannerRequest);
		return new ResponseEntity<>(response, response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);

	}

}
