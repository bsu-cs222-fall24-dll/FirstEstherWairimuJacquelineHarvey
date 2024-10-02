package edu.bsu.cs;

import javafx.application.Application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Application.launch(GUIMain.class, args);
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        controller.start();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the Wikipedia article.");
        System.out.println("Press ENTER to process: ");
        String articleName = scanner.nextLine().trim();

        if (articleName.isEmpty()) {
            System.err.println("Error: No article name entered. Exiting program.");
            System.exit(1);
        }

        try {
            controller.fetchRecentChanges(articleName);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1); // Exit the program with an error code
        }
    }
}


