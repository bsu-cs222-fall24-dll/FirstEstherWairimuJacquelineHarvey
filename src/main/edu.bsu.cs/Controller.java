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

    private final Model model;
    private final View view;

    // List to store changes
    private List<WikiChange> changes;

    // Constructor to initialize the edu.bsu.cs.Model and edu.bsu.cs.View
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.changes = new ArrayList<>();
    }
    public void fetchRecentChanges(String articleName){
        try{
            //create URL for the wikipidea API request
            String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                    + articleName + "&rvlimit=15&rvprop=user|timestamp"; // Ensure 'timestamp' is correctly spelled to avoid crushing
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            //read resnposes from the wikipedia page API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }in.close();

            //parse JSON response
            JSONObject jsonResponse = new JSONObject(content.toString());
            System.out.println(jsonResponse.toString(2)); // Print the response
            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");
            JSONArray revisions = pages.keys().hasNext() ? pages.getJSONObject(pages.keys().next()).getJSONArray("revisions"):new JSONArray();

            for(int i = 0; i<revisions.length(); i++){
                JSONObject revision = revisions.getJSONObject(i);
                String user = revision.getString("user");
                String timestamp = revision.getString("timestamp");
                String utcTimestamp = timestamp;

                WikiChange wikiChange = new WikiChange(user, timestamp, utcTimestamp);
                changes.add(wikiChange); // Add to the list of change
            }
            view.displayChanges(changes);

        }catch(Exception e){
            e.printStackTrace();
            System.out.print("Error during fetching wikipedia changes ");

        }
    }
    public void start() {
        // We will/can include initial setup logic here if we need to
        System.out.println("edu.bsu.cs.Controller started.");
    }public void updateView() {
        // Implement logic to update the view with the latest changes
        // For example, fetch changes from the model and send them to the view
        // this method  must exist in the edu.bsu.cs.Model class
        view.displayChanges(model.getChanges());
    }

}


