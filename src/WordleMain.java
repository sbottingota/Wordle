import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class WordleMain {

    public static void main(String[] args) throws IOException {
    }

    //reads and returns guessableWords as an array
    private static String[] getUsableWords() throws IOException {
        String fileContents = Files.readString(Paths.get("usableWords/guessableWords"));
        return fileContents.split(" ");
    }

    //reading a word from System.in and validating it
    public static String getWordleWord() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a five letter word:");
        String word = scanner.nextLine();
        word = word.toUpperCase();
        while (word.length() != 5 || !(Arrays.asList(getUsableWords()).contains(word))) {
            System.out.println("That was an invalid word:");
            word = scanner.nextLine();
            word = word.toUpperCase();
        }

        return word;
    }
}
