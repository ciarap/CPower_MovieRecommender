package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import models.*;

public interface IMovieRecommender {

	void addUser(User user);

	void addUser(String firstName, String lastName, int age, String gender, String occupation);

	void removeUser(Long userID);

	void addMovie(String title, String year, String url);

	void addMovie(Movie movie);

	void removeMovie(Long movieID);

	void addRating(Long userID, Long movieID, int rating);

	void addRating(Rating rating);

	String getRating(Long userID, Long movieID);

	Movie getMovie(Long movieID);

	List<Rating> getUserRatings(Long userID);

	List<Movie> getUserRecommendations(Long userID);

	Map<Long, User> getUsers();

	Map<Long, Movie> getMovies();

	ArrayList<Rating> getRatings();

	List<Movie> getTopTenMovies();

	void load() throws Exception;

	void write() throws Exception;

	List<Rating> getMovieRatings(Long id);

	String getAverageRating(Long id);

	void removeRating(Long userID, Long movieID);
	
}
