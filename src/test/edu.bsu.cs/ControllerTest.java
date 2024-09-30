package edu.bsu.cs;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ControllerTest {
    //Test case to verify that the Controller fetches recent changes and updates the model.
    @Test
    public void testFetchAndDisplayChanges(){
        Model model = new Model();
        View view = new View(){
            @Override
            public void displayChanges(List<WikiChange> changes){
            }
        };
        Controller controller = new Controller(model, view);
        String simulatedArticleName = "TestArtcile";
        controller.fetchRecentChanges(simulatedArticleName);
        assertTrue(model.getChanges().size()> 0);
    }
}
