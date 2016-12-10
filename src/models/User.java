package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

	public static Long counter = 0l;
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String occupation;
	
	 private List<Rating> ratings = new ArrayList<>();
	 
	public List<Rating> getRatings() {
		return ratings;
	}
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	public User(Long id,String firstName, String lastName, int age, String gender, String occupation) {
		this.id= id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}
	 public User(String firstName, String lastName,int age, String gender,  String occupation)
	 {
	 counter++;
	 this.id = counter;
	 this.firstName = firstName;
	 this.lastName = lastName;
	 this.gender = gender;
	 this.age = age;
	 this.occupation = occupation;
	 }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", gender="
				+ gender + ", occupation=" + occupation + "]";
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String secondName) {
		this.lastName = secondName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	 @Override
	 public boolean equals(final Object obj)
	 {
	 if (obj instanceof User)
	 {
	 final User other = (User) obj;
	 return Objects.equals(firstName, other.firstName)
	 && Objects.equals(lastName, other.lastName)
	 && Objects.equals(gender, other.gender)
	 && Objects.equals(age, other.age)
	 && Objects.equals(occupation, other.occupation)
	 && Objects.equals(ratings, other.ratings);
	 }
	 else
	 {
	 return false;
	 }
}
	public void addRating(Rating newRating) {
		ratings.add(newRating);
		
	}
	
}
