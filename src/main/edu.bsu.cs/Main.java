package edu.bsu.cs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //CREATE MVC objects/instances
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.start();

        //prompt user for an articla name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of a wikipedia article. Press ENTER to process: ");
        String articleName = scanner.nextLine();
        scanner.close();

        //fetch recent changes and displays them
        controller.fetchRecentChanges(articleName);
        controller.updateView();
    }

}
