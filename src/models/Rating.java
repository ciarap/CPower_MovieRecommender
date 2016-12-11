package models;

import java.util.Objects;

public class Rating implements Comparable<Rating> {
	private Long userID;
	private Long movieID;
	private int rating;
	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		if (userID<=0){  
			throw new IllegalArgumentException("Error:ID is empty");
		}
		else{
			this.userID = userID;
		}
		
	}
	
	public Rating(Long userID, Long movieID, int rating) {
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return "Rating [userID=" + userID + ", movieID=" + movieID + ", rating=" + rating+"]";
	}
	
	public Long getMovieID() {
		return movieID;
	}
	
	public void setMovieID(Long movieID) {
		if (movieID<=0){  
			throw new IllegalArgumentException("Error:ID  is empty");
		}
		else{
			this.movieID = movieID;
		}
	}
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
			this.rating = rating;
	}
	@Override
	public int compareTo(Rating that) {
		return Double.compare(this.getRating(),that.getRating());
	}
	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof Rating)
	    {
	      final Rating other = (Rating) obj;
	      return Objects.equals(userID,   other.userID) 
	          &&  Objects.equals(movieID,    other.movieID)
	          &&  Objects.equals(rating,       other.rating);      
	    }
	    else
	    {
	      return false;
	    }
	  }
	
}
