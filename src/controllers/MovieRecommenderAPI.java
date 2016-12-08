package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI implements IMovieRecommender {

	private Map<Long, User> userIndex = new HashMap<>();
	private Map<Long, Movie> movieIndex = new HashMap<>();
	private ArrayList<Rating> ratingIndex = new ArrayList<>();
	private Serializer serializer;
	
	public MovieRecommenderAPI(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void addUser(Long id,String firstName, String lastName, int age, String gender, String occupation) {
		userIndex.put(id, new User(id,firstName,lastName,age,gender,occupation));
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
			User.counter--;
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
	public void addMovie(Long id,String title,String year, String url) {
		movieIndex.put(id, new Movie(id,title,year,url));
	}

	@Override
	public void removeMovie(Long movieID) {
		if(movieIndex.containsKey(movieID)){
			movieIndex.remove(movieID);
			Movie.counter--;
		}	
		else{
			System.out.println("There are no movies with this ID");
		}
	}

	@Override
	public void addRating(Long userID, Long movieID, int rating) {
		Rating newRating=new Rating(userID,movieID,rating);
		ratingIndex.add(newRating);
		if(movieIndex.containsKey(movieID)){
			for (Map.Entry<Long, Movie> entry : movieIndex.entrySet()){
				if (entry.getKey()==movieID){
					entry.getValue().addRating(newRating);
				}
			}
		}
		if(userIndex.containsKey(userID)){
			for (Map.Entry<Long, User> entry : userIndex.entrySet()){
				if (entry.getKey()==userID){
					entry.getValue().addRating(newRating);
				}
			}
		}
		
	}

	@Override
	public Movie getMovie(Long movieID) {
		return movieIndex.get(movieID);
	}

	@Override
	public List<Rating> getUserRatings(Long userID) {
		return userIndex.get(userID).getRatings();
		
	}

	@Override
	public ArrayList<Movie> getUserRecommendations(Long userID) {
		return null;
		
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
		
		return ratingIndex;
	}
	
	@Override
	public List<Movie> getTopTenMovies() {
		ArrayList<Movie> movies= new ArrayList<>(movieIndex.values());
		Collections.sort(movies);
		Collections.reverse(movies);
		if(movies.size()>10){
		return movies.subList(0, 10);
	}
	else{
		return movies;
	}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() throws Exception {
		serializer.read();
	    movieIndex = (Map<Long, Movie>)     serializer.pop();	
	    userIndex = (Map<Long, User>)     serializer.pop();
	    ratingIndex=(ArrayList<Rating>) serializer.pop();
	    Movie.counter = (Long) serializer.pop();
	    User.counter = (Long) serializer.pop();
	}

	@Override
	public void write() throws Exception {
		serializer.push(User.counter);
		serializer.push(Movie.counter);
		serializer.push(ratingIndex);
		serializer.push(userIndex);
	    serializer.push(movieIndex);
	    serializer.write(); 
	    
	}


public void readFileUsers(String fileName) throws Exception{
	File usersFile = new File(fileName);
    In inUsers = new In(usersFile);
      //each field is separated(delimited) by a '|'
    String delims = "[|]";
    while (!inUsers.isEmpty()) {
    	
        // get user and rating from data source
        String userDetails = inUsers.readLine();

        // parse user details string
        String[] userTokens = userDetails.split(delims);

        // output user data to console.
        if (userTokens.length == 7) {
        addUser(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]);
        }
       else
        {
            throw new Exception("Invalid member length: "+userTokens.length);
        }
}
    for (Map.Entry<Long, User> entry : userIndex.entrySet()) {
        System.out.println(entry.getValue().toString());
    }
    User.counter= (long) userIndex.size();
    System.out.println("Counter: "+User.counter);
}



public void readFileMovies(String fileName) throws Exception{
	File moviesFile = new File(fileName);
    In inMovies = new In(moviesFile);
      //each field is separated(delimited) by a '|'
    String delims = "[|]";
    while (!inMovies.isEmpty()) {
    	
        // get movie and rating from data source
        String movieDetails = inMovies.readLine();

        // parse movie details string
        String[] movieTokens = movieDetails.split(delims);

        // output user data to console.
        if (movieTokens.length == 23) {
            addMovie(Long.parseLong(movieTokens[0]),movieTokens[1],movieTokens[2],movieTokens[3]);
        }else
        {
            throw new Exception("Invalid member length: "+movieTokens.length);
        }
}
    for (Map.Entry<Long, Movie> entry : movieIndex.entrySet()) {
        System.out.println(entry.getValue().toString());
    }
    Movie.counter= (long) movieIndex.size();
    System.out.println("Counter: "+Movie.counter);
}

public void readFileRatings(String fileName) throws Exception{
	File ratingsFile = new File(fileName);
    In inRatings = new In(ratingsFile);
      //each field is separated(delimited) by a '|'
    String delims = "[|]";
    while (!inRatings.isEmpty()) {
    	
        // get rating and rating from data source
        String ratingDetails = inRatings.readLine();

        // parse rating details string
        String[] ratingTokens = ratingDetails.split(delims);

        // output user data to console.
        if (ratingTokens.length == 4) {
            addRating(Long.parseLong(ratingTokens[0]),Long.parseLong(ratingTokens[1]),Integer.parseInt(ratingTokens[2]));
        }else
        {
            throw new Exception("Invalid member length: "+ratingTokens.length);
        }
}
    for (Rating entry : ratingIndex) {
        System.out.println(entry.toString());
    }
}
 
@Override
public void listMovieRatings(Long id) {
	for (Rating entry: movieIndex.get(id).getRatings()){
		System.out.println(entry.toString());
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

}
