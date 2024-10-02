package edu.bsu.cs;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<WikiChange> changes = new ArrayList<>();

    public List<WikiChange> getChanges() {
        return changes;
    }
    public void addChange(WikiChange change) {
        changes.add(change);
    }

}

