package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.CSVLoader;

public class MovieTest {
	List<Movie> movies;
	Map<Long,Movie> index ;
	Movie movie1;
	
	@Before
    public void setUp(){
   	 index = new HashMap<>();
     movie1=new Movie("movie1","Jan","www.movie1.com");
        
    }
    
    @After
    public void takeDown(){
   	 index.clear();
   	 Movie.counter=0l;
    }
    
    @Test
    public void testCreate(){
   	 index.put(movie1.getId(),movie1);
   	 assertEquals(index.size(),movie1.getId(),0.001);
   	 assertEquals("movie1",movie1.getTitle());
   	 assertEquals("Jan",movie1.getYear());
   	 assertEquals("www.movie1.com",movie1.getUrl());
    }
    
    @Test
 	public void testLoading() throws Exception {
 		CSVLoader loader = new CSVLoader();
		movies= loader.loadMovies("movieData/items5.dat");
 		for (Movie movie : movies) {
 			index.put(movie.getId(),movie);
 		}
 		assertEquals(movies.size(), index.size());
 		assertEquals(index.size(),Movie.counter,0.001);
 	}
    
    @Test
 	public void testToString() {
 		assertEquals("Movie [id=" + movie1.getId() + ", title=movie1, year=Jan, url=www.movie1.com]", movie1.toString());
 	}
    
    @Test
 	public void testEquals() {
 		Movie movie2 = new Movie("movie1","Jan","www.movie1.com");
 		Movie movie3 = new Movie("movie3","Feb","www.movie3.com");

 		assertEquals(movie1, movie1);  //same object
 		assertNotEquals(movie1, movie2);  //same field values EXCEPT id 
 		assertNotEquals(movie1, movie3);  //different
 	}
     
    @Test 
 	public void testGetSetTitle(){
 		assertEquals("movie1", movie1.getTitle());  //checks initial
 		movie1.setTitle("movie1Replaced");  //sets new title
 		assertEquals("movie1Replaced", movie1.getTitle()); //checks new title
 		
 		try   // test empty string argument gives exception
 		 {
 	      movie1.setTitle("");
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		try   // test null argument gives exception
 		 {
 			movie1.setTitle(null);
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals("movie1Replaced", movie1.getTitle());
    }
    
    @Test 
 	public void testGetSetYear(){
 		assertEquals("Jan", movie1.getYear());  //checks initial
 		movie1.setYear("Feb");  //sets new year
 		assertEquals("Feb", movie1.getYear()); //checks new year
 		
 		try   // test empty string argument gives exception
 		 {
 	      movie1.setYear("");
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		try   // test null argument gives exception
 		 {
 			movie1.setYear(null);
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals("Feb", movie1.getYear());
    }
    
    @Test 
 	public void testGetSetUrl(){
 		assertEquals("www.movie1.com", movie1.getUrl());  //checks initial
 		movie1.setUrl("www.movie2.com");  //sets new url
 		assertEquals("www.movie2.com", movie1.getUrl()); //checks new url
 		
 		try   // test empty string argument gives exception
 		 {
 	      movie1.setUrl("");
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		try   // test null argument gives exception
 		 {
 			movie1.setUrl(null);
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals("www.movie2.com", movie1.getUrl());
    }
    
    @Test
    public void testRatings(){    
   	 Rating rating=new Rating(1l,1l,3);
   	 assertEquals(0,movie1.getRatings().size());  //test empty 
   	 movie1.getRatings().add(rating);
   	 assertEquals(1,movie1.getRatings().size());  //added rating
   	 assertTrue(movie1.getRatings().contains(rating));  //rating added is in list
    }
    
    @Test 
    public void testAverageRatings(){     // adds in three ratings and gets the average 
    	movie1.getRatings().add(new Rating(1l,1l,3));
    	movie1.getRatings().add(new Rating(1l,1l,5));
    	movie1.getRatings().add(new Rating(1l,1l,4));
    	assertEquals(4,movie1.getAverageRating(),0.001);   
    }
     
    
}
