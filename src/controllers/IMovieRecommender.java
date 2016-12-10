package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import models.*;

public interface IMovieRecommender {
	void addUser(String firstName,String lastName,int age,String gender,String occupation);
	void removeUser(Long userID);
	void addMovie(String title, String year,String url);
	void addRating(Long userID, Long movieID, int rating);
	Movie getMovie(Long movieID);
	List<Rating> getUserRatings(Long userID);
	List<Movie> getUserRecommendations(Long userID);
	void load() throws Exception;
	void write() throws Exception;
	Map<Long, User> getUsers();
	void removeMovie(Long movieID);
	Map<Long, Movie> getMovies();
	ArrayList<Rating> getRatings();
	String getMovieDetails(String title);
	List<Rating> getMovieRatings(Long id);
	List<Movie> getTopTenMovies();
	void getRating(Long userID, Long movieID);
	void addUser(User user);
	void addMovie(Movie movie);
	void getAverageRating(Long id);
	void addRating(Rating rating);
}
