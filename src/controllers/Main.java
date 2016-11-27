package controllers;

import java.io.File;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import utils.Serializer;
import utils.XMLSerializer;


/*public class Main
{
  public static void main(String[] args) throws Exception
  { 
		 File  datastore = new File("datastore.xml");
		 Serializer serializer = new XMLSerializer(datastore);
		    
		  MovieRecommenderAPI movieRecommenderAPI = new MovieRecommenderAPI(serializer);
		  if (datastore.isFile())
		    {
		      movieRecommenderAPI.load();
		    }
		movieRecommenderAPI.readFileUsers("movieData/users5.dat");
		movieRecommenderAPI.readFileUsers("movieData/items5.dat");
		
	  }
}*/

public class Main
{
 public MovieRecommenderAPI likeMovies;
 
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
 public static void main(String[] args) throws Exception
 {
 Main main = new Main();
 Shell shell = ShellFactory.createConsoleShell("lm", "Welcome to likemovie - ?help for instructions", main);
 shell.commandLoop();
 main.likeMovies.store();
 }
}