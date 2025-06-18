package model;

public class Movie {
	private int movieId;
    private String title;
    private String genre;
    private int releaseYear;
    private String director;
    private double rating;
    private int userId; 
    
    public Movie(String title, String genre, int releaseYear, String director, double rating) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.rating = rating;
    }
    
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

}
