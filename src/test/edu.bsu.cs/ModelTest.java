package edu.bsu.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    public void testAddAndRetrieveChange(){
        Model model = new Model();
        WikiChange change = new WikiChange("user1", "2024-01-01T00:00:00Z", "2024-01-01T00:00:00Z");
        model.addChange(change);
        assertEquals(1, model.getChanges().size());
        assertEquals("user1", model.getChanges().get(0).getusername());

    }}
