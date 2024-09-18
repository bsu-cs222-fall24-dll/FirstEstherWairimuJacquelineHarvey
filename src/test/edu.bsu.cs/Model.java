package edu.bsu.cs;
import java.util.ArrayList;
import java.util.List;
public class Model {
  private List<WikiChange> changes = new ArrayList<>();

    public void fetchChanges(String articleName) {
        // Implement fetching logic here, populate the changes list
    }

    public List<WikiChange> getChanges() {
        return changes;
    }

    public void addChange(WikiChange change) {
        changes.add(change);
    }
}
