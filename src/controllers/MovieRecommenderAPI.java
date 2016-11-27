package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI implements IMovieRecommender {

	private Map<String, User> userIndex = new HashMap<>();
	private Map<Integer, Movie> movieIndex = new HashMap<>();
	private Serializer serializer;
	
	public MovieRecommenderAPI(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void addUser(String id,String firstName, String lastName, int age, String gender, String occupation) {
		userIndex.put(id, new User(id,firstName,lastName,age,gender,occupation));
	}

	@Override
	public void removeUser(int userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMovie(String title, int year, String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRating(int userID, int movieID, Rating rating) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Movie getMovie(int movieID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating getUserRatings(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Movie> getUserRecommendations(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Movie> getTopTenMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() throws Exception {
		serializer.read();
	    userIndex = (Map<String, User>)     serializer.pop();	
	}

	@Override
	public void write() throws Exception {
		serializer.push(userIndex);
	//    serializer.push(movieIndex);
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
            System.out.println("UserID: "+userTokens[0]+",First Name:"+
                    userTokens[1]+",Surname:" + userTokens[2]+",Age:"+
                    Integer.parseInt(userTokens[3])+",Gender:"+userTokens[4]+",Occupation:"+
                    userTokens[5]);
        addUser(userTokens[0],userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]);
        }
        else if (userTokens.length == 23) {
            System.out.println("UserID: "+userTokens[0]+",First Name:"+
                    userTokens[1]+",Surname:" + userTokens[2]);
        }else
        {
            throw new Exception("Invalid member length: "+userTokens.length);
        }
}
    for (Map.Entry<String, User> entry : userIndex.entrySet()) {
        System.out.println(entry.getValue().toString());
    }
}

}
