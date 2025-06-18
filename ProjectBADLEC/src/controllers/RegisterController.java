package controllers;

import javafx.stage.Stage;
import model.User;
import page.Register;
import page.Login;
import utils.Connect;
import java.sql.*;

public class RegisterController {
    private Register view;
    
    public RegisterController(Register view) {
        this.view = view;
    }
    
    public void handleRegister() {
        String username = view.getUsername();
        String password = view.getPassword();
        String email = view.getEmail();
        
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            view.setMessage("Please fill in all fields");
            return;
        }
        
        try (Connection conn = Connect.getConnection()) {
            String checkQuery = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            
            if (checkStmt.executeQuery().next()) {
                view.setMessage("Username already exists");
                return;
            }
            
            String insertQuery = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, email);
            
            int result = insertStmt.executeUpdate();
            if (result > 0) {
                showLogin();
            } else {
                view.setMessage("Registration failed");
            }
        } catch (SQLException e) {
            view.setMessage("Database error: " + e.getMessage());
        }
    }
    
    public void showLogin() {
        Stage stage = (Stage) view.getScene().getWindow();
        Login login = new Login();
        stage.setScene(login.getScene());
    }
}