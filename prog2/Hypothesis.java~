/* 
 * Hypothesis.java
 *
 * Defines a new "Hypothesis" type, which is a path that represents
 * a hypothesized utterance, obtained by searching a lattice
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 * 
 * Use of any additional Java Class Library components is not permitted 
 * 
 * J.P. Sohal
 * Megan Dittamore
 *
 */
 
 
  import java.io.*;
  import java.util.*;
  import java.lang.*;
  

public class Hypothesis {
    private double pathScore;                  // Stores the cumulative path score
    private java.util.ArrayList<String> words; // Array of words in the path

    // Constructor

    // Hypothesis
    // Preconditions:
    //     - None
    // Post-conditions
    //     - this.words points to a new, empty, ArrayList object
    //     - this.pathScore == 0
    public Hypothesis() {
        this.words = new ArrayList<String>();
        this.pathScore = 0;
        return;
    }

    // Mutator/Modifier

    // addWord
    // Preconditions:
    //     - word is another word to add to (the end of) the path
    //     - combinedScore is the weight on the corresponding edge
    //       which is a weighted sum of the amScore and lmScore
    // Post-conditions
    //     - If word equals "-silence-" then
    //            words is NOT modified: -silence- words are not included
    //            the combinedScore IS added to the pathScore: its weight counts
    //     - If word does not equal "-silence-" but DOES contain an underscore
    //            word is split into individual words at the underscore(s)
    //            each individual word is added, in sequence, to words
    //            For example, if word equals "going_to" then "going" is first
    //            added to the end of words, and then "to" is added to the end
    //            of words, so that words is now two longer 
    //            the combinedScore is added (once) to the pathScore
    //     - If word is not "-silence-" and DOES NOT contain an underscore
    //            word is added to the end of words, so that words is one longer
    //            the combinedScore is added to the pathScore
    // Hints:
    //     - To split a word into individual words, see String's split method
    public void addWord(String word, double combinedScore) {
        if(word.equals("-silence-")){
            pathScore += combinedScore;          
        }
        if(!word.equals("-silence-") && word.contains("_")){
             String [] splitWords = word.split("_");
             for(int i = 0; i < splitWords.length; i++){
                  words.add(splitWords[i]);
             }   
             pathScore += combinedScore;
        }     
        else if(!word.equals("-silence-")){
            words.add(word);
            pathScore += combinedScore;
        }    

        return;
    }

    // Accessors

    // getPathScore
    // Preconditions:
    //     - None
    // Post-conditions
    //     - this.pathScore is returned
    public double getPathScore() {
        return this.pathScore;
    }

    // getHypothesisString
    // Preconditions:
    //     - The hypothesis has already been created via calls to addWord
    // Post-conditions
    //     - A single string representation of the hypothesis sequence
    //       is returned, obtained by concatenating the individual words
    //       in the hypothesis (with spaces in-between)
    public String getHypothesisString() {
        String sFinal = new String();
        
        for (int i = 0; i < words.size(); i++){
            String s = words.get(i);
            s = (s + " "); 
            sFinal = sFinal + s;
        }
        
        return sFinal;
    }

    // computeWER
    // Preconditions:
    //     - referenceFilename is the name of a file with the reference transcript
    //     - The hypothesis has already been created via calls to addWord
    // Post-conditions
    //         - The word error rate (WER) of the hypothesis with respect to 
    //        the reference transcript is returned.
    //      - WER first requires you to compute the minimum edit distance between
    //        the hypothesis and reference word sequences.  Given that, WER
    //          is simply the minimum edit distance divided by the number of words
    //        in the reference sequence
    public double computeWER(String referenceFilename) {
    
    java.io.PrintStream output;
    java.util.ArrayList<String> referenceList = new ArrayList<String>();
    try {Scanner input = new Scanner(new File(referenceFilename));       
        while(input.hasNext()) {
            referenceList.add(input.next());
        }
        
    } catch(FileNotFoundException e ) {
          System.err.println("Error: unable to open file." + referenceFilename);
          System.exit(1);
    } finally {
             
    }
    double matrix[][] = new double[words.size()+1][referenceList.size()+1];
    
    for(int i = 0; i <= words.size(); i++) {
        matrix[i][0] = (double) i;
    }
    for(int j = 0; j <= referenceList.size(); j++) {
        matrix[0][j] = (double) j;
    }
    for(int j = 1; j <= referenceList.size(); j++) {
        for(int i = 1; i <= words.size(); i++) {
            if (words.get(i-1).equals(referenceList.get(j-1))) {            
                matrix[i][j] = matrix[i-1][j-1];
            } else {
            
                double minFinal = 0.0;
                double minimum = 0.0;
                minimum = Math.min(matrix[i-1][j], matrix[i][j-1]);
                minFinal = Math.min(minimum, matrix[i-1][j-1]);
                matrix[i][j] = 1.0 + minFinal;
            }
        }
    }
    double finalWER = matrix[words.size()][referenceList.size()];
       
        return finalWER /(double) referenceList.size();
        
    }
}
