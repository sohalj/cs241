/* 
 * Program2.java
 *
 * The driver program for CSCI 241's Program 2
 *
 * Creates Lattice objects for each lattice in the lattice list input file
 * searches them to find and print the best hypothesis, and prints 
 * various lattice statistics
 * 
 * Do not modify this file.  
 *
 * Brian Hutchinson
 * October 27th, 2013
 *
 * ----------------------------------------------------------------------------
 *
 * usage:
 *
 * java Program2 latticeListFilename lmScale outputDir
 *
 * where the arguments are
 * 
 *   latticeListFilename    a plaintext file with one line per lattice
 *                          each line contains two strings, separated by a space
 *                          the first string is the filename for a lattice file
 *                          the second string is the filename for a ref file
 *   lmScale                a non-negative number that specifies how much to weight 
 *                          the "language model" score, relative to the 
 *                          "acoustic model" score
 *   outputDir              a directory where output lattices and dot files will
 *                          be written, one for each line in the lattice list
 *
*/


public class Program2 {
    private static final String slash = "/"; // You may need to change this to "\" on Windows...

    public static void main(String[] args) {
    
        // Check and load arguments
        if( args.length != 3 ) {
            System.err.println("Error: Wrong number of arguments.");
            System.exit(2);
        }

        String latticeListFilename = args[0];
        double lmScale = Double.parseDouble(args[1]);
        String outputDir = args[2];

        // Read through latticeListFilename
        java.util.Scanner input = null;
        try {
            input = new java.util.Scanner(new java.io.File(latticeListFilename));
        } catch( java.io.FileNotFoundException e ) {
            System.err.println("Error: Unable to open file " + latticeListFilename);
            System.exit(1);
        }

        double totalWER = 0.0;
        int numFiles = 0;
        while( input.hasNext() ) {
            numFiles++;

            // Read next line in latticeListFilename
            String latticeFilename = input.next();
            String refFilename = input.next(); 
        
            // Build the lattice
            Lattice lattice = new Lattice(latticeFilename);
            System.out.println("\nUtterance " + lattice.getUtteranceID());

            // Print reference text
            printReference(refFilename);

            // Decode, print best hypothesis and various statistics
            Hypothesis hypothesis = lattice.decode(lmScale);
            System.out.println("Hypothesis: " + hypothesis.getHypothesisString());
            double WER = hypothesis.computeWER(refFilename);
            System.out.println("WER : " + new java.text.DecimalFormat("0.000").format(WER));
            totalWER += WER;
            System.out.println("Number of unique paths: " + lattice.countAllPaths());
            System.out.println("Lattice density: " + new java.text.DecimalFormat("0.000").format(lattice.getLatticeDensity()));
        
            // Write lattice to output dir in dot and lattice formats
            lattice.writeAsDot(outputDir + slash + lattice.getUtteranceID() + ".dot");
            String latticeOutputFilename = outputDir + slash + lattice.getUtteranceID() + ".lattice";
            if( latticeOutputFilename.equals(latticeFilename) ) {
                System.err.println("Error: Output directory must not be the same as the input directory\n");
                System.exit(5);
            }
            lattice.saveAsFile(latticeOutputFilename);
        }
    
        System.out.println("Avg WER = " + totalWER/numFiles);

        return;
    }
                

    private static void printReference(String refFilename) {
        java.util.Scanner refInput = null;
        try {
            refInput = new java.util.Scanner(new java.io.File(refFilename));
        } catch( java.io.FileNotFoundException e ) {
            System.err.println("Error: Unable to open file " + refFilename);
            System.exit(1);
        }
        if( refInput.hasNext() ) {
            System.out.println("Reference: " + refInput.nextLine());
        } else {
            System.out.println("Reference: ");
        }
        return;
    }
}
