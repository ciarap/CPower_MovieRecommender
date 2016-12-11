package models;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.CSVLoader;

public class UserTest {
	List<User> users;
	Map<Long,User> index ;
	User homer;
	
     @Before
     public void setUp(){
    	 index = new HashMap<>();
         homer=new User("homer","simpson",40,"M","technician");
         
     }
     
     @After
     public void takeDown(){
    	 index.clear();
    	 User.counter=0l;
     }
     
     @Test
     public void testCreate(){
    	 index.put(homer.getId(),homer);
    	 assertEquals(index.size(),homer.getId(),0.001);
    	 assertEquals("homer",homer.getFirstName());
    	 assertEquals("simpson",homer.getLastName());
    	 assertEquals(40,homer.getAge(),0.001);
    	 assertEquals("M",homer.getGender());
    	 assertEquals("technician",homer.getOccupation());
     }
     
    @Test
 	public void testIds() throws Exception {
 		CSVLoader loader = new CSVLoader();
		users= loader.loadUsers("movieData/users5.dat");
 		for (User user : users) {
 			index.put(user.getId(),user);
 		}
 		assertEquals(users.size(), index.size());
 		assertEquals(index.size(),User.counter,0.001);
 	}
     
     @Test
 	public void testToString() {
 		assertEquals("User[id=" + homer.getId() + ", firstName=homer, lastName=simpson, age=40, gender=M, occupation=technician]", homer.toString());
 	}
     
     @Test
 	public void testEquals() {
 		User homer2 = new User("homer", "simpson", 40, "M","technician");
 		User bart = new User("bart", "simpson",10, "M", "student");

 		assertEquals(homer, homer);
 		assertNotEquals(homer, homer2);
 		assertNotEquals(homer, bart);
 	}
     
     @Test 
 	public void testGetSetNames(){
 		assertEquals("homer", homer.getFirstName());  //checks initial
 		homer.setFirstName("homerReplaced");  //sets new name
 		assertEquals("homerReplaced", homer.getFirstName()); //checks new name
 		
 		try   // test empty string argument gives exception
 		 {
 	      homer.setFirstName("");
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		try   // test null argument gives exception
 		 {
 			homer.setFirstName(null);
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals("homerReplaced", homer.getFirstName());
 		
 		assertEquals("simpson", homer.getLastName());  //checks initial
 		homer.setLastName("simpsonReplaced");  //sets new name
 		assertEquals("simpsonReplaced", homer.getLastName()); //checks new name
 		
 		try   // test empty string argument gives exception
 		 {
 	      homer.setLastName("");
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		try   // test null argument gives exception
 		 {
 			homer.setLastName(null);
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals("simpsonReplaced", homer.getLastName());
 	}
     
     @Test 
 	public void testGetSetAge(){
 		assertEquals(40, homer.getAge(),0.001);   //checks getter
 		homer.setAge(41); //sets valid age
 		assertEquals(41, homer.getAge(),0.001);   //checks it changed

 		try   // test invalid argument gives exception
 		 {
 		 homer.setAge(-3);//sets invalid age
 		 fail("Should throw exception");
 		 }
 		 catch (Exception e)
 		 {
 		 assertTrue(true);
 		 }
 		assertEquals(41, homer.getAge(),0.001);
 	}
     
     @Test 
  	public void testGetSetGenderOccupation(){
  		assertEquals("M", homer.getGender());  //checks initial
  		homer.setGender("F");  //sets new gender
  		assertEquals("F", homer.getGender()); //checks new gender
  		
  		try   // test empty string argument gives exception
  		 {
  	      homer.setGender("");
  		 fail("Should throw exception");
  		 }
  		 catch (Exception e)
  		 {
  		 assertTrue(true);
  		 }
  		try   // test null argument gives exception
  		 {
  			homer.setGender(null);
  		 fail("Should throw exception");
  		 }
  		 catch (Exception e)
  		 {
  		 assertTrue(true);
  		 }
  		assertEquals("F", homer.getGender());
  		
  		assertEquals("technician", homer.getOccupation());  //checks initial
  		homer.setOccupation("tech");  //sets new occupation
  		assertEquals("tech", homer.getOccupation()); //checks new occupation
  		
  		try   // test empty string argument gives exception
  		 {
  	      homer.setOccupation("");
  		 fail("Should throw exception");
  		 }
  		 catch (Exception e)
  		 {
  		 assertTrue(true);
  		 }
  		try   // test null argument gives exception
  		 {
  			homer.setOccupation(null);
  		 fail("Should throw exception");
  		 }
  		 catch (Exception e)
  		 {
  		 assertTrue(true);
  		 }
  		assertEquals("tech", homer.getOccupation());
  	}
      
     @Test
     public void testRatings(){
    	 Rating rating=new Rating(1l,1l,3);
    	 assertEquals(0,homer.getRatings().size());
    	 homer.getRatings().add(rating);
    	 assertEquals(1,homer.getRatings().size());
    	 assertTrue(homer.getRatings().contains(rating));
     }

}
