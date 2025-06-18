package page;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import utils.Styles;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import controllers.LoginController;

public class Login {
	private Scene scene;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private LoginController controller;

    public Login() {
    	initialize();
        controller = new LoginController(this);
        createView();
    }
    
    private void initialize() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        messageLabel = new Label();
    }

    private void createView() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: white;");
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Movie Management System");
//        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setStyle(Styles.TITLE_STYLE);
        
        VBox formContainer = new VBox(15);
        formContainer.setMaxWidth(400);
        formContainer.setStyle(Styles.FORM_CONTAINER_STYLE);

        GridPane usernamePane = createFieldPane("Username:", usernameField);
        
        GridPane passwordPane = createFieldPane("Password:", passwordField);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button loginButton = createStyledButton("Login", Styles.BUTTON_STYLE);
        Button registerButton = createStyledButton("Register", Styles.REGISTER_BUTTON_STYLE);

        loginButton.setOnAction(e -> controller.handleLogin());
        registerButton.setOnAction(e -> controller.showRegister());
        
        buttonBox.getChildren().addAll(loginButton, registerButton);

        messageLabel = new Label();
        messageLabel.setStyle(Styles.ERROR_STYLE);

        formContainer.getChildren().addAll(
            usernamePane,
            passwordPane,
            buttonBox,
            messageLabel
        );

        root.getChildren().addAll(titleLabel, formContainer);
        scene = new Scene(root, 500, 400);
        
    }
    
    private GridPane createFieldPane(String labelText, TextField field) {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER_LEFT);
        
        Label label = new Label(labelText);
        label.setStyle(Styles.FIELD_LABEL_STYLE);
        label.setPrefWidth(80);
        
        field.setPrefHeight(30);
        field.setPrefWidth(250);
        field.setPromptText("Enter " + labelText.toLowerCase().replace(":", ""));
        
        pane.addRow(0, label, field);
        return pane;
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

    public Scene getScene() {
        return scene;
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return passwordField.getText(); }
    public void setMessage(String message) { messageLabel.setText(message); }
}