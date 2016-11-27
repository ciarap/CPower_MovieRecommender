package controllers;

import java.util.ArrayList;

import models.*;

public interface IMovieRecommender {
	void addUser(String id,String firstName,String lastName,int age,String gender,String occupation);
	void removeUser(int userID);
	void addMovie(String title, int year,String url);
	void addRating(int userID, int movieID, Rating rating);
	Movie getMovie(int movieID);
	Rating getUserRatings(int userID);
	ArrayList<Movie> getUserRecommendations(int userID);
	ArrayList<Movie> getTopTenMovies();
	void load() throws Exception;
	void write() throws Exception;
}
