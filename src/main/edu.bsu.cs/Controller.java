package edu.bsu.cs;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
public class Controller {
    // It's like a blueprint for building controllers in our program
    private final Model model;
    private final View view;

    // List to store changes
    private final List<WikiChange> changes;

    // Constructor to initialize the Model and View
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.changes = new ArrayList<>();
    }//method that gets updates from Wikipedia articleName
    public void fetchRecentChanges(String articleName) {
        try {
            // Create URL for the Wikipedia API request
            String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                    + articleName + "&rvlimit=15&rvprop=user|timestamp";

            // Opening a Connection to get data
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            // Read responses from the Wikipedia page API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            System.out.println(jsonResponse.toString(2)); 

            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");

            // Check if there are revisions for the article
            String pageId = pages.keys().next(); // Get the first key (page id)
            JSONObject page = pages.getJSONObject(pageId);

            // If the revisions array is missing, it means there are no revisions for this article
            if (!page.has("revisions")) {
                System.out.println("No revisions found for the article: " + articleName);
                view.displayChanges(new ArrayList<>()); 
                return; 
            }

            JSONArray revisions = page.getJSONArray("revisions");
            for (int i = 0; i < revisions.length(); i++) {
                JSONObject revision = revisions.getJSONObject(i);
                String user = revision.getString("user");
                String timestamp = revision.getString("timestamp");
                String utcTimestamp = timestamp;

                WikiChange wikiChange = new WikiChange(user, timestamp, utcTimestamp);
                changes.add(wikiChange); // Add to the list of changes
            }

            view.displayChanges(changes); // Display the changes

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching Wikipedia changes for article: " + articleName);
        }
    }

    public void start() {
        // We will/can include initial setup logic here if we need to
        System.out.println("Controller started.");
    }public void updateView() {
        // Implement logic to update the view with the latest changes
        // For example, fetch changes from the model and send them to the view
        // this method  must exist in the Model class
        view.displayChanges(model.getChanges());
    }

}


