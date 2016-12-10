package models;

public class Rating implements Comparable<Rating> {
	private Long userID;
	private Long movieID;
	private int rating;
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
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
		this.movieID = movieID;
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
	
}
