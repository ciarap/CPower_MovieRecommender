package models;

public class Fixtures
{
  public static User[] users =
  {
    new User ("marge", "simpson", 40,"F","HouseWife"),
    new User ("lisa",  "simpson",8, "F",   "student"),
    new User ("bart",  "simpson", 10,"M",   "student"),
    new User ("maggie","simpson",2, "F", "baby")
  };

  public static Rating[] ratings =
  {
    new Rating (1l,  1l, 1),
    new Rating (2l,  1l,    1),
    new Rating (2l,   2l,   2),
    new Rating (3l,  3l,  4),
    new Rating (4l, 1l, -4)
  };

  public static Movie[]movies =
  {
    new Movie("movie1","Jan","www.movie1.com"),
    new Movie("movie2","Jan","www.movie2.com"),  
    new Movie("movie3","Jan","www.movie3.com"),
    new Movie("movie4","Jan","www.movie4.com")       
  };
}