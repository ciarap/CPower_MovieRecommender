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
	public void removeRating(Long userID,Long movieID){
		if (movieIndex.containsKey(movieID) && userIndex.containsKey(userID)){
			boolean movieRatingExists=false;
			for(int i=0;i<movieIndex.get(movieID).getRatings().size();i++){
				if(userID==movieIndex.get(movieID).getRatings().get(i).getUserID()){
					movieIndex.get(movieID).getRatings().remove(i);
					movieRatingExists=true;
					break;
				}
			}
			if(movieRatingExists){
			for(int i=0;i<userIndex.get(userID).getRatings().size();i++){
				if(movieID==userIndex.get(userID).getRatings().get(i).getMovieID()){
					userIndex.get(userID).getRatings().remove(i);
					break;
				}
			}
			}
			if(!movieRatingExists){
				System.out.println("There were no ratings for this user/movie combination");
			}
		}
		else{
			System.out.println("These IDs do not correspond to existing users/movies");
		}
	}
	
	@Override  //method to get a specific rating 
	public String getRating(Long userID,Long movieID){
		String result="";
		if(movieIndex.containsKey(movieID) && userIndex.containsKey(userID)){  //user and movie must exist
		for(int i=0;i<userIndex.get(userID).getRatings().size();i++){
			Rating rating=userIndex.get(userID).getRatings().get(i);  //go through each user rating
			if(movieID.equals(rating.getMovieID())){  //if it corresponds to the movie entered
				result+=rating.toString();
				break;  //user can only rate movie once, so when found we can exit loop
			}
		}
		if (result.equals("")){   //no ratings were added to string
			result="This user did not rate this movie";
		}
		return result;
		}
		else{    //if user or movie doesnt exist
			return null;
		}
	}
	
	@Override    //method to return a list of ratings for a movie 
	public Movie getMovie(Long movieID) {
		if(movieIndex.containsKey(movieID)){  //movie must exist
		return movieIndex.get(movieID);
		}
		else{
			return null;
		}
	}

	@Override   //method to return a list of ratings for a user
	public List<Rating> getUserRatings(Long userID) {
		if (userIndex.containsKey(userID)){  //user must exist
			return userIndex.get(userID).getRatings();
		}
		else{
			return null;
		}

	}

	@Override
	public List<Movie> getUserRecommendations(Long userID) {       // uses similar user and returns movies they have rated but you havent
		ArrayList<Movie> recommendations=new ArrayList<Movie>();
		User user = userIndex.get(userID);   //user we want recommendations for
		if(!getRatings().isEmpty() && !user.getRatings().isEmpty() ){  //ratings must exist, and the user must have ratings (because if no user ratings then cant find similar user)
			User smallest=user;   //variable for smallest user (based on no. ratings)
			User largest=user;    //as above but for largest   , both set to user as base 
			int highestSum= Integer.MIN_VALUE;   //most similar user will have high number for this variable, set to min starting so the first user number found will be set as the highest
			User closestUser=user;   //most similar user set to user as base
			for (User entry:userIndex.values()){    //for every user
				if(!entry.equals(user)){   //similar user cant be the user
					int sum=0;
					boolean sameMoviesRated=false;  //if both users have rated the movie in question
					for(int i=0;i<user.getRatings().size();i++){   //goes through user ratings
						for (int j=0;j<entry.getRatings().size();j++){    //goes through possible similar user ratings
							if(user.getRatings().get(i).getMovieID()==entry.getRatings().get(j).getMovieID()){  //if ratings for the same movie
								sum+=user.getRatings().get(i).getRating()* entry.getRatings().get(j).getRating();  //add in the product of the two ratings 
								sameMoviesRated=true;  //both users have rated at least one same movie
							}
						}
					}
					if(highestSum==Integer.MIN_VALUE && sameMoviesRated){  //if the highest sum hasnt changed from the initial yet, and the possible similar has rated at least one same movie as user
						highestSum=sum;   
						closestUser=entry;    //set the closest user as the possible similar just tested
					}
					else{
						if (highestSum<sum && sameMoviesRated){  //if the current sum is greater than the previous highest sum from previous closestUser found, and both users rated same movies
							highestSum=sum;
							closestUser=entry;
						}
					}
				}
			}
			if(!closestUser.equals(user)){   //if a similar user was found (closestUser would equal user as initialised if not)  
					ArrayList<Rating> allRatings=new ArrayList<Rating>(closestUser.getRatings());  
					Collections.sort(allRatings);   //sort in accordance to rating
					Collections.reverse(allRatings);  //high to low
					for (Rating entry:allRatings){    
						boolean alreadyRated=false;   //if user has already rated the movie the closest users rating applies to
						for (Rating userEntry : user.getRatings()){   //go through each of the users ratings
							if (entry.getMovieID().equals(userEntry.getMovieID())) {  //if both ratings are for same movie
								alreadyRated=true;
							}
						}
						if (!alreadyRated){    //if the closest user has a movie rated that the user does not, we add the movie to the user's recommendations
							recommendations.add(getMovie(entry.getMovieID()));
						}
					}

					if(recommendations.size()>10){      //only want 10 recommendations
						return recommendations.subList(0, 10);
					}
					else if(recommendations.size()==0){   // no movies from closest user that user hasnt rated, so just show top ten general movies
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
			else{   //if closestUser was still equal to user, then no similars found 
				System.out.println("There are no similar users to this user to provide personalised recommendations!\nFor now, these are the top rated movies:");
				for(Movie movie:getTopTenMovies()){
					System.out.println(movie.toString());
				}
				return null;
			}
		}
		else if (!getRatings().isEmpty()){   // if there are ratings , but  the user doesnt have any
			System.out.println("Please add some ratings for this user to get personalised recommendations.\nFor now, these are the top rated movies:");
			for(Movie movie:getTopTenMovies()){
				System.out.println(movie.toString());
			}
			return null;
		}
		else{   //if there are no ratings at all
			System.out.println("Please add some ratings for this user, and other users, to get personalised recommendations.");
			return null;
		}

	}

	@Override   //method to return users in a map 
	public Map<Long, User> getUsers() {
		return userIndex;
	}

	@Override  //method to return movies in a map
	public Map<Long, Movie> getMovies() {
		return movieIndex;
	}
	
	@Override     //method to return all ratings 
	public ArrayList<Rating> getRatings() {
		ArrayList<Rating> ratingIndex=new ArrayList<Rating>();
		for(User user:userIndex.values()){   //goes through each user
			for(Rating rating:user.getRatings()){
				ratingIndex.add(rating);     //adds each user rating to list
			}
		}
		return ratingIndex;
	}

	@Override   //method to get the top ten movies going by average ratings
	public List<Movie> getTopTenMovies() {
		if(!getRatings().isEmpty()){   //ratings exist
			List<Movie> movies=new ArrayList<Movie> ();
			for(Movie movie:getMovies().values()){    //for each movie
				if(!movie.getRatings().isEmpty()){   //if the movie has ratings
					movies.add(movie);   //add movie to list
				}
			}
			Collections.sort(movies);   //sort the movies with ratings
			Collections.reverse(movies);  //reverse to put highest--> lowest
			if(movies.size()>10){      
				return movies.subList(0, 10);    //return top ten if more than ten movies in list
			}
			else{
				return movies;
			}
		}
		else{
			return null;   //return null if no ratings exist   
		}
	}

	@Override
	public List<Rating> getMovieRatings(Long id) {   //get ratings for movie by id
		if (movieIndex.containsKey(id)){   //if movie exists
			return movieIndex.get(id).getRatings();  //return ratings
		}
		else{
			return null;  //if no movie exists
		}
	}

	@Override  //gets the average rating of a movie
	public String getAverageRating(Long id) {
		if(movieIndex.containsKey(id)){  //movie must exist
		if (!movieIndex.get(id).getRatings().isEmpty()){  //if movie has ratings
		 return "The average rating is: "+movieIndex.get(id).getAverageRating();
		}
		else {
			return "This movie has no ratings yet";
		}
		}
		else{
			return "This movie does not exist";
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
}
