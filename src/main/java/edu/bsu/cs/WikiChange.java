package edu.bsu.cs;
public class WikiChange {private final String username;
    private final String timestamp;
    private final String utcTimestamp;

    public WikiChange(String username, String timestamp, String utcTimestamp){
        this.username = username;
        this.timestamp = timestamp;
        this.utcTimestamp = utcTimestamp;

    }
    public String getusername(){
        return username;
    }
    public String gettimestamp(){
        return timestamp;
    }
    public String getutcTimestamp(){
        return utcTimestamp;
    }//
}
