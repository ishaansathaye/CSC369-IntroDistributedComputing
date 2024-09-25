package lab1;

import java.io.File;
import java.io.IOException;

public class lab1 {

    // createStoreFile: create a file named store.txt
    public static void createStoreFile() {
        // Create a file named store.txt
        File file = new File("./labs/lab1/store.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create a file named store.txt
        createStoreFile();
    }
    
}
