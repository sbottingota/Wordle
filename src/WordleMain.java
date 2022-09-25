import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public final class WordleMain {

    public static void main(String[] args) throws IOException {
        playGame();
    }

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

    private static String stringArrToString(String[] strings) {
        String output = "";

        for (int i = 0; i < strings.length; i++) {
            output += strings[i];
        }

        return output;
    }

    private static String getKeyColor(char key) {
        for (int row = 0; row < keys.length; row++) {
            if (Arrays.asList(charArrayToCharObjArray(keys[row])).contains(Character.valueOf(key))) {
                return keyColors[row][String.valueOf(keys[row]).indexOf(key)];
            }
        }
        return RESET;
    }

    //sets the ansiColor of a particular key on the keyboard (not guessed, not in the word, not in the right place, in the right place)
    public static void setKeyboardLetterColor(char letter, String ansiColor) {

        for (int row = 0; row < keys.length; row++) {
            if (Arrays.asList(charArrayToCharObjArray(keys[row])).contains(Character.valueOf(letter))) {
                keyColors[row][String.valueOf(keys[row]).indexOf(letter)] = ansiColor;
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

            keyboard += RESET;
            keyboard += "\n";
        }

        keyboard += RESET;
        System.out.println(keyboard);
    }

    //prints a color coded string showing you how good your guess was
    private static void printGuessCorrectness(String guess, String wordle) {
        char[] guessArray = guess.toCharArray();
        char[] wordleArray = wordle.toCharArray();

        String[] output = new String[guessArray.length];

        //set all the chars to gray
        for (int character = 0; character < guessArray.length; character++) {
            output[character] = WHITE + guessArray[character];
        }

        //set the appropriate ones to yellow
        for (int character = 0; character < guessArray.length; character++) {
            if (Arrays.asList(charArrayToCharObjArray(wordleArray)).contains(guessArray[character])) {
                output[character] = YELLOW + guessArray[character];
                wordleArray[String.valueOf(wordleArray).indexOf(guessArray[character])] = ' ';
            }
        }

        wordleArray = wordle.toCharArray();

        //set the appropriate ones to green
        for (int character = 0; character < guessArray.length; character++) {
            if (guessArray[character] == wordleArray[character]) {
                output[character] = GREEN + guessArray[character];
            }
        }

        System.out.println(stringArrToString(output) + RESET);
    }

    private static void changeKeyboardColors(String guess, String wordle) {
        char[] guessArray = guess.toCharArray();
        char[] wordleArray = wordle.toCharArray();

        for (int character = 0; character < guessArray.length; character++) {
            if (guessArray[character] == wordleArray[character]) {
                setKeyboardLetterColor(guessArray[character], GREEN);
            } else if (Arrays.asList(charArrayToCharObjArray(wordleArray)).contains(Character.valueOf(guessArray[character])) && !getKeyColor(guessArray[character]).equals(GREEN)) {
                setKeyboardLetterColor(guessArray[character], YELLOW);
            } else if (!getKeyColor(guessArray[character]).equals(GREEN)){
                setKeyboardLetterColor(guessArray[character], WHITE);
            }
        }
    }

    public static int playGame() throws IOException {
        String wordle = getWordle();

        for (int guesses = 0; guesses < 6; guesses++) {
            System.out.println("\n");
            printKeyboard();
            String guess = getGuess();

            printGuessCorrectness(guess, wordle);

            if (guess.equalsIgnoreCase(wordle)) {
                System.out.println("Well done! You got it in " + (guesses + 1) + " guess(es)!");
                return guesses + 1;
            }

            changeKeyboardColors(guess, wordle);
        }

        System.out.println("The wordle was: " + wordle + ". You failed to get it in 6 tries");
        return -1;
    }
}
