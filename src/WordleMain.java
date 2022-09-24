import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public final class WordleMain {

    public static void main(String[] args) {}

    //returns a random word from possibleWords
    public static String getWordle() throws IOException {
        String fileContents = Files.readString(Paths.get("./usableWords/possibleWordles"));
        String[] wordlesArray = fileContents.split(" ");

        Random random = new Random();
        String wordle = wordlesArray[random.nextInt(wordlesArray.length)];

        return wordle;
    }

    //reads and returns guessableWords as an array
    private static String[] getUsableWords() throws IOException {
        String fileContents = Files.readString(Paths.get("./usableWords/guessableWords"));
        return fileContents.split(" ");
    }

    //reads a word from System.in and validating it
    public static String getGuess() throws IOException {
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

    //ansi colors for setKeyboardLetterColor
    private static final String GREEN = "\u001B[42m";
    private static final String YELLOW = "\u001B[43m";
    private static final String WHITE = "\u001B[47m";
    private static final String RESET = "\u001B[0m";

    private static final char[][] keys = {"QWERTYUIOP".toCharArray(), "ASDFGHJKL".toCharArray(), "ZXCVBNM".toCharArray()};
    private static String[][] keyColors = {{RESET, RESET, RESET, RESET, RESET, RESET, RESET, RESET, RESET, RESET},
            {RESET, RESET, RESET, RESET, RESET, RESET, RESET, RESET, RESET},
            {RESET, RESET, RESET, RESET, RESET, RESET, RESET}};

    private static Character[] charArrayToCharObjArray(char[] chars) {
        Character[] output = new Character[chars.length];

        for (int character = 0; character < chars.length; character++) {
            output[character] = Character.valueOf(chars[character]);
        }

        return output;
    }

    //sets the ansiColor of a particular key on the keyboard (not guessed, not in the word, not in the right place, in the right place)
    public static void setKeyboardLetterColor(char letter, String ansiColor) {

        for (int row = 0; row < keys.length; row++) {
            if (Arrays.asList(charArrayToCharObjArray(keys[row])).contains(Character.valueOf(letter))) {
                keyColors[row][new String(keys[row]).indexOf(letter)] = ansiColor;
                return;
            }
        }
    }

    public static void printKeyboard() {
        String keyboard = "";

        for (int row = 0; row < keys.length; row++) {
            for (int key = 0; key < keys[row].length; key++) {
                keyboard += keyColors[row][key];
                keyboard += keys[row][key];
            }

            keyboard += "\n";
        }

        System.out.println(keyboard);
    }
}
