package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

	static Long counter = 0l;
	private Long id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String occupation;
	
	 public List<Rating> ratings = new ArrayList<>();
	 
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
	 this.id = counter++;
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
	 public String toString()
	 {
	 return new ToJsonString(getClass(), this).toString();
	 }

	 @Override
	 public int hashCode()
	 {
	 return Objects.hashCode(this.lastName, this.firstName, this.gender, this.age);
	 }

	 @Override
	 public boolean equals(final Object obj)
	 {
	 if (obj instanceof User)
	 {
	 final User other = (User) obj;
	 return Objects.equal(firstName, other.firstName)
	 && Objects.equal(lastName, other.lastName)
	 && Objects.equal(gender, other.gender)
	 && Objects.equal(age, other.age)
	 && Objects.equal(occupation, other.occupation)
	 && Objects.equal(ratings, other.ratings);
	 }
	 else
	 {
	 return false;
	 }
}
