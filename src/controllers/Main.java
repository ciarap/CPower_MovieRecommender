package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;
import utils.XMLSerializer;

public class Main
{
  public File  datastore = new File("datastore.xml");
 public  Serializer serializer = new XMLSerializer(datastore);
 public  MovieRecommenderAPI likeMovies= new MovieRecommenderAPI(serializer);
  
 public void load () throws Exception{
	  
	  if (datastore.isFile())
	    {
	      likeMovies.load();
	    }
	  else{
	likeMovies.readFileUsers("movieData/users5.dat");
	likeMovies.readFileMovies("movieData/items5.dat");
	likeMovies.readFileRatings("movieData/ratings5.dat");
	likeMovies.write();
	  }

 }
 
 @Command(description="Add a new User")
 public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName,
 @Param(name="age")int age, @Param(name="gender") String gender, @Param(name="occupation") String occupation)
 {
 likeMovies.addUser(firstName, lastName, age, gender, occupation);
 }

 @Command(description="Delete a User")
 public void removeUser (@Param(name="id") Long id)
 {
 likeMovies.removeUser(id);
 }
 
 @Command(description="Add a Movie")
 public void addMovie (@Param(name="title") String title, @Param(name="year") String year, @Param(name="url") String url)
 {
 likeMovies.addMovie(title, year, url);
 }
 @Command(description="Add a Rating")
 public void addRating (@Param(name="userID") Long userID, @Param(name="movieID") Long movieID, @Param(name="rating") int rating)
 {
 likeMovies.addRating(userID,movieID,rating);
 }
 
 @Command(description="Delete a Movie")
 public void removeMovie (@Param(name="id") Long id)
 {
 likeMovies.removeMovie(id);
 }
 
 @Command(description="Get Movie Details")
 public void getMovieDetails (@Param(name="title") String title)
 {
  System.out.println(likeMovies.getMovieDetails(title));
 }
 
 @Command(description="List Users")
 public void listUsers() 
 {
  for (Map.Entry<Long, User> entry :  likeMovies.getUsers().entrySet()) {
      System.out.println(entry.getValue().toString());
  }
  System.out.println("Counter: "+User.counter);
 }
 
 @Command(description="List Movies")
 public void listMovies() 
 {
  if (likeMovies.getMovies().isEmpty()){
	  System.out.println("There are no movies");
  }
  else{
  for (Map.Entry<Long, Movie> entry :  likeMovies.getMovies().entrySet()) {
      System.out.println(entry.getValue().toString());
  }
  }
  System.out.println("Counter: "+Movie.counter);
 }
 
 @Command(description="List Top TenMovies")
 public void listTopTenMovies() 
 {
  ArrayList<Movie> topMovies=new ArrayList<>(likeMovies.getTopTenMovies());
  if(topMovies.size()>0){
  for(Movie entry:topMovies){
	  System.out.println(entry.toString());
  }
  }
  else{
	  System.out.println("There are no top movies");
  }
  
 }
 
 @Command(description="List Ratings")
 public void listRatings() 
 {
	 for (Rating entry : likeMovies.getRatings()) {
	        System.out.println(entry.toString());
	    }
 }
 
 @Command(description="List Ratings for a Movie")
 public void listMovieRatings (@Param(name="id") Long id)
 {
	likeMovies.listMovieRatings(id);
 }
 
 @Command(description="List Ratings for a User")
 public void listUserRatings (@Param(name="id") Long id)
 {
	for(Rating rating: likeMovies.getUserRatings(id)){
		System.out.println(rating.toString());
	}
 }
 
 @Command (description="List recommendations for a User")
 public void listUserRecommendations (@Param(name="id") Long id){
	 likeMovies.getUserRecommendations(id);
 }
  
 public static void main(String[] args) throws Exception
 {
 Main main = new Main();
 main.load();
 Shell shell = ShellFactory.createConsoleShell("lm", "Welcome to likemovie - ?help for instructions", main);
 shell.commandLoop();
 main.save();
 
 }
 public void save() throws Exception{
	 likeMovies.write();
 }
}