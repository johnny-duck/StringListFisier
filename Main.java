import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static File file = new File("C:/scanner/listOfStrings.txt");
    static PrintWriter out = null;
    static BufferedWriter bufferedWriter;
    static BufferedReader reader;

    public static void main(String[] args) {
        //if (!file.isFile()) return;
        Boolean quit = false;
        Integer option;
        printOptions();
        while (!quit) {
            System.out.println("Choose your action (0. to show menu options): ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0:
                    printOptions();
                    break;
                case 1:
                    addString();
                    break;
                case 2:
                    removeString();
                    break;
                case 3:
                    printList();
                    break;
                case 9:
                    quit = true;
                    break;
            }
        }
    }

    public static void printOptions() {
        System.out.println("I want: "
                + "\n\t1. to add a string;"
                + "\n\t2. to remove a string;"
                + "\n\t3. to print all strings;"
                + "\n\t9. to move it move it (EXIT);"
        );
    }

    public static void addString() {
        System.out.println("Enter new string: ");
        String theStringToAdd = scanner.nextLine();
        try {
            bufferedWriter = Files.newBufferedWriter(file.toPath(),
                    Charset.forName("UTF8"),
                    StandardOpenOption.APPEND);
            out = new PrintWriter(bufferedWriter, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(theStringToAdd);
        out.close();
        System.out.println(theStringToAdd + " added successfully!");
    }

    public static void removeString() {
        System.out.println("Enter the string you want to be removed: ");
        String toBeRemoved = scanner.nextLine();
        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        try {
            out = new PrintWriter(new FileWriter(tempFile), true);
            reader = Files.newBufferedReader(file.toPath());
            String line = reader.readLine();

            while (line != null) {
                if (!line.trim().equalsIgnoreCase(toBeRemoved)) {
                    out.println(line);
                } else System.out.println(toBeRemoved + " has been removed successfully!");
                line = reader.readLine();
            }
            reader.close();
            out.close();
            if (!file.delete()) {
                System.out.println("Could not delete file!");
                return;
            }
            if (!tempFile.renameTo(file)) {
                System.out.println("Could not rename temp file!");
            }
        } catch (IOException e) {
            System.out.println("method prinList");
        }
    }

    public static void printList() {
        try {
            reader = Files.newBufferedReader(file.toPath());
            String line = reader.readLine();
            System.out.println("List elements are: ");
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("method prinList - file not found!");
        }
    }
}
