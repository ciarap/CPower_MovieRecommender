package controllers;

import static models.Fixtures.users;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;
import utils.XMLSerializer;

public class MovieRecommenderAPITest {
	public File  datastore = new File("datastore.xml");
	public  Serializer serializer = new XMLSerializer(datastore);
	public MovieRecommenderAPI likeMovies;
	
	
	@Before
	public void setup()
	{
		likeMovies = new MovieRecommenderAPI(serializer);
		for (User user : users)
	    {
	      likeMovies.addUser(user);
	    }
		for (Movie movie : movies)
	    {
	      likeMovies.addMovie(movie);
	    }
		for (Rating rating : ratings)
	    {
	      likeMovies.addRating(rating);
	    }
	}

	@After
	public void tearDown()
	{
		User.counter=4l;
		Movie.counter=4l;
		for(User user:likeMovies.getUsers().values()){
			user.getRatings().clear();
		}
		for(Movie movie:likeMovies.getMovies().values()){
			movie.getRatings().clear();
		}
		likeMovies = null;
	}

	@Test
	public void testAddUser(){
	assertEquals(4,likeMovies.getUsers().size(),0.001);
	assertEquals(4,User.counter,0.001);
	
	User user=new User("Ciara","Power",21,"F","Cashier");   
	
	assertEquals(4,likeMovies.getUsers().size(),0.001);
	likeMovies.addUser(user);   //one type add method
	assertEquals(5,likeMovies.getUsers().size(),0.001);
	assertEquals(5,User.counter,0.001);
	
	likeMovies.addUser("Gavin","Murphy",22,"M","Cashier");   //second type add method
	assertEquals(6,likeMovies.getUsers().size(),0.001);
	assertEquals(6,User.counter,0.001);

	}
	
	@Test
	public void testRemoveUsers(){
		assertEquals(4,likeMovies.getUsers().size(),0.001);
		assertEquals(4,User.counter,0.001);
		
		assertEquals(1,likeMovies.getUserRatings(3l).size());
		assertEquals(1,likeMovies.getMovieRatings(3l).size());
		likeMovies.removeUser(2l);
		assertEquals(0,likeMovies.getMovieRatings(2l).size()); //the movie rated by this user now has no ratings
		assertEquals(3,likeMovies.getRatings().size()); //the user ratings were removed from total ratings 
		assertEquals(3,likeMovies.getUsers().size(),0.001);
		assertEquals(4,User.counter,0.001);   // does not get decremented, because if we delete user in middle 
		                                      //of index of 5,we still need the next user id to be 6,not 5 (user 5 still exists) 
		likeMovies.removeUser(123l);  //id doesnt exist
		assertEquals(3,likeMovies.getUsers().size(),0.001); //no change
	}
	
	@Test
	public void testAddMovie(){
	assertEquals(4,likeMovies.getMovies().size(),0.001);
	assertEquals(4,Movie.counter,0.001);
	
	Movie movie=new Movie("movie5","Jan","www.movie5.com");
	
	assertEquals(4,likeMovies.getMovies().size(),0.001);
	likeMovies.addMovie(movie);   //one type add method
	assertEquals(5,likeMovies.getMovies().size(),0.001);
	assertEquals(5,Movie.counter,0.001);
	
	likeMovies.addMovie("movie6","Jan","www.movie6.com");   //second type add method
	assertEquals(6,likeMovies.getMovies().size(),0.001);
	assertEquals(6,Movie.counter,0.001);
	
	
	}
	

	@Test
	public void testRemoveMovie(){
		assertEquals(4,likeMovies.getMovies().size(),0.001);
		assertEquals(4,Movie.counter,0.001);
		
	 	assertEquals(2,likeMovies.getUserRatings(2l).size());
		assertEquals(1,likeMovies.getMovieRatings(2l).size());
		likeMovies.removeMovie(2l);
		assertNull(likeMovies.getMovie(2l));
		assertEquals(1,likeMovies.getUserRatings(2l).size()); //the user does not have this rating anymore as movie doesnt exist
		assertEquals(4,likeMovies.getRatings().size()); //the movie ratings were removed from total ratings 
		assertEquals(3,likeMovies.getMovies().size(),0.001);
		assertEquals(4,Movie.counter,0.001);   // does not get decremented, because if we delete movie in middle 
		                                      //of index of 5,we still need the next movie id to be 6,not 5 (movie 5 still exists) 
		likeMovies.removeMovie(123l);  //id doesnt exist
		assertEquals(3,likeMovies.getMovies().size(),0.001); //no change
	}
	
