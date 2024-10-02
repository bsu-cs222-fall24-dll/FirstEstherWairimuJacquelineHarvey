package edu.bsu.cs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class GUIMain extends Application {
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
        fetchButton.setOnAction(event -> onFetchButtonClicked());

        // Layout setup
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

        setUIEnabled(false);

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

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
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
                resultArea.appendText(change.getutcTimestamp() + "  " + change.getusername() + "\n");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setUIEnabled(boolean enabled) {
        fetchButton.setDisable(!enabled);
        articleInput.setDisable(!enabled);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


