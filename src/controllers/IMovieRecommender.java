package controllers;

import java.util.ArrayList;
import java.util.Map;

import models.*;

public interface IMovieRecommender {
	void addUser(Long id,String firstName,String lastName,int age,String gender,String occupation);
	void addUser(String firstName,String lastName,int age,String gender,String occupation);
	void removeUser(Long userID);
	void addMovie(String title, String year,String url);
	void addRating(Long userID, Long movieID, Rating rating);
	Movie getMovie(Long movieID);
	Rating getUserRatings(Long userID);
	ArrayList<Movie> getUserRecommendations(Long userID);
	ArrayList<Movie> getTopTenMovies();
	void load() throws Exception;
	void write() throws Exception;
	void addMovie(Long id, String title, String year, String url);
	Map<Long, User> getUsers();
	void removeMovie(Long movieID);
	Map<Long, Movie> getMovies();
}
