package edu.bsu.cs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class GUIMain extends Application { 
    // Declares the `GUIMain` class that extends `Application`, making it a JavaFX application.
    private Controller controller;
    private TextField articleInput;
    private TextArea resultArea;
    private Button fetchButton;

    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        View view = new View();
        controller = new Controller(model, view); // Initialize Controller with Model and View

        // GUI components
        articleInput = new TextField();
        articleInput.setPromptText("Enter Wikipedia article name you want to search here...");

        resultArea = new TextArea();
        resultArea.setEditable(false);

        fetchButton = new Button("Click me to Fetch Recent Changes");
        fetchButton.setOnAction(event -> onFetchButtonClicked());  // Makes the TextArea non-editable, as users should only view results and not modify them
        
        VBox layout = new VBox(10, articleInput, fetchButton, resultArea);
        Scene scene = new Scene(layout, 500, 300);

        primaryStage.setTitle("Wikipedia Recent Changes");
        primaryStage.setScene(scene);
        primaryStage.show();

        layout.requestFocus();
    }
    private void onFetchButtonClicked() {
        String articleName = articleInput.getText();
        if (articleName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter an article name to process the request.");
            return;
        }
        // Disable interaction while fetching data
        setUIEnabled(false);        
        // Starts a new background thread to fetch data without freezing the UI.
        new Thread(() -> {
            try {
                controller.fetchRecentChanges(articleName);
                List<WikiChange> changes = controller.getChanges();
                boolean isRedirected = false; // Logic to detect redirection can be added here

                // Update the GUI on the JavaFX Application thread
                javafx.application.Platform.runLater(() -> {
                    displayResults(changes, isRedirected);
                    setUIEnabled(true); // Re-enable the UI
                });
                // Catches any exceptions that occur during data fetching.
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> { // Schedules code to run on the JavaFX Application thread to show an error alert.
                    showAlert(Alert.AlertType.ERROR, "Network Error", "Failed to fetch recent changes. Please check your network connection.");
                    setUIEnabled(true);
                });
            }
        }).start();
    }
    private void displayResults(List<WikiChange> changes, boolean isRedirected) {
        resultArea.clear();
        if (isRedirected) {
            resultArea.appendText("You were redirected to a different article.\n\n");
        }
        if (changes.isEmpty()) {
            resultArea.appendText("No changes were found for this article.\n");
        } else {
            for (WikiChange change : changes) {
                resultArea.appendText(change.getusername() + " at " + change.getutcTimestamp() + "\n");
            }}
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.initModality(Modality.APPLICATION_MODAL); // Sets the alert to be modal, meaning it blocks interaction with other windows until dismissed
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void setUIEnabled(boolean enabled) {
        fetchButton.setDisable(!enabled);
        articleInput.setDisable(!enabled);
    }
// The main method to launch the JavaFX application. This starts the GUI application
    public static void main(String[] args) {
        launch(args);
    }
}
