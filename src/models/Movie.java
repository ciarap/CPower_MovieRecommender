package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public  class Movie  implements Comparable<Movie>{
	public static Long counter= 0l;
	private Long id;
     private String title;
     private String year;
     private String url;
     private List<Rating> ratings = new ArrayList<Rating>();
     
     
	public List<Rating> getRatings() {
		return ratings;
	}


	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "Movie [id="+id+", title=" + title + ", year=" + year + ", url=" + url + "]";
	}


	public Movie(String title, String year, String url) {
		counter++;
		this.id = counter;
		this.title = title;
		this.year = year;
		this.url = url;
	}
	public Movie(Long id,String title, String year, String url) {
		this.id=id;
		this.title = title;
		this.year = year;
		this.url = url;
	}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void addRating(Rating newRating) {
		ratings.add(newRating);
		
	}


	public double getAverageRating() {
		double sum=0.0;
		if(ratings.size()>0){
		for(Rating entry: ratings){
			sum+=entry.getRating();
		}
		return sum/ratings.size();
		}
		else{
			return 0.0;
		}
	}
	@Override
	public int compareTo(Movie that) {
	 return Double.compare(this.getAverageRating(),that.getAverageRating());
	}

}
