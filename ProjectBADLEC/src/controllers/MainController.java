package controllers;

import javafx.collections.FXCollections;
import javafx.stage.Stage;
import model.Movie;
import page.Login;
import page.Home;
import utils.Connect;
import utils.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Home view;
    private Movie selectedMovie;
    
    public MainController(Home view) {
        this.view = view;
        loadMovies();
    }
    
    public void loadMovies() {
    	if (view.getMovieTable() == null) {
            System.err.println("Error: TableView is not initialized");
            return;
        }
    	 
        try (Connection conn = Connect.getConnection()) {
        	// Get current user ID
            int currentUserId = UserSession.getInstance().getUserId();
            
        	System.out.println("Current user ID: " + currentUserId);
        	
            String query = "SELECT * FROM movies WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, currentUserId);
            
            ResultSet rs = pstmt.executeQuery();
            
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getInt("release_year"),
                    rs.getString("director"),
                    rs.getDouble("rating")
                );
                movie.setMovieId(rs.getInt("movie_id"));
                movies.add(movie);
            }
            
            view.getMovieTable().setItems(FXCollections.observableArrayList(movies));
        } catch (SQLException e) {
            showError("Error loading movies: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void handleAdd() {
        Movie movie = view.getInputData();
        
        if (!validateInput(movie)) {
            return;
        }
        
        try (Connection conn = Connect.getConnection()) {
        	String query = "INSERT INTO movies (title, genre, release_year, director, rating, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getGenre());
            pstmt.setInt(3, movie.getReleaseYear());
            pstmt.setString(4, movie.getDirector());
            pstmt.setDouble(5, movie.getRating());
            pstmt.setInt(6, UserSession.getInstance().getUserId());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                loadMovies();
                view.clearInputs();
            }
        } catch (SQLException e) {
            showError("Error adding movie: " + e.getMessage());
        }
    }
    
    public void handleUpdate() {
        if (selectedMovie == null) {
            showError("Please select a movie to update");
            return;
        }
        
        Movie updatedMovie = view.getInputData();
        if (!validateInput(updatedMovie)) {
            return;
        }
        
        try (Connection conn = Connect.getConnection()) {
            String query = "UPDATE movies SET title=?, genre=?, release_year=?, director=?, rating=? WHERE movie_id=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, updatedMovie.getTitle());
            pstmt.setString(2, updatedMovie.getGenre());
            pstmt.setInt(3, updatedMovie.getReleaseYear());
            pstmt.setString(4, updatedMovie.getDirector());
            pstmt.setDouble(5, updatedMovie.getRating());
            pstmt.setInt(6, selectedMovie.getMovieId());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                loadMovies();
                view.clearInputs();
                selectedMovie = null;
            }
        } catch (SQLException e) {
            showError("Error updating movie: " + e.getMessage());
        }
    }
    
    public void handleDelete() {
        if (selectedMovie == null) {
            showError("Please select a movie to delete");
            return;
        }
        
        try (Connection conn = Connect.getConnection()) {
            String query = "DELETE FROM movies WHERE movie_id=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, selectedMovie.getMovieId());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                loadMovies();
                view.clearInputs();
                selectedMovie = null;
            }
        } catch (SQLException e) {
            showError("Error deleting movie: " + e.getMessage());
        }
    }
    
    public void handleMovieSelection(Movie movie) {
        selectedMovie = movie;
        view.setInputData(movie);
    }
    
    public void handleClear() {
        view.clearInputs();
        selectedMovie = null;
        view.getMovieTable().getSelectionModel().clearSelection();
    }
    
    public void handleLogout() {
    	UserSession.getInstance().clearSession();
        Stage stage = (Stage) view.getScene().getWindow();
        Login login = new Login();
        stage.setScene(login.getScene());
        stage.setResizable(false);
        stage.centerOnScreen();
    }
    
    private boolean validateInput(Movie movie) {
    	if (movie.getTitle().trim().isEmpty() || 
    	        movie.getGenre().trim().isEmpty() || 
    	        movie.getDirector().trim().isEmpty()) {
    	        showError("Please fill in all fields");
    	        return false;
        }
        if (movie.getRating() < 0 || movie.getRating() > 10) {
            showError("Rating must be between 0 and 10");
            return false;
        }
        return true;
    }
    
    private void showError(String message) {
        System.err.println(message);
    }
}