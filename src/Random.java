import java.io.File;
import java.io.IOException;

public class Random {
    private InputOutput io = new InputOutput();
    private int a;
    private int b;
    private int i;
    private int numStudents = 0;
    public String randomizedGroups = "";
    public String currentGroup = "";
    public String[] block = new String[1000];
    // This array is created with 1000 spaces, however most of them will likely be
    // null, not taking up much space

    /*
     * While App.Java is reading the file, all the names get transferred to an array
     * within Random.Java, which also creates numStudents
     */
    public void setBlock(String name) {
        block[numStudents] = name;
        numStudents++;
    }

    /*
     * Randomization uses the studentsPerTable number given in App.Java to first
     * randomize the names, then seperate them into groups
     */
    public String Randomization(int studentsPerTable) {
        // all these variables have to be reset here because if randomization is
        // recalled on (in case the teacher didn't like the previos random generated
        // groups), their values would be at the end of the loop instead of the
        // beginning
        a = 0;
        b = 1;
        // b is set as 1 becuase it's used to label the groups as "Group 1, Group 2, etc
        i = 0;
        randomizedGroups = "";
        currentGroup = "";
        int leftoverGroup = numStudents % studentsPerTable;
        /*
         * This for loop is used to cycle through the names int eh array and randomize
         * them. It works by setting the the variable nm as block[c], then giving
         * block[c] the value of a random String in the block array, and giving where
         * that String used to be the String of nm. I got this from Mr. Griswold's
         * suggestion on how to randomize the groups
         */
        for (int c = 0; c < numStudents; c++) {
            String nm = block[c];
            int ran = (int) (Math.random() * numStudents);
            block[c] = block[ran];
            block[ran] = nm;
        }
        /*
         * This next part seperates the now random Array into groups of
         * StudentsPerTable. I cycles through numStudents, increasing both a and i,
         * adding block[i] to a String called currentGroup. When a is equal to
         * studentsPerTable, it adds currentGroup to the final Randomzation String,
         * resets a and currentGroup, continuing until the loop is done
         */
        while (i < numStudents) {
            currentGroup += block[i] + ", ";
            a++;
            if (a == studentsPerTable && i < numStudents - leftoverGroup) {
                randomizedGroups += "Group " + b + ": " + currentGroup;
                currentGroup = "";
                a = 0;
                b++;

            }
            i++;
        }
        /*
         * Because the textfile might not be evenly divided by studentsPerTable, this
         * while loop accounts for the leftover groups that would've never been reached
         * by (a == studentsPerTable)
         */
        while (leftoverGroup != 0 && i < numStudents) {
            currentGroup += block[i] + ", ";
            i++;
        }
        randomizedGroups += "Group " + b + ": " + currentGroup;
        /*
         * This saves them to the textfile currentRandom, which changes everytime
         * there's a new randomization
         */
        io.openWriteFile("currentRandom.txt");
        io.writeToFile(randomizedGroups);
        io.closeWriteFile();
        return randomizedGroups;
    }

    /*
     * In case the user wants to save the groups permanetely, this method is called
     * on to save the file. It creates a file with a name given by the user. I had
     * to research online on how to create a file, since the code on classroom did
     * not teach us to do that. After the file was created, I used the changing a
     * file code to change the contents of the file
     */
    public Boolean saveToFile(String filename) {
        try {
            File myObj = new File(filename + ".txt");
            if (myObj.createNewFile()) {
                io.openWriteFile(filename + ".txt");
                io.writeToFile(randomizedGroups);
                io.closeWriteFile();
                return false;
                // false is needed so that app.java knows if saveToFile should be rerun (if the
                // name the user has chosen has already been used)
            } else {
                System.out.println("This file already exists, please choose a new name.");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
            return true;
        }
    }
}
