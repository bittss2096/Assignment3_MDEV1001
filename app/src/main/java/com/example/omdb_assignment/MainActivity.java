package com.example.omdb_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omdb_assignment.retrofit.ApiMovieListService;
import com.example.omdb_assignment.interfaces.OnMovieClicked;
import com.example.omdb_assignment.models.Movie;
import com.example.omdb_assignment.models.MovieResponse;
import com.example.omdb_assignment.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	EditText et_movies;
	ImageView iv_search;
	TextView tv_message;
	RecyclerView rv_movieslist;
	AllMoviesAdapter all_movies_adapter;
	private List<Movie> movieList = new ArrayList<com.example.omdb_assignment.models.Movie>();
	private ApiMovieListService apiMovieListService;
	private final String API_KEY = "112fa45c";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_movies = findViewById(R.id.et_movies);
		iv_search = findViewById(R.id.iv_search);
		tv_message = findViewById(R.id.tv_message);
		rv_movieslist = findViewById(R.id.rv_movieslist);

		load_initial_view();

		rv_movieslist = findViewById(R.id.rv_movieslist);
		rv_movieslist.setLayoutManager(new LinearLayoutManager(this));


		apiMovieListService = RetrofitClient.getRetrofitInstance().create(ApiMovieListService.class);


		iv_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (et_movies.getText().toString().trim().isEmpty() || et_movies.getText().toString().trim().length() == 0) {
					Toast.makeText(MainActivity.this, "You need to enter some movie name to search", Toast.LENGTH_SHORT).show();
				} else {
					try {
						String keyword = et_movies.getText().toString().trim();
						hideSoftKeyboard();
						searchMovies(keyword);
					} catch (Exception e) {
						load_error_view();
						Log.e("mango", "exception   " + e.getMessage());
					}
				}
			}
		});


	}

	public void searchMovies(String query) {
		apiMovieListService.searchMovies(query, API_KEY).enqueue(new Callback<MovieResponse>() {
			@Override
			public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
				if (response.body() != null && response.body().getResponse().equals("True")) {
					movieList.clear();
					movieList.addAll(response.body().getSearch());
					all_movies_adapter = new AllMoviesAdapter(MainActivity.this, movieList, onMovieClicked);
					rv_movieslist.setAdapter(all_movies_adapter);
					//	all_movies_adapter.notifyDataSetChanged();
					load_result_view();
				} else {
					load_no_result_view();
					Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Call<MovieResponse> call, Throwable t) {
				load_error_view();
				Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	OnMovieClicked onMovieClicked = new OnMovieClicked() {
		@Override
		public void on_movie_clicked(String id) {
			hideSoftKeyboard();
			Intent i = new Intent(MainActivity.this, PreviewActivity.class);
			i.putExtra("movie_id", id);
			startActivity(i);
		}
	};

	public void load_initial_view() {
		tv_message.setText(getResources().getString(R.string.initial_message));
		rv_movieslist.setVisibility(View.GONE);
		tv_message.setVisibility(View.VISIBLE);
	}

	public void load_result_view() {
		rv_movieslist.setVisibility(View.VISIBLE);
		tv_message.setVisibility(View.GONE);
	}

	public void load_no_result_view() {
		tv_message.setText(getResources().getString(R.string.no_record));
		rv_movieslist.setVisibility(View.GONE);
		tv_message.setVisibility(View.VISIBLE);
	}

	public void load_error_view() {
		tv_message.setText(getResources().getString(R.string.error_message));
		rv_movieslist.setVisibility(View.GONE);
		tv_message.setVisibility(View.VISIBLE);
	}


	@Override
	public void onBackPressed() {
		finishAffinity();
		finish();
		super.onBackPressed();
	}

	private void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		View currentFocusedView = getCurrentFocus();
		if (currentFocusedView != null) {
			inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
		}
	}
}