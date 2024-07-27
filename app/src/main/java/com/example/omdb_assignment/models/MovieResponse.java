package com.example.omdb_assignment.models;

import java.util.List;

public class MovieResponse {
	private List<Movie> Search;
	private String totalResults;

	public List<Movie> getSearch() {
		return Search;
	}

	public void setSearch(List<Movie> search) {
		Search = search;
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	private String Response;

	// Getters and Setters
}
