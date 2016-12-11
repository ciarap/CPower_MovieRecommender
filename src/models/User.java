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
	
	public User(Long id,String firstName, String lastName, int age, String gender, String occupation) {
		this.id= id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		counter++;   //increment counter
	}
	 public User(String firstName, String lastName,int age, String gender,  String occupation)
	 {   //if no id as parameter(user not read from file) then set incremented counter as id
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
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if (firstName==""){  
			throw new IllegalArgumentException("Error:Name String is empty");
		}
		else if(firstName==null){   
			throw new NullPointerException("Error:Name is null");
		}
		else{
		this.firstName = firstName;
		}
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		if (lastName==""){  
			throw new IllegalArgumentException("Error:Name String is empty");
		}
		else if(lastName==null){   // if weight is above 0 then set it
			throw new NullPointerException("Error:Name is null");
		}
		else{
		this.lastName = lastName;
		}
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		if (age<=0){    //cant be negative age
			throw new IllegalArgumentException("Error:Age  is negative");
		}
		else{
		this.age = age;
		}
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		if (gender==""){  
			throw new IllegalArgumentException("Error:Gender String is empty");
		}
		else if(gender==null){   // if argument is null throw exception
			throw new NullPointerException("Error:Gender is null");
		}
		else{
		this.gender = gender;
		}
	}
	
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		if (occupation==""){  
			throw new IllegalArgumentException("Error:Occupation String is empty");
		}
		else if(occupation==null){   // if argument is null throw exception
			throw new NullPointerException("Error:Occupation is null");
		}
		else{
		this.occupation = occupation;
		}
	}

	 @Override
	 public boolean equals(final Object obj)
	 {
	 if (obj instanceof User)
	 {
	 final User other = (User) obj;
	 return Objects.equals(firstName, other.firstName)
	 && Objects.equals(id, other.id)
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
	
	@Override
	public String toString() {
		return "User[id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", gender="
				+ gender + ", occupation=" + occupation + "]";
	}
	
}