	@Test
	public void testAddRatings(){
		assertEquals(5,likeMovies.getRatings().size(),0.001);
		
		assertEquals(5,likeMovies.getRatings().size(),0.001);
		assertEquals(1,likeMovies.getUserRatings(4l).size(),0.001);     
		assertEquals(1,likeMovies.getMovieRatings(2l).size(),0.001);
		likeMovies.addRating(4l,2l,3);
		assertEquals(6,likeMovies.getRatings().size(),0.001);      //added to total ratings 
		assertEquals(2,likeMovies.getUserRatings(4l).size(),0.001);  //added to relevant user ratings
		assertEquals(2,likeMovies.getMovieRatings(2l).size(),0.001);  //added to relevant movie ratings
		
		likeMovies.addRating(4l,2l,3); //try add the same rating again
		assertEquals(6,likeMovies.getRatings().size(),0.001); // no changes anywhere because duplicate rating wasnt added
		assertEquals(2,likeMovies.getUserRatings(4l).size(),0.001);
		assertEquals(2,likeMovies.getMovieRatings(2l).size(),0.001);
		
	}
	
	@Test
	public void testRemoveRatings(){
		assertEquals(5,likeMovies.getRatings().size(),0.001);
		
	    assertEquals(1,likeMovies.getUserRatings(4l).size()); 
		assertEquals(3,likeMovies.getMovieRatings(1l).size());
		likeMovies.removeRating(4l,1l);
		assertEquals(4,likeMovies.getRatings().size(),0.001);
		assertEquals(0,likeMovies.getUserRatings(4l).size()); //the user does not have this rating anymore
		assertEquals(2,likeMovies.getMovieRatings(1l).size()); //the rating has been removed from the movies ratings
		
		likeMovies.removeRating(123l,1l);  // userid doesnt exist
		likeMovies.removeRating(1l,14567l); //movieID doesnt exist
		likeMovies.removeRating(123l,14567l);  // both id dont exist
		likeMovies.removeRating(4l, 4l);
		assertEquals(4,likeMovies.getRatings().size(),0.001); //no changes 
		assertEquals(0,likeMovies.getUserRatings(4l).size()); 
		assertEquals(2,likeMovies.getMovieRatings(1l).size());
	}
	
	@Test
	public void testGetRating(){
		assertEquals("Rating [userID=4, movieID=1, rating=-4]",likeMovies.getRating(4l, 1l)); //valid ids
		assertNull(likeMovies.getRating(4433l, 1l));   //id doesnt exist
		assertNull(likeMovies.getRating(4l, 17766l));
		assertNull(likeMovies.getRating(4678l, 16456l));
		assertEquals("This user did not rate this movie",likeMovies.getRating(4l, 4l));  // valid ids but no rating corresponding
	}
	
	@Test
	public void testGetMovie(){
		assertEquals(movies[0],likeMovies.getMovie(1l)); //valid id
		assertNull(likeMovies.getMovie(4433l));   //id doesnt exist
	}
	
	@Test
	public void testGetUserRatings(){
		List<Rating> userRatings= new ArrayList<Rating>();  //add in ratings we know are for this user in fixtures
		userRatings.add(ratings[1]);
		userRatings.add(ratings[2]);
		assertEquals(userRatings,likeMovies.getUserRatings(2l));  
		
		assertNull(likeMovies.getUserRatings(234l));  //no user for this id
		
		userRatings.clear();
		likeMovies.addUser(new User("ciara","power",21,"F","Cashier"));  //new user no ratings yet
		assertEquals(userRatings,likeMovies.getUserRatings(5l));
	}
	
	@Test 
	public void testGetUsers(){
		assertEquals(users.length,likeMovies.getUsers().size());
		assertEquals(users[3],likeMovies.getUsers().get(users[3].getId())); // tests the last user in fixtures is in the returned map, no users cut off
		
		likeMovies.getUsers().clear();    //to test when no users, empty map returned
		assertEquals(0,likeMovies.getUsers().size());
	}
	@Test 
	public void testGetMovies(){
		assertEquals(movies.length,likeMovies.getMovies().size());
		assertEquals(movies[3],likeMovies.getMovies().get(movies[3].getId())); // tests the last movie in fixtures is in the returned map, no movies cut off
		
		likeMovies.getMovies().clear();    //to test when no movies, empty map returned
		assertEquals(0,likeMovies.getMovies().size());
	}
	
