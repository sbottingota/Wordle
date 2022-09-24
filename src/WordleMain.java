import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class WordleMain {
    private static final String[] guessableWords = {"crane"};

    public static void main(String[] args) {
    }

    private static String[] getUsableWords() throws IOException {
        String fileContents = Files.readString(Paths.get("usableWords/guessableWords"));
        return fileContents.split(" ");
    }

    public static String getWordleWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a five letter word:");
        String word = scanner.nextLine();
        while (word.length() != 5 || !(Arrays.asList(guessableWords).contains(word))) {
            System.out.println("That was an invalid word:");
            word = scanner.nextLine();
        }

        return word;
    }
}
