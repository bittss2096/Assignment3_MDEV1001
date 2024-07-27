package com.example.omdb_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.omdb_assignment.retrofit.ApiMovieListService;
import com.example.omdb_assignment.models.MoviePreviewDetails;
import com.example.omdb_assignment.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviewActivity extends AppCompatActivity {

	LinearLayout ll_loader;
	NestedScrollView nv_scrollview;
	ImageView back_button,iv_thumbnail;

	TextView tv_movie_title, tv_imdb_rating,tv_type,tv_votes, tv_genre, tv_country, tv_language, tv_runtime, tv_releaseyear, tv_description,
			tv_director, tv_writer, tv_actors;
	private ApiMovieListService apiMovieListService;
	private final String API_KEY = "112fa45c";
	public String movie_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		ll_loader = findViewById(R.id.ll_loader);
		nv_scrollview = findViewById(R.id.nv_scrollview);

		back_button = findViewById(R.id.back_button);
		iv_thumbnail = findViewById(R.id.iv_thumbnail);
		tv_movie_title = findViewById(R.id.tv_movie_title);
		tv_imdb_rating = findViewById(R.id.tv_imdb_rating);
		tv_genre = findViewById(R.id.tv_genre);
		tv_country = findViewById(R.id.tv_country);
		tv_language = findViewById(R.id.tv_language);
		tv_runtime = findViewById(R.id.tv_runtime);
		tv_releaseyear = findViewById(R.id.tv_releaseyear);
		tv_description = findViewById(R.id.tv_description);
		tv_actors = findViewById(R.id.tv_actors);
		tv_writer = findViewById(R.id.tv_writer);
		tv_director = findViewById(R.id.tv_director);

		tv_type = findViewById(R.id.tv_type);
		tv_votes = findViewById(R.id.tv_votes);

		apiMovieListService = RetrofitClient.getRetrofitInstance().create(ApiMovieListService.class);

		movie_id = getIntent().getStringExtra("movie_id");

		fetchMovieDetails(movie_id);

		back_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

	}

	public void fetchMovieDetails(String imdbID) {

		Call<MoviePreviewDetails> call = apiMovieListService.getMovieDetails(imdbID, API_KEY);
		call.enqueue(new Callback<MoviePreviewDetails>() {
			@Override
			public void onResponse(Call<MoviePreviewDetails> call, Response<MoviePreviewDetails> response) {
				if (response.isSuccessful()) {
					MoviePreviewDetails movie = response.body();
					if (movie != null) {
						// display the image path in glide
						String movie_path = movie.getPoster();
						Glide.with(PreviewActivity.this).load(Uri.parse(movie_path)).placeholder(R.drawable.clapperboard).into(iv_thumbnail);

						String movie_title = movie.getTitle();
						tv_movie_title.setSelected(true);
						tv_movie_title.setText(movie_title);

						tv_type.setText("Studio : "+movie_title);
						tv_type.setSelected(true);
						tv_votes.setText("IMDB Votes : "+movie.getImdbVotes());

						String rating = movie.getImdbRating();
						tv_imdb_rating.setSelected(true);
						tv_imdb_rating.setText("IMDb Rating :  " + rating);

						String genre = movie.getGenre();
						tv_genre.setSelected(true);
						tv_genre.setText("Genre : "+genre);

						String country = movie.getCountry();
						tv_country.setText(country);
						tv_country.setSelected(true);

						String language = movie.getLanguage();
						tv_language.setText(language);
						tv_language.setSelected(true);

						String runtime = movie.getRuntime();
						tv_runtime.setText("Length : "+runtime);
						tv_runtime.setSelected(true);

						String release_year = movie.getYear();
						tv_releaseyear.setText("Year : "+release_year);
						tv_releaseyear.setSelected(true);

						String description = movie.getPlot();
						tv_description.setText(description);

						String director =movie.getDirector();
						String actor=movie.getActors();
						String writer=movie.getWriter();

						tv_actors.setText(actor);
						tv_writer.setText(writer);
						tv_director.setText(director);

						ll_loader.setVisibility(View.GONE);
						nv_scrollview.setVisibility(View.VISIBLE);

//								+ "\n" +
//								"Year: " + movie.getYear() + "\n" +
//								"Rated: " + movie.getRated() + "\n" +
//								"Released: " + movie.getReleased() + "\n" +
//								"Runtime: " + movie.getRuntime() + "\n" +
//								"Genre: " + movie.getGenre() + "\n" +
//								"Director: " + movie.getDirector() + "\n" +
//								"Writer: " + movie.getWriter() + "\n" +
//								"Actors: " + movie.getActors() + "\n" +
//								"Plot: " + movie.getPlot() + "\n" +
//								"Language: " + movie.getLanguage() + "\n" +
//								"Country: " + movie.getCountry() + "\n" +
//								"Awards: " + movie.getAwards() + "\n" +
//								"imdbRating: " + movie.getImdbRating() + "\n" +
//								"imdbVotes: " + movie.getImdbVotes();
//						movieDetailsTextView.setText(movieDetails);
					}
				}
			}

			@Override
			public void onFailure(Call<MoviePreviewDetails> call, Throwable t) {

			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}