	@Test 
	public void testGetRatings(){
		assertEquals(ratings.length,likeMovies.getRatings().size());
		assertEquals(ratings[4],likeMovies.getRatings().get(4)); // tests the last rating in fixtures is in the returned list, no ratings cut off
		
		for(User user:likeMovies.getUsers().values()){
			user.getRatings().clear();
		}
		for(Movie movie:likeMovies.getMovies().values()){
			movie.getRatings().clear();
		}                                                //to test when no ratings, empty list returned
		assertEquals(0,likeMovies.getRatings().size());
	}
	
	@Test 
	public void testGetTopTenMovies(){
		assertEquals(3,likeMovies.getTopTenMovies().size());  // only 3 movies from fixtures have ratings
		assertEquals(movies[2],likeMovies.getTopTenMovies().get(0));  // highest average rating 
		assertEquals(movies[1],likeMovies.getTopTenMovies().get(1));  // second highest average rating 
		assertEquals(movies[0],likeMovies.getTopTenMovies().get(2));  // lowest average rating 
		//add movies and ratings to give more than ten movies rated
		likeMovies.addMovie(new Movie("movie5","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie6","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie7","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie8","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie9","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie10","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie11","Jan","www.movie1.com"));
		likeMovies.addMovie(new Movie("movie12","Jan","www.movie1.com"));
		likeMovies.addRating(4l,5l,3);
		likeMovies.addRating(4l,6l,3);
		likeMovies.addRating(4l,7l,3);
		likeMovies.addRating(4l,8l,3);
		likeMovies.addRating(4l,9l,3);
		likeMovies.addRating(4l,10l,3);
		likeMovies.addRating(4l,11l,3);
		likeMovies.addRating(4l,12l,3);
	
		assertEquals(10,likeMovies.getTopTenMovies().size());  // only 3 movies from fixtures have ratings
		assertEquals(movies[2],likeMovies.getTopTenMovies().get(0));  // highest average rated movie
		assertEquals(2,likeMovies.getTopTenMovies().get(9).getAverageRating(),0.001);  // lowest average rating listed should be movie with av rating 2 
		
	}
	
	@Test
	public void testGetMovieRatings(){
		List<Rating> movieRatings= new ArrayList<Rating>();  //create list with the ratings for movie[0] in fixtures
		movieRatings.add(ratings[0]);
		movieRatings.add(ratings[1]);
		movieRatings.add(ratings[4]);
		assertEquals(movieRatings,likeMovies.getMovieRatings(1l));   // test if list of known ratings equals returned list
		
		assertNull(likeMovies.getMovieRatings(234l));  //no user for this id
		
		movieRatings.clear();   //clear the known ratings
		likeMovies.addMovie(new Movie("movie5","Jan","www.movie5.com"));  //new movie no ratings yet
		assertEquals(movieRatings,likeMovies.getMovieRatings(5l)); //test empty list returned
	}
	
	@Test
	public void testGetAverageRating(){
		assertEquals("This movie does not exist",likeMovies.getAverageRating(2345l));  //movie doesnt exist
		assertEquals("This movie has no ratings yet",likeMovies.getAverageRating(4l));  //no ratings for this movie
		assertEquals("The average rating is: "+2.0,likeMovies.getAverageRating(2l));   //movie with one rating
		assertEquals("The average rating is: "+-0.6666666666666666,likeMovies.getAverageRating(1l));   //movie with more than one rating
	}
	
	@Test
	public void testGetUserRecommendations(){
		List<Movie> movieRecs=new ArrayList<Movie>();
		movieRecs.add(movies[1]);  // User 1 has similar user 2, user 2 has rated movies[1] and user has not
		assertEquals(movieRecs,likeMovies.getUserRecommendations(1l));
		assertNull(likeMovies.getUserRecommendations(3l));  //no similar users to user id 3
		assertNull(likeMovies.getUserRecommendations(2l));  //similar user is user id 1, but they havent rated any movies that user id 2 hasnt rated
		
		likeMovies.addUser("Gavin","Murphy",22,"M","Cashier"); //no ratings yet
		assertNull(likeMovies.getUserRecommendations(5l)); 
		
		for(User user:likeMovies.getUsers().values()){  //delete all ratings
			user.getRatings().clear();
		}
		assertNull(likeMovies.getUserRecommendations(1l));  //no ratings at all so no recommendations returned 
		assertNull(likeMovies.getUserRecommendations(5l));
		
		
	}
}
