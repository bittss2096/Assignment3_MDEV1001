package com.example.omdb_assignment.retrofit;

import com.example.omdb_assignment.models.MoviePreviewDetails;
import com.example.omdb_assignment.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMovieListService {
	@GET("/")
	Call<MovieResponse> searchMovies(@Query("s") String query, @Query("apikey") String apiKey);

	@GET("/")
	Call<MoviePreviewDetails> getMovieDetails(@Query("i") String imdbID, @Query("apikey") String apiKey);
}