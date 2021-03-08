package solution;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Sample program to show how to read the comma delimited text file pets.txt and
 * break the lines up into a name, age, and weight.
 * 
 * @author ???
 * @version ???
 * 
 */
public class FileReading
{

    private Scanner fileIn = null;

    /**
     * Constructor reads from filename.
     * 
     * @param filename
     *            The name of the file.
     */
    public FileReading(String filename)
    {
        // open the input file
        try
        {
            setFileIn(new Scanner(new FileReader(filename)));
        }
        catch (IOException ioe)
        {
            System.out.println("Could not open the input file." + filename);
            System.exit(0);
        }

        // read the data
        readLines();

        // close the input file
        fileIn.close();
    }

    /**
     * Sets the input scanner.
     * 
     * @param input
     *            The input scanner.
     */
    public void setFileIn(Scanner input)
    {
        fileIn = input;
    }

    /**
     * Read lines of the file.
     */
    public void readLines()
    {
        String[] line;
        String petName;
        int petAge;
        double petWeight;

        // as long as there are lines to read
        while (fileIn.hasNextLine())
        {
            // read a line from the file and split it
            // into an array of Strings around the commas
            line = fileIn.nextLine().split(",");
            // put the data before the first comma in petName
            petName = line[0];
            // put the data before the second comma in petAge
            // after converting the String to an integer
            petAge = Integer.parseInt(line[1]);
            // put the data before the third comma in petWeight
            // after converting the String to a Double
            petWeight = Double.parseDouble(line[2]);
            // print out the data for testing to make sure it worked
            System.out.print("Name: " + petName + " ");
            System.out.print("Age: " + petAge + " ");
            System.out.println("Weight: " + petWeight);
        }
    }

    /**
     * The main program to read from pets.txt.
     * 
     * @param args
     *            unused.
     */
    public static void main(String[] args)
    {
        new FileReading("pets.txt");
    }

}
