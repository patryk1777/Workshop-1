package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager01 {

    static String[][] tasks;

    public static void main(String[] args) {
       tasks = loadToArray();
        menu();
        Scanner scanner = new Scanner(System.in);

        while ((scanner.hasNextLine())) {

            String optionMenu = scanner.nextLine();
            switch (optionMenu) {
                case "add":
                    addToTasks();
                    break;
                case "list":
                    showTasks();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "exit":
                    writeToFile();
                    System.exit(0);
                default:
                    System.out.println("Wybierz poprawną opcję programu");
            }
        }
    }

    public static void menu() {
        System.out.println(ConsoleColors.BLUE + "Wybierz opcję programu :");
        System.out.print(ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("list");
        System.out.println("remove");
        System.out.println("exit");
    }


    public static String[][] loadToArray() {
        Path path = Paths.get("tasks.csv");

       if (!Files.exists(path)) {
           System.out.println("Plik nie istnieje.");
           System.exit(0);
       }

       String[][] tab = null;
        try {
            List<String> arrList = Files.readAllLines(path);
            tab = new String[arrList.size()][arrList.get(0).split(",").length];
            for (int i = 0; i < arrList.size(); i++) {
                String[] arrRows = arrList.get(i).split(",");
                for (int j = 0; j < arrRows.length; j++) {
                    tab[i][j] = arrRows[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
    public static void showTasks(){

        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + ":");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addToTasks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj opis zadania :");
        String description = scanner.nextLine();
        System.out.println("Podaj datę zadania :");
        String date = scanner.nextLine();
        System.out.println("Podaj rangę ważności zadania :");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length +1);
        tasks[tasks.length -1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = date;
        tasks[tasks.length - 1][2] = isImportant;
    }

    public static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Proszę wybrać zadanie do usunięcia");
        try {
            int index = Integer.parseInt(scanner.nextLine());

            try {
                tasks = ArrayUtils.remove(tasks, index);
                System.out.println("Sukces - usunięto zadanie nr: " + index);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Zadanie o podanym indeksie nie istnieje.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny indeks zadania.");
        }

    }

    public static void writeToFile(){
        Path path = Paths.get("tasks.csv");
        String[] row = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            row[i] = String.join(",", tasks[i]);
        }
        try {
            Files.write(path, Arrays.asList(row));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}