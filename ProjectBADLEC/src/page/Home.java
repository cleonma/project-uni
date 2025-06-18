package page;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import model.Movie;
import utils.Styles;
import utils.UserSession;
import controllers.MainController;

public class Home {
	private Scene scene;
    private TableView<Movie> movieTable;
    private TextField titleField, genreField, directorField;
    private Spinner<Integer> yearSpinner;
    private Spinner<Double> ratingSpinner;
    private MainController controller;

    public Home() {
    	createMovieTable();
        controller = new MainController(this);
        createView();
    }

    private void createView() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");
        root.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + UserSession.getInstance().getUsername());
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        
        VBox header = createHeader();
        root.setTop(header);

        SplitPane mainContent = new SplitPane();
        
        VBox tableContainer = createTableContainer();
        
        VBox inputForm = createInputForm();
        
        mainContent.getItems().addAll(tableContainer, inputForm);
        mainContent.setDividerPositions(0.7);
        root.setCenter(mainContent);

        HBox buttonBox = createButtonBox();
        root.setBottom(buttonBox);

        scene = new Scene(root, 1000, 600);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setStyle("-fx-background-color: #1976D2; -fx-padding: 15 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");
        
        Label welcomeLabel = new Label("Welcome, " + UserSession.getInstance().getUsername());
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label subtitle = new Label("Movie Management System");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        
        header.getChildren().addAll(welcomeLabel, subtitle);
        return header;
    }

    private VBox createTableContainer() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: white;");

        Label tableTitle = new Label("Movie List");
        tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #424242;");

        createMovieTable();
        container.getChildren().addAll(tableTitle, movieTable);
        return container;
    }

    private void createMovieTable() {
        movieTable = new TableView<>();
        movieTable.setStyle("-fx-font-size: 13px;");
        movieTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
//        movieTable.setPrefHeight(400);
        VBox.setVgrow(movieTable, Priority.ALWAYS);

        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(150);

        TableColumn<Movie, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreCol.setPrefWidth(100);

        TableColumn<Movie, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        yearCol.setPrefWidth(70);

        TableColumn<Movie, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
        directorCol.setPrefWidth(150);

        TableColumn<Movie, Double> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.setPrefWidth(70);

        movieTable.getColumns().addAll(titleCol, genreCol, yearCol, directorCol, ratingCol);
    
        movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                controller.handleMovieSelection(newSelection);
            }
        });
    }

    private VBox createInputForm() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f5f5f5;");
        form.setMinWidth(300);

        Label formTitle = new Label("Movie Details");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #424242;");

        titleField = createStyledTextField("Movie Title");
        genreField = createStyledTextField("Genre");
        directorField = createStyledTextField("Director");
        
        yearSpinner = new Spinner<>(1900, 2100, 2024);
        yearSpinner.setEditable(true);
        yearSpinner.setPrefWidth(Double.MAX_VALUE);
        yearSpinner.setStyle("-fx-font-size: 13px;");
        
        ratingSpinner = new Spinner<>(0.0, 10.0, 5.0, 0.1);
        ratingSpinner.setEditable(true);
        ratingSpinner.setPrefWidth(Double.MAX_VALUE);
        ratingSpinner.setStyle("-fx-font-size: 13px;");
        
        form.getChildren().addAll(
            new Label("Title:"), titleField,
            new Label("Genre:"), genreField,
            new Label("Year:"), yearSpinner,
            new Label("Director:"), directorField,
            new Label("Rating:"), ratingSpinner
        );

        return form;
    }
    
    private TextField createStyledTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-font-size: 13px;");
        return field;
    }

    private VBox createFieldContainer(String labelText, Control field) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #616161;");
        container.getChildren().addAll(label, field);
        return container;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.setStyle("-fx-background-color: white;");

        Button addButton = createStyledButton("Add", Styles.BUTTON_STYLE);
        Button updateButton = createStyledButton("Update", Styles.BUTTON_STYLE);
        Button deleteButton = createStyledButton("Delete", Styles.DELETE_BUTTON_STYLE);
        Button clearButton = createStyledButton("Clear Text", Styles.BACK_BUTTON_STYLE);
        Button logoutButton = createStyledButton("Logout", Styles.LOGOUT_BUTTON_STYLE);

        addButton.setOnAction(e -> controller.handleAdd());
        updateButton.setOnAction(e -> controller.handleUpdate());
        deleteButton.setOnAction(e -> controller.handleDelete());
        clearButton.setOnAction(e -> controller.handleClear());
        logoutButton.setOnAction(e -> controller.handleLogout());

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton, logoutButton);
        return buttonBox;
    }
    
    private Button createStyledButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        
        button.setOnMouseEntered(e -> {
            String currentStyle = button.getStyle();
            button.setStyle(currentStyle + "-fx-opacity: 0.9;");
        });
        
        button.setOnMouseExited(e -> {
            String currentStyle = button.getStyle();
            button.setStyle(currentStyle.replace("-fx-opacity: 0.9;", ""));
        });
        
        return button;
    }

    public Scene getScene() { return scene; }
    public TableView<Movie> getMovieTable() { return movieTable; }
    
    public Movie getInputData() {
        return new Movie(
            titleField.getText(),
            genreField.getText(),
            yearSpinner.getValue(),
            directorField.getText(),
            ratingSpinner.getValue()
        );
    }

    public void setInputData(Movie movie) {
        titleField.setText(movie.getTitle());
        genreField.setText(movie.getGenre());
        yearSpinner.getValueFactory().setValue(movie.getReleaseYear());
        directorField.setText(movie.getDirector());
        ratingSpinner.getValueFactory().setValue(movie.getRating());
    }

    public void clearInputs() {
        titleField.clear();
        genreField.clear();
        yearSpinner.getValueFactory().setValue(2024);
        directorField.clear();
        ratingSpinner.getValueFactory().setValue(5.0);
    }
}

