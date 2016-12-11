package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		if (title==""){  
			throw new IllegalArgumentException("Error:Title String is empty");
		}
		else if(title==null){   
			throw new NullPointerException("Error:Title is null");
		}
		else{
		this.title = title;
		}
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		if (year==""){  
			throw new IllegalArgumentException("Error:Year String is empty");
		}
		else if(year==null){   
			throw new NullPointerException("Error:Year is null");
		}
		else{
		this.year = year;
		}
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		if (url==""){  
			throw new IllegalArgumentException("Error:Url String is empty");
		}
		else if(url==null){   
			throw new NullPointerException("Error:Url is null");
		}
		else{
		this.url = url;
		}
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
		counter++;
	}
	public Long getId() {
		return id;
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
	
	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof Movie)
	    {
	      final Movie other = (Movie) obj;
	      return Objects.equals(title,   other.title)
	    	  && Objects.equals(id,other.id)
	          &&  Objects.equals(year,    other.year)
	          &&  Objects.equals(url,       other.url)
	          &&  Objects.equals(ratings,    other.ratings);      
	    }
	    else
	    {
	      return false;
	    }
	  }

}
