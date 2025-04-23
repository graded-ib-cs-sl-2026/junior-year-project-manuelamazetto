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
    private String option2 = "";
    // This will be used to name the file the user would like to create
    private Boolean fileError = true;
    private Boolean initialFileError = true;
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
            initialFileError = false;
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
        System.out.println("[5] Delete Class/File");
        // alows for the program to read the lines the user types in when prompted
        Scanner input = new Scanner(System.in);
        option = input.nextInt();
        // int specifies that the line will be an int. If the line is not an int, the
        // program will break
        if (option == 1) {
            input.nextLine(); //lets the program know the next input will be a String not an Int
            while (initialFileError == true){
            System.out.println("Which class would you like to randomize?");
            filename = input.nextLine();
            myRandom = readBlock(filename + ".txt"); //assuming the user won't add .txt at the end of the filename
            }
              /* By including readBlock here instead of before openingMenu in start, it allows the user to pick which file they want to randomize. I have done this for all the options and it's great because the teacher can have multiple textfiles, and then chose which one they want to randomize, meaning they can have multiple classes.*/
            System.out.println("Is there anyone missing from your class?");
            option2 = input.nextLine();
            if (option2.indexOf("ye") > -1 || option2.indexOf("Ye") > -1){ 
                System.out.println("Please name the people with a // between their names and no spaces");
                names = input.nextLine();
                myRandom.deleteNames(names + "//"); //uses the same code to permanatently delete names except it doesn't save them to the file like Delete does
            }
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
            option2 = "ye";
            input.nextLine();
            while (option2.indexOf("ye") > -1 || option2.indexOf("Ye") > -1) {
                /*
                 * option is automatically set as one, and will keep repeating and randomizing
                 * the names while it remains one. However, at the end of the loop, it gives the
                 * user the option to re-randomize, making it so option is whatever number they
                 * type. If they type 1, the loop replays, but if they type any other number, it
                 * breaks out of the loop, moving onto the next part
                 */
                System.out.println(myRandom.Randomization(studentsPerTable));
                System.out.println("Would you like to re-randomize?");
                option2 = input.nextLine();
            }
            System.out.println("Would you like to save this to a file?");
            option2 = input.nextLine();
            // this is needed because all of the other inputs have been an int, so this
            // clarifies that the next one will be a String, not an int
            while (option2.indexOf("ye") > -1 && fileError == true || option2.indexOf("Ye") > -1 && fileError == true) {
                System.out.println("What would you like to name this file?");
                filename = input.nextLine() + ".txt";
                // this sends the filename chosen by the user into the method, which saves the
                // groups into a new file
                fileError = myRandom.saveToFile(filename);
                // fileError is updated to be false (if there's no error) or if there is an
                // error, it remains true, not breaking out of the loop
            }
            openingMenu(); 

        } else if (option == 2) {
            System.out.println("Which class' names would you like to edit?");
            input.nextLine();
            filename = input.nextLine() + ".txt"; 
            myRandom = readBlock(filename);
            System.out.println("Would you like to remove or add?");
            option2 = input.nextLine();
            if (option2.indexOf("remove") > -1 && option2.indexOf("add") == -1 || option2.indexOf("Remove") > -1 && option2.indexOf("Add") == -1){ //add and Add count as different answers so I need to account for both of them
                System.out.println("What names would you like to remove from this file?");
                names = input.nextLine();
                myRandom.deleteNames(names + "//");
                myRandom.editFile(filename);
            }else if (option2.indexOf("remove") == -1 && option2.indexOf("add") > -1 || option2.indexOf("Remove") == -1 && option2.indexOf("Add") > -1){
                System.out.println("What names would you like to add? Please seperate them by a // and no spaces");
                names = input.nextLine();
                myRandom.editArray(names + "//", filename); //I need to add "//" at the end of the string of names because otherwise the editArray method will stop at the second to last name, since it doesn't see a // at the end of the string
               
            }else if (option2.indexOf("remove") == -1 && option2.indexOf("add") == -1 || option2.indexOf("Remove") == -1 && option2.indexOf("Add") == -1) {
                System.out.println("Please say whether you would like to remove or add names. Returning to opening menu.");
                openingMenu();
            }else {
                System.out.println("Please say ONLY delete or and, not both. Returning to opening menu.");
                openingMenu();
            }
            openingMenu();

            
        } else if (option == 3) {
            myRandom = new Random();
            while(fileError == true){
                System.out.println("What would you like to name your new class?");
                input.nextLine();
                filename = input.nextLine() + ".txt";
                fileError = myRandom.saveToFile(filename);
            }
            //while fileError remains true (meaning there is a fileError and there is another file with that name), the user will have the ability to chose an original name for their file
            System.out.println("What names would you like to add? Please seperate them by // and no spaces.");
            names = input.nextLine();
            myRandom.editArray(names + "//", filename);
            openingMenu();
        }   
         else if (option == 4) {
            /* How I did renaming (since I couldn't find a good way to just rename the file) was create a new file with the chosen name (by the user), upload all the contents of the old file to that new file, then delete the previous file. This was practical because it just reused other methods I had, so I didn't have to rewrite anything new for it */
            System.out.println("Which file would you like to rename?");
            input.nextLine();
            filename = input.nextLine() + ".txt";
            myRandom = readBlock(filename); 
            myRandom.deleteFile(filename);
            System.out.println("What would you like to rename your file?");
            filename = input.nextLine() + ".txt";
            myRandom.renameFile(filename);
            openingMenu();
         } else if (option == 5){
            myRandom = new Random();
            System.out.println("What class would you like to delete?");
            input.nextLine();
            filename = input.nextLine() + ".txt";
            if (filename.equals("currentRandom") == true){
                //If the user tries to delete currentRandom, which is essential for the program since all the groups are copied to that file
                System.out.println("You may not delete that file as it's essential to the program. Returning to opening menu.");
                openingMenu();
            }
            System.out.println("Are you sure you woud like to delete " + filename + "?") ;
            option2 = input.nextLine();
            if (option2.indexOf("ye") > -1 || option2.indexOf("Ye") > -1){
            myRandom.deleteFile(filename);
            }
            openingMenu();
        }else {
            System.out.println("That's not an option, please pick [1] [2] [3] [4] [5]");
            openingMenu();
        }
    }
    
    

    public static void main(String[] args) throws Exception {
        new App().start();
    }
}