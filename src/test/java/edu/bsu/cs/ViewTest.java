package edu.bsu.cs;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ViewTest {
    @Test
    public void testDisplayChanges_NoChanges(){
        View view = new View();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        view.displayChanges(Collections.emptyList());

        assertTrue(outContent.toString().contains("No changes found for the page")); }

    @Test
    public void testDisplayChanges_WithChanges(){
        View view = new View();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Model model = new Model();
        model.addChange(new WikiChange("user1", "2024-01-01T00:00:00Z", "2024-01-01T00:00:00Z"));
        view.displayChanges(model.getChanges());

        String expectedOutput = "2024-01-01T00:00:00Z 2024-01-01T00:00:00Z User1";
        assertTrue(outContent.toString().contains(expectedOutput));

    }
}

