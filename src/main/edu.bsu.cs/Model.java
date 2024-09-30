package edu.bsu.cs;
import java.util.ArrayList;
import java.util.List;
public class Model {
    private List<WikiChange> changes = new ArrayList<>();
    // Implement fetching logic here, populate the changes list
    public void fetchChanges(String articleName) {
      
    }

    public List<WikiChange> getChanges() {
        return changes;
    }

    public void addChange(WikiChange change) {
        changes.add(change);
    }

}
