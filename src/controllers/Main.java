package controllers;

import java.io.File;
import java.util.Map;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
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
	likeMovies.readFileUsers("movieData/users5.dat");
	likeMovies.readFileMovies("movieData/items5.dat");

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
 
 @Command(description="Delete a Movie")
 public void removeMovie (@Param(name="id") Long id)
 {
 likeMovies.removeMovie(id);
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
  for (Map.Entry<Long, Movie> entry :  likeMovies.getMovies().entrySet()) {
      System.out.println(entry.getValue().toString());
  }
  System.out.println("Counter: "+Movie.counter);
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