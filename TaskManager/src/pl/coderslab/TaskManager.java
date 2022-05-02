package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;
    static final String[] arr = {"add", "remove", "list", "exit"};
    static final String FILENAME = "tasks.csv";

    public static void addToTasks() {
        // dodawanie do listy notatek
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scan.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scan.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scan.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;


    }

    public static void showTasks() {
        // wyświetlanie notatek
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] loadToArray(String fileName) {

        // odczyt z pliku, zwiększanie rozmiaru tablicy i zapis do tablicy

        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("File not exists");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> list = Files.readAllLines(path);
            tab = new String[list.size()][list.get(0).split(",").length];
            for (int i = 0; i < list.size(); i++) {
                String[] arrRows = list.get(i).split(",");
                for (int j = 0; j < arrRows.length; j++) {
                    tab[i][j] = arrRows[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void writeToFile(String fileName, String[][] tab) {
        Path path = Paths.get(fileName);
        String[] row = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            row[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(path, Arrays.asList(row));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String element : arr) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        tasks = loadToArray(FILENAME);
        menu();

        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            String option = scan.nextLine();
            switch (option) {
                case "add":
                    addToTasks();
                    break;
                case "remove":
                    // potrrzebna wczytana tablica
                    System.out.println("Please select number to remove.");
                    try {
                        int i = Integer.parseInt(scan.nextLine());
                        try {
                            tasks = ArrayUtils.remove(tasks, i);
                            System.out.println("Successfully deleted");
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Element not exist");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("You entered an invalid index. Enter the number");
                    }

                    break;
                case "list":
                    showTasks();
                    break;
                case "exit":
                    writeToFile(FILENAME, tasks);
                    System.exit(0);
                default:
                    System.out.println("Please select a correct option.");
            }
            menu();
        }
    }
}
