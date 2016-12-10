package controllers;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI implements IMovieRecommender{

	private Map<Long, User> userIndex = new HashMap<>();
	private Map<Long, Movie> movieIndex = new HashMap<>();
	private Serializer serializer;

	public MovieRecommenderAPI(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void addUser(User user) {
		userIndex.put(user.getId(), user);
	}
	@Override
	public void addUser(String firstName, String lastName, int age, String gender, String occupation) {
		User user=new User(firstName,lastName,age,gender,occupation);
		userIndex.put(user.getId(), user);
	}

	@Override
	public void removeUser(Long userID) {
		if(userIndex.containsKey(userID)){
			userIndex.remove(userID);
			for(Movie movie: movieIndex.values()){
				for(int j=0;j<movie.getRatings().size();j++){
					if(movie.getRatings().get(j).getUserID()==userID){
						movie.getRatings().remove(j);
						j--;
					}
				}
			}
		}	
		else{
			System.out.println("There are no users with this ID");
		}
	}

	@Override
	public void addMovie(String title, String year, String url) {
		Movie movie=new Movie(title,year,url);
		movieIndex.put(movie.getId(),movie);

	}
	@Override
	public void addMovie(Movie movie) {
		movieIndex.put(movie.getId(), movie);
	}

	@Override
	public void removeMovie(Long movieID) {
		if(movieIndex.containsKey(movieID)){
			movieIndex.remove(movieID);
			for(User user: userIndex.values()){
				for(int j=0;j<user.getRatings().size();j++){
					if(user.getRatings().get(j).getMovieID()==movieID){
						user.getRatings().remove(j);
						j--;
					}
				}
			}
		}	
		else{
			System.out.println("There are no movies with this ID");
		}
	}

	@Override
	public void addRating(Long userID, Long movieID, int rating) {
		Rating newRating=new Rating(userID,movieID,rating);
		boolean alreadyRated=false;
		if(movieIndex.containsKey(movieID)){
			for (Map.Entry<Long, Movie> entry : movieIndex.entrySet()){
				if (entry.getKey().equals(movieID)){
					for(Rating movieRating:entry.getValue().getRatings()){
						if(userID.equals(movieRating.getUserID())){
							alreadyRated=true;
							break;
						}
					}
					if(userIndex.containsKey(userID) && !alreadyRated){
					entry.getValue().addRating(newRating);
					}
					break;
				}
			}
		
		if(userIndex.containsKey(userID) && !alreadyRated){
			for (Map.Entry<Long, User> entry : userIndex.entrySet()){
				if (entry.getKey().equals(userID)){
					entry.getValue().addRating(newRating);
				}
			}
		}
		else if(!userIndex.containsKey(userID)){
			System.out.println("There are no users with this id!");
		}
		else{
			System.out.println("This user has already rated this movie");
		}
		}
		else{
			System.out.println("There are no movies with this id!");
		}

	}

	@Override
	public void addRating(Rating rating) {
			for (Map.Entry<Long, Movie> entry : movieIndex.entrySet()){
				if (entry.getKey().equals(rating.getMovieID())){
					entry.getValue().addRating(rating);
					}
			}
			for (Map.Entry<Long, User> entry : userIndex.entrySet()){
				if (entry.getKey().equals(rating.getUserID())){
					entry.getValue().addRating(rating);
				}
			}
		}

	
	@Override 
	public void getRating(Long userID,Long movieID){
		String result="";
		for(int i=0;i<userIndex.get(userID).getRatings().size();i++){
			Rating rating=userIndex.get(userID).getRatings().get(i);
			if(movieID.equals(rating.getMovieID())){
				result+=rating.toString();
			}
		}
		if (result.equals("")){
			result="This user did not rate this movie";
		}
		System.out.println(result);
	}
	@Override
	public Movie getMovie(Long movieID) {
		return movieIndex.get(movieID);
	}

	@Override
	public List<Rating> getUserRatings(Long userID) {
		if (movieIndex.containsKey(userID)){
			return userIndex.get(userID).getRatings();
		}
		else{
			return null;
		}

	}

	@Override
	public List<Movie> getUserRecommendations(Long userID) {
		ArrayList<Movie> recommendations=new ArrayList<Movie>();
		User user = userIndex.get(userID);
		if(!getRatings().isEmpty() && !user.getRatings().isEmpty() ){
			User smallest=user;
			User largest=user;
			int highestSum= Integer.MIN_VALUE;
			User closestUser=user;
			for (User entry:userIndex.values()){
				if(!entry.equals(user)){
					if (user.getRatings().size()>entry.getRatings().size()) {
						smallest=entry;
					}
					else{
						largest=entry;
					}
					int sum=0;
					boolean sameMoviesRated=false;
					for(int i=0;i<smallest.getRatings().size();i++){
						for (int j=0;j<largest.getRatings().size();j++){
							if(smallest.getRatings().get(i).getMovieID()==largest.getRatings().get(j).getMovieID()){
								sum+=smallest.getRatings().get(i).getRating()* largest.getRatings().get(j).getRating();
								sameMoviesRated=true;
							}
						}
					}
					if(highestSum==Integer.MIN_VALUE && sameMoviesRated){
						highestSum=sum;
						closestUser=entry;
					}
					else{
						if (highestSum<sum && sameMoviesRated){
							highestSum=sum;
							closestUser=entry;
						}
					}
				}
			}
			if(!closestUser.equals(user)){
					ArrayList<Rating> allRatings=new ArrayList<Rating>(closestUser.getRatings());
					Collections.sort(allRatings);
					Collections.reverse(allRatings);
					for (Rating entry:allRatings){
						boolean alreadyRated=false;
						for (Rating userEntry : user.getRatings()){
							if (entry.getMovieID().equals(userEntry.getMovieID())) {
								alreadyRated=true;
							}
						}
						if (!alreadyRated){
							recommendations.add(getMovie(entry.getMovieID()));
						}
					}

					if(recommendations.size()>10){
						return recommendations.subList(0, 10);
					}
					else if(recommendations.size()==0){
						System.out.println("This user has rated all recommendations from a similar user!\nFor now, these are the top rated movies:");
						for(Movie movie:getTopTenMovies()){
							System.out.println(movie.toString());
						}
						return null;
					}
					else{
						return recommendations;
					}
			}
			else{
				System.out.println("There are no similar users to this user to provide personalised recommendations!\nFor now, these are the top rated movies:");
				for(Movie movie:getTopTenMovies()){
					System.out.println(movie.toString());
				}
				return null;
			}
		}
		else if (!getRatings().isEmpty()){
			System.out.println("Please add some ratings for this user to get personalised recommendations.\nFor now, these are the top rated movies:");
			for(Movie movie:getTopTenMovies()){
				System.out.println(movie.toString());
			}
			return null;
		}
		else{
			System.out.println("Please add some ratings for this user, and other users, to get personalised recommendations.");
			return null;
		}

	}

	@Override
	public Map<Long, User> getUsers() {

		return userIndex;
	}

	@Override
	public Map<Long, Movie> getMovies() {
		return movieIndex;
	}
	@Override
	public ArrayList<Rating> getRatings() {
		ArrayList<Rating> ratingIndex=new ArrayList<Rating>();
		for(User user:userIndex.values()){
			for(Rating rating:user.getRatings()){
				ratingIndex.add(rating);
			}
		}
		return ratingIndex;
	}

	@Override
	public List<Movie> getTopTenMovies() {
		boolean anyRating=false;
		if(!getRatings().isEmpty()){
			anyRating=true;
		}
		if(anyRating){
			List<Movie> movies=new ArrayList<Movie> ();
			for(Movie movie:getMovies().values()){
				if(!movie.getRatings().isEmpty()){
					movies.add(movie);
				}
			}
			Collections.sort(movies);
			Collections.reverse(movies);
			if(movies.size()>10){
				return movies.subList(0, 10);
			}
			else{
				return movies;
			}
		}
		else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() throws Exception {
		serializer.read();
		movieIndex = (Map<Long, Movie>)     serializer.pop();	
		userIndex = (Map<Long, User>)     serializer.pop();
		Movie.counter = (Long) serializer.pop();
		User.counter = (Long) serializer.pop();
	}

	@Override
	public void write() throws Exception {
		serializer.push(User.counter);
		serializer.push(Movie.counter);
		serializer.push(userIndex);
		serializer.push(movieIndex);
		serializer.write(); 

	}





	@Override
	public List<Rating> getMovieRatings(Long id) {
		if (movieIndex.containsKey(id)){
			return movieIndex.get(id).getRatings();
		}
		else{
			return null;
		}
	}

	@Override
	public String getMovieDetails(String title) {
		String details="There are no movies with this title";
		for (Movie entry: movieIndex.values()){
			if(entry.getTitle().equalsIgnoreCase(title)){
				details=entry.toString();
			}

		}
		return details;
	}

	@Override
	public void getAverageRating(Long id) {
		if (!movieIndex.get(id).getRatings().isEmpty()){
		System.out.println( movieIndex.get(id).getAverageRating());
		}
		else {
			System.out.println("This movie has no ratings yet");
		}

	}



}
