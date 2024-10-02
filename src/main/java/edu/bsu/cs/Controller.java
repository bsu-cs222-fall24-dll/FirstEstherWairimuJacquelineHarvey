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
    private final List<WikiChange> changes;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.changes = new ArrayList<>();
    }

    public void fetchRecentChanges(String articleName) {
        try {
            String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                    + articleName + "&rvlimit=15&rvprop=user|timestamp";

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");
            String pageId = pages.keys().next();
            JSONObject page = pages.getJSONObject(pageId);

            // Check if the page exists
            if (pageId.equals("-1")) {
                throw new IllegalArgumentException("No Wikipedia page found for the article: " + articleName);
            }

            if (!page.has("revisions")) {
                System.out.println("No revisions found for the article: " + articleName);
                view.displayChanges(new ArrayList<>());
                return;
            }

            // Parse revisions
            JSONArray revisions = page.getJSONArray("revisions");
            for (int i = 0; i < revisions.length(); i++) {
                JSONObject revision = revisions.getJSONObject(i);
                String user = revision.getString("user");
                String timestamp = revision.getString("timestamp");
                String utcTimestamp = timestamp;

                WikiChange wikiChange = new WikiChange(user, timestamp, utcTimestamp);
                changes.add(wikiChange);
            }

            view.displayChanges(changes);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Network error occurred while fetching Wikipedia changes for article: " + articleName);
        }
    }
    public List<WikiChange> getChanges() {
        return changes;
    }

    public void start() {
        System.out.println("Controller started.");
    }

    public void updateView() {
        view.displayChanges(model.getChanges());
    }

    public boolean isRedirected() {
        return true;
    }

    public String getRedirectedArticleName() {
        return "Redirected Article Name";
    }
}
