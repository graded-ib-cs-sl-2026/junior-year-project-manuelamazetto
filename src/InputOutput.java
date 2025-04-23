import java.util.Scanner;
import java.io.*;

public class InputOutput {

    private Scanner in = new Scanner(System.in);
    private Scanner fileInput;
    private File file;

    private File outFile;
    private PrintStream fileOutput;

    /**
     * Used to open a file for READING
     * 
     * @param filename
     * @throws FileNotFoundException
     */
    public void openFile(String filename) throws FileNotFoundException {
        file = new File(filename);
        fileInput = new Scanner(file);
    }

    /**
     * Used to open a file for WRITING. Will delete the contents of the file
     * if the file already exists.
     * 
     * @param filename
     */
    public void openWriteFile(String filename) {
        try {
            outFile = new File(filename);
            fileOutput = new PrintStream(outFile);
        } catch (Exception e) {
            output("Output file was not opened - error " + e.toString());
            outFile = null;
            fileOutput = null;
        }
    }

    /**
     * Used to close the file for writing when finished
     */
    public void closeWriteFile() {
        fileOutput.close();
        outFile = null;
        fileOutput = null;
    }

    /**
     * Used to write ONE line of text to the end of our write file
     * 
     * @param s
     */
    public void writeToFile(String s) {
        if (fileOutput == null) {
            return;
        } else {
            fileOutput.println(s);
            fileOutput.flush();
        }
    }

    /**
     * Used to close our connection to the file for READING
     */
    public void closeFile() {
        fileInput.close();
        file = null;
        fileInput = null;
    }

    /**
     * Used to check if the file we are reading has any unread lines
     * 
     * @return
     */
    public boolean fileHasNextLine() {
        if (fileInput == null) {
            return false;
        } else {
            return fileInput.hasNextLine();
        }
    }

    /**
     * Used to get the next unread line from our read file
     * 
     * @return
     */
    public String getNextLine() {
        if (fileHasNextLine()) {
            return fileInput.nextLine();
        } else {
            return "";
        }

    }

    /**
     * Print to the terminal
     * 
     * @param s
     */
    public void output(String s) {
        System.out.println(s);
    }

    /**
     * Read from the terminal
     * 
     * @return
     */
    public String input() {
        return in.nextLine();
    }

}