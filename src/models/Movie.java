package models;

public class Movie {
	public static Long counter= 0l;
	private Long id;
     private String title;
     private String year;
     private String url;
     
     
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
		return "Movie [title=" + title + ", year=" + year + ", url=" + url + "]";
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


	
}
