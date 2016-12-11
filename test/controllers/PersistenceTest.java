package controllers;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controllers.MovieRecommenderAPI;
import models.Movie;
import models.Rating;
import models.User;
import utils.CSVLoader;
import utils.Serializer;
import utils.XMLSerializer;

import org.junit.After;
import org.junit.Test;

public class PersistenceTest {
	List<User> users=new ArrayList<User>();
	List<Movie> movies=new ArrayList<Movie>();
	List<Rating> ratings=new ArrayList<Rating>();
	private  MovieRecommenderAPI likeMovies;
			
	@After
	public void takeDown(){
		users.clear();
		movies.clear();
		ratings.clear();
		User.counter=0l;
		Movie.counter=0l;
	}
			
	  @Test
	  public void testPopulate() throws Exception
	  { 
	    MovieRecommenderAPI likeMovies = new MovieRecommenderAPI(null);
	    assertEquals(0, likeMovies.getUsers().size());
	    assertEquals(0,User.counter,0.001);
	    
	    assertEquals(0, likeMovies.getMovies().size());
	    assertEquals(0,Movie.counter,0.001);
	    
	    assertEquals(0, likeMovies.getRatings().size());
	    populate (likeMovies);

	    assertEquals(users.size(), likeMovies.getUsers().size()); 
	    assertEquals(5,User.counter,0.001);
	    assertEquals(movies.size(), likeMovies.getMovies().size()); 
	    assertEquals(10,Movie.counter,0.001);
	    assertEquals(ratings.size(), likeMovies.getRatings().size());   
	  }
	  
	  @Test
	  public void testXMLSerializer() throws Exception
	  { 
	    String datastoreFile = "testdatastore.xml";
	    deleteFile (datastoreFile);

	    Serializer serializer = new XMLSerializer(new File (datastoreFile));

	    likeMovies = new MovieRecommenderAPI(serializer); 
	    populate(likeMovies);
	    likeMovies.write();

	    MovieRecommenderAPI likeMovies2 =  new MovieRecommenderAPI(serializer);
	    likeMovies2.load();

	    assertEquals (likeMovies.getUsers().size(), likeMovies2.getUsers().size());
	    assertEquals(5,User.counter,0.001);
	    
	    assertEquals (likeMovies.getMovies().size(), likeMovies2.getMovies().size());
	    assertEquals(10,Movie.counter,0.001);
	    
	    assertEquals (likeMovies.getRatings().size(), likeMovies2.getRatings().size());
	    
	    for (User user : likeMovies.getUsers().values())
	    {
	      assertTrue(likeMovies2.getUsers().values().contains(user));
	    }
	    for (Movie movie : likeMovies.getMovies().values())
	    {
	      assertTrue(likeMovies2.getMovies().values().contains(movie));
	    }
	    for (Rating rating : likeMovies.getRatings())
	    {
	      assertTrue(likeMovies2.getRatings().contains(rating));
	    }
	    
	    deleteFile ("testdatastore.xml");
	  }
	  
	  
	  void deleteFile(String fileName)
	  {
	    File datastore = new File ("testdatastore.xml");
	    if (datastore.exists())
	    {
	      datastore.delete();
	    }
	  }
	void populate (MovieRecommenderAPI likeMovies3) throws Exception{
		
		CSVLoader loader = new CSVLoader();
		users= loader.loadUsers("movieData/users5.dat");
		for(User user:users){
			likeMovies3.addUser(user);
		}
		movies=loader.loadMovies("movieData/items5.dat");
		for(Movie movie:movies){
			likeMovies3.addMovie(movie);
		}
		ratings=loader.loadRatings("movieData/ratings5.dat");
		for(Rating rating:ratings){
			likeMovies3.addRating(rating);
		}
	}
}
