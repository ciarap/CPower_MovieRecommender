package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.*;

public interface IMovieRecommender {
	void addUser(Long id,String firstName,String lastName,int age,String gender,String occupation);
	void addUser(String firstName,String lastName,int age,String gender,String occupation);
	void removeUser(Long userID);
	void addMovie(String title, String year,String url);
	void addRating(Long userID, Long movieID, int rating);
	Movie getMovie(Long movieID);
	List<Rating> getUserRatings(Long userID);
	ArrayList<Movie> getUserRecommendations(Long userID);
	List<Movie> getTopTenMovies();
	void load() throws Exception;
	void write() throws Exception;
	void addMovie(Long id, String title, String year, String url);
	Map<Long, User> getUsers();
	void removeMovie(Long movieID);
	Map<Long, Movie> getMovies();
	ArrayList<Rating> getRatings();
	void listMovieRatings(Long id);
	String getMovieDetails(String title);
}
