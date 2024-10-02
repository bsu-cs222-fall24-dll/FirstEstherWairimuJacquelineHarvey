package edu.bsu.cs;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;


/* This class tests if the Controller class correctly fetches and stores changes.
 *
 *
 * */
public class ControllerTest {
    @Test
    public void testFetchAndDisplayChanges(){
        Model model = new Model();
        View view = new View(){
            @Override
            public void displayChanges(List<WikiChange> changes){
                //override to capture output for testing
            }
        };
        Controller controller = new Controller(model, view);
        String simulatedArticleName = "TestArticle";
        controller.fetchRecentChanges(simulatedArticleName);
        assertTrue(model.getChanges().size() >0);
    }
}


