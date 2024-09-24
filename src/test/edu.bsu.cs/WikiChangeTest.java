package edu.bsu.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiChangeTest {
    //testcase with instances of the wikichange class that verifies the behavior of the wikichange class
    @Test
    public void testWikiChangeGetters()
    {
        //user1 ia associated with the change
        //timestamp and utctimestamp of the change
        WikiChange change = new WikiChange("user1", "2024-01-01T00:00:00Z", "2024-01-01T00:00:00Z");
        assertEquals("user1", change.getusername());
        assertEquals("2024-01-01T00:00:00Z", change.gettimestamp());
        assertEquals("2024-01-01T00:00:00Z", change.getutcTimestamp());
    }
}
