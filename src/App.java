import java.util.Scanner;

public class App {
    public Random myRandom;
    // creates a connection to the other two classes, Random.java and
    // InputOutput.java
    private InputOutput io = new InputOutput();
    public int numStudents = 0;
    // numStudents is how many students are the
    public int studentsPerTable = 0;
    // studentsPerTable is later established when the user sets it as whatever they
    // would like
    private int option = 1;
    // this is used for the user to select what option they would like.
    private String filename = "";
    private String filename2 = "";
    // This will be used to name the file the user would like to create
    private Boolean fileError = true;
    private String names = "";

    /**
     * Where the program begins!
     */
    public void start() {
       
        openingMenu();
    }

    // This is utilzied to read the file the user has set up. It is able to count
    // lines in the text file
    public Random readBlock(String filename) {
        Random s = new Random();
        try {
            io.openFile(filename);
        } catch (Exception e) {
            io.output(e.toString());
            return s;
        }

        System.out.println("reading...");
        while (io.fileHasNextLine()) {
            // this sets the variable n as the next line of the text file
            String n = io.getNextLine();
            // sets the file lines into the random.java file
            s.setBlock(n);
            // raises the number of students everytime there is another line in the text
            // file
            numStudents++;
        }
        return s;

    }

    public void openingMenu() {
        System.out.print("Hello, welcome to Random Seat Generator!");
        System.out.println(" Please select which of the following options you would like to do:");
        System.out.println("[1] Randomize Seating");
        System.out.println("[2] Edit Class Names");
        System.out.println("[3] Create New Class");
        System.out.println("[4] Rename Class");
        // alows for the program to read the lines the user types in when prompted
        Scanner input = new Scanner(System.in);
        option = input.nextInt();
        // int specifies that the line will be an int. If the line is not an int, the
        // program will break
        if (option == 1) {
            System.out.println("Which class would you like to randomize? Please type in the exact name, not including '.txt'");
            input.nextLine();
            filename = input.nextLine();
            myRandom = readBlock(filename + ".txt");
            System.out.println("How many people would you like per group?");
            studentsPerTable = input.nextInt();
            // studentsPerTable needs to be recorded here, because if it where directly put
            // into Randomization, it wouldn't be possible to control if the user requests
            // group sizes too small or big
            if (studentsPerTable > numStudents || studentsPerTable < 1) {
                // if the group size is more than the amount of students, or smaller than one,
                // it will run the program again, telling the user they did something wrong
                System.out.println("That's not possible due to the number of students.");
                openingMenu();
            }
            while (option == 1) {
                /*
                 * option is automatically set as one, and will keep repeating and randomizing
                 * the names while it remains one. However, at the end of the loop, it gives the
                 * user the option to re-randomize, making it so option is whatever number they
                 * type. If they type 1, the loop replays, but if they type any other number, it
                 * breaks out of the loop, moving onto the next part
                 */
                System.out.println(myRandom.Randomization(studentsPerTable));
                System.out.println("Would you like to re-randomize? Answer [1] for yes or [2] for no");
                option = input.nextInt();
            }
            System.out.println("Would you like to save this to a file? Answer [1] for yes or [2] for no");
            option = input.nextInt();
            input.nextLine();
            // this is needed because all of the other inputs have been an int, so this
            // clarifies that the next one will be a String, not an int
            while (option == 1 && fileError == true) {
                System.out.println("What would you like to name this file?");
                filename = input.nextLine() + ".txt";
                // this sends the filename chosen by the user into the method, which saves the
                // groups into a new file
                fileError = myRandom.saveToFile(filename);
                // fileError is updated to be false (if there's no error) or if there is an
                // error, it remains true, not breaking out of the loop
            }

        } else if (option == 2) {
            System.out.println("Which class names would you like to edit?");
            input.nextLine();
            filename = input.nextLine() + ".txt";
            myRandom = readBlock(filename);
            System.out.println("What names would you like to add? Please seperate them by a //");
            names = input.nextLine();
            myRandom.editArray(names + "//");
            myRandom.editFile(filename);

            
        } else if (option == 3) {
            myRandom = new Random();
            while(fileError == true){
                System.out.println("What would you like to name your new class?");
                input.nextLine();
                filename = input.nextLine() + ".txt";
                fileError = myRandom.saveToFile(filename);
            }
            System.out.println("What names would you like to add? Please seperate them by //");
            names = input.nextLine();
            myRandom.editArray(names + "//");
            myRandom.editFile(filename);
        }   
         else if (option == 4) {
            System.out.println("Which file would you like to rename?");
            input.nextLine();
            filename = input.nextLine() + ".txt";
            myRandom = readBlock(filename);
            myRandom.deleteFile(filename);
            System.out.println("What would you like to rename your file?");
            filename = input.nextLine() + ".txt";
            myRandom.saveToFile(filename);
            myRandom.editFile(filename);
        
            
        } else {
            System.out.println("That's not an option, please pick [1] [2] [3] [4]");
            openingMenu();
        }
    }
    
    

    public static void main(String[] args) throws Exception {
        new App().start();
    }
}