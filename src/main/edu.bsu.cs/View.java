package edu.bsu.cs;

import java.util.List;
public class View {
    // Display recent revisions in a readable format
    public void displayChanges(List<WikiChange> changes){
        if(changes.isEmpty()){
            System.out.print("No wikipedia changes found ");
        } else{
            for(WikiChange change : changes){
                System.out.print(change.getutcTimestamp() + "(Live timestamp - " + change.getutcTimestamp()+ " utc" + change.getusername());

            }
        }
    }
}
