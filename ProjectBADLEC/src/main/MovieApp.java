package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Movie;
import page.Login;
import utils.Connect;

import java.sql.*;
import java.util.ArrayList;

public class MovieApp extends Application{
	
	@Override
    public void start(Stage primaryStage) {
        Login login = new Login();
        primaryStage.setTitle("Movie Management System");
        primaryStage.setScene(login.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
	
}
