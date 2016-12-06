package models;

public class Rating {
	private Long userID;
	private Long movieID;
	private Long rating;
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Rating(Long userID, Long movieID, Long rating) {
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
		this.movieID = movieID;
	}
	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}
	
}
