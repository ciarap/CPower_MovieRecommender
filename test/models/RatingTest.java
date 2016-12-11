package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RatingTest {
	List<Rating> ratings;
	Rating rating;
	
	@Before
    public void setUp(){
   	 ratings = new ArrayList<>();
       rating=new Rating(1l,1l,4); 
    }
	 @After
     public void takeDown(){
    	 ratings.clear();
     }
	 
	 @Test
     public void testCreate(){
    	 ratings.add(rating);
    	 assertEquals(ratings.size(),1,0.001);
    	 assertEquals(1l,rating.getUserID(),0.001);
    	 assertEquals(1l,rating.getMovieID(),0.001);
    	 assertEquals(4,rating.getRating(),0.001);
     }
	 
	 @Test
	 	public void testToString() {
	 		assertEquals("Rating [userID=" + rating.getUserID() + ", movieID="+rating.getMovieID()+", rating="+rating.getRating()+"]",rating.toString());
	 	}
	 
	 @Test
	 	public void testEquals() {
	 		Rating rating2 =new Rating(1l,1l,4); 
	 		Rating rating3 =new Rating(2l,2l,2);

	 		assertEquals(rating,rating);
	 		assertEquals(rating,rating2);
	 		assertNotEquals(rating,rating3);
	 	}
	 
	 @Test 
	 	public void testGetSetUserID(){
	 		assertEquals(1l, rating.getUserID(),0.001);   //checks getter
	 		rating.setUserID(2l); //sets valid id
	 		assertEquals(2l, rating.getUserID(),0.001);   //checks it changed

	 		try   // test invalid argument gives exception
	 		 {
	 		 rating.setUserID(-3l);//sets invalid id
	 		 fail("Should throw exception");
	 		 }
	 		 catch (Exception e)
	 		 {
	 		 assertTrue(true);
	 		 }
	 		assertEquals(2l, rating.getUserID(),0.001);
	 	}
	 
	 @Test 
	 	public void testGetSetMovieID(){
	 		assertEquals(1l, rating.getMovieID(),0.001);   //checks getter
	 		rating.setMovieID(2l); //sets valid id
	 		assertEquals(2l, rating.getMovieID(),0.001);   //checks it changed

	 		try   // test invalid argument gives exception
	 		 {
	 		 rating.setMovieID(-3l);//sets invalid id
	 		 fail("Should throw exception");
	 		 }
	 		 catch (Exception e)
	 		 {
	 		 assertTrue(true);
	 		 }
	 		assertEquals(2l, rating.getMovieID(),0.001);
	 	}
	 
	 @Test 
	 	public void testGetRating(){
	 		assertEquals(4, rating.getRating(),0.001);   //checks getter
	 	}
}
