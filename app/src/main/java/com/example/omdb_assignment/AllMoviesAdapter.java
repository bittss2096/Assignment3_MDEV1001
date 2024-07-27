package com.example.omdb_assignment;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.omdb_assignment.interfaces.OnMovieClicked;
import com.example.omdb_assignment.models.Movie;


import java.util.ArrayList;
import java.util.List;


public class AllMoviesAdapter extends RecyclerView.Adapter<AllMoviesAdapter.ViewHolder> {

	public Context context;
	public List<Movie> movie_list;
	public Uri uri;
	public String TAG = getClass().getName();

	OnMovieClicked on_movie_clicked;


	public AllMoviesAdapter(Context context, List<Movie> movie_list, OnMovieClicked on_movie_clicked) {
		this.context = context;
		this.movie_list = movie_list;
		this.on_movie_clicked=on_movie_clicked;

	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allmovies, parent, false);
		ViewHolder card = new ViewHolder(v);
		return card;
	}

	@Override
	public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width / 2, width / 2);
//        holder.fl_main.setLayoutParams(params);

		final Movie movie_Model_details = movie_list.get(position);


		String title = movie_Model_details.getTitle();
		String movie_yr = movie_Model_details.getYear();
		String imdb_rating = movie_Model_details.getImdbID();
		String movie_type = movie_Model_details.getType();
		String movie_poster = movie_Model_details.getPoster();



		holder.tv_movie_title.setSelected(true);
		holder.tv_movie_title.setText(title);
		holder.tv_year.setText(movie_yr);
		holder.tv_id.setText("Studio : "+title);
		holder.tv_type.setText(movie_type);
		Glide.with(context).load(Uri.parse(movie_poster)).placeholder(R.drawable.clapperboard).into(holder.iv_thumbnail);

		holder.fl_main.setOnClickListener(new View.OnClickListener() {
			@Override

			public void onClick(View v) {
				on_movie_clicked.on_movie_clicked(imdb_rating);
			}
		});


	}


	@Override
	public int getItemCount() {

		return movie_list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		TextView tv_movie_title, tv_id, tv_type, tv_year;
		ImageView iv_thumbnail, iv_edit, iv_delete;

		FrameLayout fl_main;

		public ViewHolder(View itemView) {
			super(itemView);

			fl_main = itemView.findViewById(R.id.fl_main);
			iv_thumbnail = itemView.findViewById(R.id.iv_thumbnail);
			tv_movie_title = itemView.findViewById(R.id.tv_movie_title);
			tv_id = itemView.findViewById(R.id.tv_id);
			tv_type = itemView.findViewById(R.id.tv_type);
			tv_year = itemView.findViewById(R.id.tv_year);


		}
	}

}
