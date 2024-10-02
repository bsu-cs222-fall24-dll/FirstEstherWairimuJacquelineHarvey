package edu.bsu.cs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiChangeTest {

    @Test
    public void testWikiChangeGetters()
    {  WikiChange change = new WikiChange("user1", "2024-01-01T00:00:00Z", "2024-01-01T00:00:00Z");
        assertEquals("user1", change.getusername());
        assertEquals("2024-01-01T00:00:00Z", change.gettimestamp());
        assertEquals("2024-01-01T00:00:00Z", change.getutcTimestamp());
    }
}

