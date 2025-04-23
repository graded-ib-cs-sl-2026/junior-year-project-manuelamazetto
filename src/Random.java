import java.io.File;
import java.io.IOException;

public class Random {
    private InputOutput io = new InputOutput();
    private int a;
    private int b;
    private int i;
    private int min = 0;
    private int max = 0;
    public int numStudents = 0;
    public Boolean namesLeft = true;
    public String randomizedGroups = "";
    public String currentGroup = "";
    public String fileName = "";
    private String currentName = "";
    public int leftoverGroup = 0;
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
              // "
        i = 0;
        randomizedGroups = "";
        currentGroup = "";
        leftoverGroup = numStudents % studentsPerTable;
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
        if (leftoverGroup != 0) {
            randomizedGroups += "Group " + b + ": " + currentGroup;
        }
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
            File myFile = new File(filename);
            if (myFile.createNewFile()) {
                io.openWriteFile(filename);
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

    public void editArray(String names, String filename){
    this.fileName = filename; //sets the variable fileName in this class as the String given my app.Java
    String currentName; 
    min = 0;
    max = 0;
    while (namesLeft == true) { //namesLeft is if there are still names the user has input that haven't been added to the array Block 
        currentName = "";
        max = names.indexOf("//", min); // max has the same index as when // is typed, so that the program gets the substring between min and max (just the name)
        if (max >= 0) { //if the indexOf max isn't -1 (meaning there is no // in the program, it can set the names to Block)
        currentName = names.substring(min, max); 
        block[numStudents] = currentName;
        numStudents++;
        min = max + 2; //because // is two characters long, +2 must be added so the next substring doesn't include //
        }
        else {
        //once max is equal to -1, namesLeft is set to false (because there are none left) and the loop ends, as all the names in the String have been given
        namesLeft = false;
        }
    }
    editFile(fileName); //calls the method editFile
    }

    public void editFile(String filename){
        io.openWriteFile(filename); //opens the file the user has given in app.java
        for (int c = 0; c < numStudents; c++){ //sets each line in the file as one string from block until all the strings have been added
            io.writeToFile(block[c]);
        }
        io.closeWriteFile();
    }

    public void deleteFile(String filename){
        File myFile = new File(filename); //From what I researched, I needed to create ae file object to use the method myFile.delete
        myFile.delete(); 
    }

    public void renameFile(String filename){
        //for the sake of abstraction, I put both methods (which use the same file) into rename file
        saveToFile(filename);
        editFile(filename);
    }
  /* Delete names works by finding a string in the array that is completely equal to the name given by the user, and then replacing that name with the last name in the array. This works similarly as editArray but with deleting names instead of them */
    public void deleteNames(String names){
     min = 0;
     while (namesLeft == true) { 
     currentName = "";
     max = names.indexOf("//", min); 
        if (max >= 0) { 
        currentName = names.substring(min, max); 
        for (int c = 0; c < numStudents; c++){
            if (block[c].equals(currentName)){
                for (int d = 0; d < numStudents; d++){
                block[c] = block[numStudents - 1];
                }
            }
        }
     min = max + 2; 
     numStudents = numStudents - 1; //numStudents must go down becuase the amount of students decreased
    }else {
    namesLeft = false;
        }
    
    }
    }
}
