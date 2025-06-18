package controllers;

import javafx.stage.Stage;
import model.User;
import page.Login;
import page.Register;
import page.Home;
import utils.Connect;
import utils.UserSession;

import java.sql.*;

public class LoginController {
    private Login view;
    
    public LoginController(Login view) {
        this.view = view;
    }
    
    public void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();
        
        if (username.isEmpty() || password.isEmpty()) {
            view.setMessage("Please fill in all fields");
            return;
        }
        
        try (Connection conn = Connect.getConnection()) {
        	String query = "SELECT user_id, username FROM users WHERE username = ? AND password = ?";
        	PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password); 
            
            System.out.println("Executing login query for user: " + username);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	System.out.println("User found with ID: " + rs.getInt("user_id"));
                 
            	UserSession.getInstance().setUser(
                        rs.getInt("user_id"),
                        rs.getString("username")
                    );
                showMainView();
            } else {
                view.setMessage("Invalid username or password");
            }
        } catch (SQLException e) {
            view.setMessage("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void showRegister() {
        Stage stage = (Stage) view.getScene().getWindow();
        Register register = new Register();
        stage.setScene(register.getScene());
    }
    
    private void showMainView() {
        Stage stage = (Stage) view.getScene().getWindow();
        Home main = new Home();
        stage.setScene(main.getScene());
        stage.setResizable(true);
        stage.centerOnScreen();
    }
}