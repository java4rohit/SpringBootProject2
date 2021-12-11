package com.aihello.aggragatorController;

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

import com.aihello.aggragatorService.KeywordSerachPlanService;
import com.aihello.amazon.entities.GoogleAndAmazoneResponse;
import com.aihello.google.entities.KeywordPlannerRequest;

@RestController
@RequestMapping("/scraper")
public class KeywordSearchController {

	@Autowired
	KeywordSerachPlanService keywordSerachPlanService;

	@PostMapping(value = "/getKeywordMetadate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GoogleAndAmazoneResponse>> getScapperKeywordResponse(
			@RequestBody KeywordPlannerRequest keywordPlannerRequest) throws IOException {

		List<GoogleAndAmazoneResponse> response = keywordSerachPlanService.getResponse(keywordPlannerRequest);
		return new ResponseEntity<List<GoogleAndAmazoneResponse>>(response,
				response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

}
