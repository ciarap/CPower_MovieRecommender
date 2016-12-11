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
				likeMovies.addUser(user);   //add each user 
			}
			List <Movie> movies = loader.loadMovies("movieDataLong/assign2/data_movieLens/items.dat");  // SHORT DATA: "movieData/items5.dat"
			for (Movie movie : movies)
			{
				likeMovies.addMovie(movie); //add each movie
			}
			List <Rating> ratings = loader.loadRatings("movieDataLong/assign2/data_movieLens/ratings.dat");  // SHORT DATA: "movieData/ratings5.dat"
			for (Rating rating:ratings)
			{
				likeMovies.addRating(rating); //add each rating
			}
			likeMovies.write();  //save when all is added
		}
	}

	@Command (description="Load data")  //program starts off empty 
	public void prime() throws Exception
	{
		load();   
	}


	@Command(description="Add a new User")  //add user using parameters
	public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName,
			@Param(name="age")int age, @Param(name="gender") String gender, @Param(name="occupation") String occupation)
	{
		likeMovies.addUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description="Delete a User")  //delete user by id
	public void removeUser (@Param(name="id") Long id)
	{
		likeMovies.removeUser(id);
	}

	@Command(description="Add a Movie")   //add movie using parameters
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year, @Param(name="url") String url)
	{
		likeMovies.addMovie(title, year, url);
	}

	@Command(description="Add a Rating")  //add rating by using parameters
	public void addRating (@Param(name="userID") Long userID, @Param(name="movieID") Long movieID, @Param(name="rating") int rating)
	{
		likeMovies.addRating(userID,movieID,rating);
	}

	@Command(description="Delete a Movie")  //delete movie by id
	public void removeMovie (@Param(name="id") Long id)
	{
		likeMovies.removeMovie(id);
	}

	@Command(description="Get Movie")  //print out movie details by id
	public void getMovie(@Param(name="id") Long id)
	{
		Movie movie=likeMovies.getMovie(id);
		if(movie==null){   //no movie returned
           System.out.println("This movie does not exist");
		}
		else{
			System.out.println(likeMovies.getMovie(id).toString());
		}
	}

	@Command(description="List Users")  //list all users
	public void listUsers() 
	{
		Map<Long,User> users= likeMovies.getUsers();
		if (users.isEmpty()){   //no users returned
			System.out.println("There are no users yet");
		}
		else{
			for (Map.Entry<Long, User> entry :  likeMovies.getUsers().entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	@Command(description="List Movies")  //list all movies
	public void listMovies() 
	{
		Map<Long,Movie> movies= likeMovies.getMovies();
		if (movies.isEmpty()){  //no movies returned
			System.out.println("There are no movies yet");
		}
		else{
			for (Map.Entry<Long, Movie> entry :  movies.entrySet()) {
				System.out.println(entry.getValue().toString());
			}
		}
	}

	@Command(description="List Top TenMovies")  //list top ten average rated movies
	public void listTopTenMovies() 
	{
		List<Movie> topMovies=likeMovies.getTopTenMovies();
		if(topMovies!=null){  //if there are movies rated
			for(Movie entry:topMovies){
				System.out.println(entry.toString());
			}
		}
		else{  //no movies/no movies rated
			System.out.println("There are no top movies, please add some movies/ratings for this feature!");
		}

	}

	@Command(description="List Ratings")  //list all ratings
	public void listRatings() 
	{
		List<Rating> ratings=likeMovies.getRatings();
		if(ratings.size()==0){  //no ratings made 
			System.out.println("There are no ratings yet");
		}
		else{
			for (Rating entry : ratings) {
				System.out.println(entry.toString());
			}
		}
	}

	@Command(description="List Ratings for a Movie")  //list ratings for one movie
	public void listMovieRatings (@Param(name="id") Long id)
	{
		List<Rating> ratings=likeMovies.getMovieRatings(id);
		if(ratings==null){   //no movie exists
			System.out.println("There are no movies with this ID");
		}
		else if(ratings.size()==0){  //movie doesnt have ratings
			System.out.println("There are no ratings for this movie");
		}
		else{
			for(Rating rating: ratings){
				System.out.println(rating.toString());
			}
		}

	}

	@Command(description="List Ratings for a User")  //list ratings for a user
	public void listUserRatings (@Param(name="id") Long id)
	{
		List<Rating> ratings=likeMovies.getUserRatings(id);
		if(ratings==null){ //user doesnt exist
			System.out.println("There are no users with this ID");
		}
		else if(ratings.size()==0){  //user doesnt have ratings
			System.out.println("There are no ratings for this user");
		}
		else{
			for(Rating rating: ratings){
				System.out.println(rating.toString());
			}
		}
	}

	@Command (description="List recommendations for a User")  //get top ten recommendations for a user
	public void listUserRecommendations (@Param(name="id") Long id){

		if(likeMovies.getUserRecommendations(id)!=null){  //if there are recommendations
			for(Movie entry:likeMovies.getUserRecommendations(id)){
				System.out.println(entry.toString());
			}
		}
	}

	@Command (description="Give Average Rating for a Movie")  //get average rating for specific movie
	public void getAverageRating (@Param(name="id") Long id){
		System.out.println(likeMovies.getAverageRating(id));
	}

	@Command (description="Get specific rating")
	public void getRating (@Param(name="userID") Long userID,@Param(name="movieID") Long movieID){
		String result=likeMovies.getRating(userID, movieID);
		if(result==null){
			System.out.println("These IDs do not correspond to existing users/movies");
		}
		else{
			System.out.println(result);
		}
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