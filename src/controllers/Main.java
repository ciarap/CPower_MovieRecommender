package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
import models.Rating;
import models.User;
import utils.CSVLoader;
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
	  CSVLoader loader = new CSVLoader();
	  List <User> users = loader.loadUsers("movieDataLong/assign2/data_movieLens/users.dat");  // SHORT DATA: "movieData/users5.dat"
	  for (User user : users)
	  {
	  likeMovies.addUser(user);
	  }
	  List <Movie> movies = loader.loadMovies("movieDataLong/assign2/data_movieLens/items.dat");  // SHORT DATA: "movieData/items5.dat"
	  for (Movie movie : movies)
	  {
	  likeMovies.addMovie(movie);
	  }
	  List <Rating> ratings = loader.loadRatings("movieDataLong/assign2/data_movieLens/ratings.dat");  // SHORT DATA: "movieData/ratings5.dat"
	  for (Rating rating:ratings)
	  {
	  likeMovies.addRating(rating);
	  }
 }
 }
 
@Command (description="Load data")
public void prime() throws Exception
{
load();
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
 
 @Command(description="Get Movie Details By Title")
 public void getMovieDetails (@Param(name="title") String title)
 {
  System.out.println(likeMovies.getMovieDetails(title));
 }
 
 @Command(description="List Users")
 public void listUsers() 
 {
	 Map<Long,User> users= likeMovies.getUsers();
	  if (users.isEmpty()){
		  System.out.println("There are no users yet");
	  }
	  else{
  for (Map.Entry<Long, User> entry :  likeMovies.getUsers().entrySet()) {
      System.out.println(entry.getValue().toString());
  }
	  }
 }
 
 @Command(description="List Movies")
 public void listMovies() 
 {
  Map<Long,Movie> movies= likeMovies.getMovies();
  if (movies.isEmpty()){
	  System.out.println("There are no movies yet");
  }
  else{
  for (Map.Entry<Long, Movie> entry :  movies.entrySet()) {
      System.out.println(entry.getValue().toString());
  }
  }
 }
 
 @Command(description="List Top TenMovies")
 public void listTopTenMovies() 
 {
  List<Movie> topMovies=likeMovies.getTopTenMovies();
  if(topMovies!=null){
  for(Movie entry:topMovies){
	  System.out.println(entry.toString());
  }
  }
  else{
	  System.out.println("There are no top movies, please add some ratings for this feature!");
  }
  
 }
 
 @Command(description="List Ratings")
 public void listRatings() 
 {
	 List<Rating> ratings=likeMovies.getRatings();
		if(ratings.size()==0){
			System.out.println("There are no ratings yet");
		}
		else{
	 for (Rating entry : ratings) {
	        System.out.println(entry.toString());
	    }
		}
 }
 
 @Command(description="List Ratings for a Movie")
 public void listMovieRatings (@Param(name="id") Long id)
 {
	 List<Rating> ratings=likeMovies.getMovieRatings(id);
	 if(ratings==null){
			System.out.println("There are no movies with this ID");
	 }
	 else if(ratings.size()==0){
			System.out.println("There are no ratings for this movie");
		}
		else{
		for(Rating rating: ratings){
			System.out.println(rating.toString());
		}
		}
	
 }
 
 @Command(description="List Ratings for a User")
 public void listUserRatings (@Param(name="id") Long id)
 {
	List<Rating> ratings=likeMovies.getUserRatings(id);
	if(ratings==null){
		System.out.println("There are no users with this ID");
 }
 else if(ratings.size()==0){
		System.out.println("There are no ratings for this user");
	}
	else{
	for(Rating rating: ratings){
		System.out.println(rating.toString());
	}
	}
 }
 
 @Command (description="List recommendations for a User")
 public void listUserRecommendations (@Param(name="id") Long id){
	 
	 if(likeMovies.getUserRecommendations(id)!=null){
	 for(Movie entry:likeMovies.getUserRecommendations(id)){
		 System.out.println(entry.toString());
	 }
	 }
 }
 
 @Command (description="Give Average Rating for a Movie")
 public void getAverageRating (@Param(name="id") Long id){
	 likeMovies.getAverageRating(id);
 }
 
 @Command (description="Get specific rating")
 public void getRating (@Param(name="userID") Long userID,@Param(name="movieID") Long movieID){
	 likeMovies.getRating(userID, movieID);
 }
  
 public static void main(String[] args) throws Exception
 {
 Main main = new Main();
 Shell shell = ShellFactory.createConsoleShell("lm", "Welcome to likemovie - ?help for instructions", main);
 shell.commandLoop();
 main.save();
 
 }
 public void save() throws Exception{
	 likeMovies.write();
 }
}