import java.io.File;
import java.util.Scanner;


public class Game {

    private String movieName;
    private String guessedMovieName;
    private String wrongLetters;
    private int wrongGuesses;
    private boolean playerWin;
    boolean gameOver = false;
    private String guessedWrong;
    private int lineNumber;

    Game() {
        movieName = "";
        wrongLetters = "";
        lineNumber = 0;
        wrongGuesses = 0;

        movieName = readFilmTitle();
        movieName = movieName.trim();
        movieName = movieName.toLowerCase();
        guessedWrong = movieName;
        guessedMovieName = movieName.replaceAll("[a-z0-9]", "*");
        displayMessages();
    }

    private int movieNumber(int maxNumber) {
        double randomNumber = Math.random() * maxNumber;
        randomNumber++;
        return (int) randomNumber;
    }

    private String readFilmTitle() {
        String movieTitle = "";
        String[] allMoviesTitles = {" "};
        int lineCount = 0;

        try {
            File file = new File ("filme.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                allMoviesTitles = addElement(allMoviesTitles, scanner.nextLine());
                lineCount++;
            }
            scanner.close();
            lineNumber = movieNumber(lineCount);
            movieTitle = allMoviesTitles[lineNumber];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return movieTitle;
    }

    private String[] addElement(String[] stringArray, String newValue) {
        String[] tempArray = new String[stringArray.length+1];
        System.arraycopy(stringArray, 0, tempArray, 0, stringArray.length);
        tempArray[stringArray.length] = newValue;
        return tempArray;
    }

    void displayMessages() {
        if (wrongGuesses == 10) {
            System.out.println("Trebuie sa ghicesti: '" + guessedMovieName + "'");
            System.out.println("Jocul s-a terminat. Ai pierdut.");
            System.out.println("Cuvantul a fost: " +guessedWrong);
        } else if (playerWin) {
            System.out.println("Ai castigat!");
            System.out.println("Cuvantul a fost: " + guessedMovieName);
        } else {
            if (wrongGuesses == 0) {
                System.out.println("Trebuie sa ghicesti: " + guessedMovieName);
                System.out.print("Ghiceste o litera: ");
            } else if(wrongGuesses == 1) {
                System.out.println("Trebuie sa ghicesti: " +guessedMovieName);
                System.out.println("Ai ghicit incorect o singura data. Litera ghicita incorect : " + wrongLetters);
                System.out.print("Ghiceste o litera: ");
            } else {
                System.out.println("Trebuie sa ghicesti : " +guessedMovieName);
                System.out.println("Ai ghicit incorect de " + wrongGuesses + " ori. Litere ghicite incorect : " + wrongLetters);
                System.out.print("Ghiceste o litera: ");
            }
        }
    }

    boolean endGame() {
        gameOver = wrongGuesses == 10;
        if (playerWin) gameOver = true;
        return gameOver;
    }

    void checkLetter() {
        char newLetter;
        String temp;
        boolean findIt = false;

        Scanner keyboard = null;
        try {
            keyboard = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keyboard == null) throw new AssertionError();
        temp = keyboard.next().toLowerCase();
        newLetter = temp.charAt(0);
        for (int i = 0; i < movieName.length(); i++) {
            if (movieName.charAt(i) == newLetter) {
                if (guessedMovieName.charAt(i) == newLetter && !findIt) {
                    System.out.println("Litera '" + newLetter + "' este deja corecta.");
                }
                guessedMovieName = replaceCharAt(guessedMovieName, i, newLetter);
                findIt = true;
            }
        }
        if (findIt) {
            playerWin = true;
            for (int i = 0; i < movieName.length(); i++) {
                if (guessedMovieName.charAt(i) == '*') {
                    playerWin = false;
                }
            }
        } else {
            playerWin = true;
            for (int i = 0; i < wrongLetters.length(); i++) {
                if (wrongLetters.charAt(i) == newLetter) {
                    System.out.println("Litera '" + newLetter + "' este deja incorecta.");
                    findIt = true;
                    break;
                }
            }
            if (!findIt) {
                wrongLetters = wrongLetters + newLetter + " ";
                wrongGuesses++;
            }
        }
    }

    private String replaceCharAt(String str, int index, char replaceChar) {
        if (str == null) {
            return null;
        } else if (index < 0 || index >= str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replaceChar;
        return String.valueOf(chars);
    }
}
