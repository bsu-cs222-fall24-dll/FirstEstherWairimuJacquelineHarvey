package edu.bsu.cs;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

public class View {

    private final TextField articleInput;
    private final Button fetchButton;
    private final TextArea storeResults;
    private final VBox layout;

    public View() {
        // Initialize the UI components for the GUI
        articleInput = new TextField();
        articleInput.setPromptText("Enter the name of the article you want searched:");

        fetchButton = new Button("Fetch changes");

        storeResults = new TextArea();
        storeResults.setEditable(false);

        layout = new VBox(10, articleInput, fetchButton, storeResults);
    }

     public VBox getLayout() {
        return layout;
    }

    public Button getFetchButton() {
        return fetchButton;
    }

    // Getter for articleInput
    public TextField getArticleInput() {
        return articleInput;
    }

    // Show an alert in the GUI
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear the results area in the GUI
    public void clearResults() {
        storeResults.clear();
    }

    public void displayChanges(List<WikiChange> changes) {
        if (changes.isEmpty()) {
            System.out.print("No Wikipedia changes found");
        } else {
            for (WikiChange change : changes) {
                System.out.print(change.getutcTimestamp() + "  " + change.getusername() + "\n");
            }
        }
    }
}

