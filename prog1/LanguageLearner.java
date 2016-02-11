/* 
 * LanguageLearner.java
 *
 * Implements methods for learning from a training text file, writing 
 * a dictionary and count statistics, and generating new sentences
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 * 
 * Use of any additional Java Class Library components is not permitted 
 * 
 * Author Kelly Lyon & J.P. Sohal
 * 
 *
 */

import java.util.HashMap;
import java.util.Random;


public class LanguageLearner {
     static int total;
    // This RANDOM_SEED should be provided to the 
    public static final int RANDOM_SEED = 999;

    // getMostProbableNextWord
    // Preconditions:
    //        - counts[i][j] contains the number of times word j follows word i in the input text
    //        - indexToWord[i] is the ith word in the dictionary, for all i
    // Postconditions:
    //        - for all words i, the ith entry of the returned array contains the index j 
    //        of the word that most often follows word i     
    // Notes:
    //         - It is possible that two or more words are tied for being the most probable to follow
    //        Among the tied words, use the one with the smallest index (closest to the front
    //        of the dictionary)
    public static int[] getMostProbableNextWord(int[][] counts, String[] indexToWord) {
    
        int [] nextIndexArray = new int [total+1]; 
        double [][] probabilityArray = new double[total+1][total+1];
        double iCount;
        double current; 
        
        for(int i = 0; i<=total; i++){
            iCount = 0;
            for(int j = 0; j<=total; j++){
                iCount+=counts[i][j];                                     
            }
            for (int j2 = 0; j2<=total; j2++){
                probabilityArray[i][j2]=counts[i][j2]/iCount; 
            }  
        }
        
        for(int i = 0; i<=total; i++){
            current = 0;
            for(int j = 0; j<=total; j++){
                if(probabilityArray[i][j] > current){
                        nextIndexArray[i]= j; 
                        current= probabilityArray[i][j];
                }              
            }
         }   
     return nextIndexArray;
    }

    // generateDictionary
    // Preconditions:
    //      - inputFileName is the name of a readable, space delimited text file, where 
    //        each line begins with the <s> token and ends with the </s> token
    //      - wordToIndex is a reference to an existing, empty HashMap from String to integers
    // Postconditions:
    //      - the i'th entry of the returned array contains the ith dictionary word
    //      - wordToIndex is populated such that each word w maps to its index i in the dictionary 
    //        Note: these two structures let you map back and forth between string and index 
    //        representations of the dictionary words
    //      - If inputFileName does not exist or is not readable, then the following error message is printed
    //        "Error: Unable to open file $inputFileName" // $inputFileName denotes the actual file name
    //        and the system exists with value 1
    // Notes:
    //         - The words in the dictionary must be ordered as they are encountered (excluding duplicates)
    //           For example, <s> should always be the 0th element in the dictionary, the following word should be 1
    public static String[] generateDictionary(String inputFileName, HashMap<String,Integer> wordToIndex){
        
         java.io.PrintStream output;
         String [] dictionaryArray = new String [5000];	 
         int i = 0;
         boolean duplicate = false; 
         
         try {
                java.util.Scanner input = new java.util.Scanner(new java.io.File(inputFileName));       

                while (input.hasNext()){
                        String token = input.next();
                        dictionaryArray[i] = token;
                        duplicate = false;                                
                            for ( int a = 0; a < i; a++){
                                if (dictionaryArray[a].equals(token)){
                                    duplicate = true; 
                                    break;
                                }
                            }                            
                            if (duplicate == false ){                            
                                wordToIndex.put(token, i);
                                i++;
                            }                            
                            if ( i== 0){
                                wordToIndex.put(token, i);
                                i++;
                            }
                }
                total = i;
            
            }          
         catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: unable to open file.");
               System.exit(1);
         }        
         finally {
                  
         }
 
        return dictionaryArray;
    }
    
    // printDictionary 
    // Preconditions:
    //        - indexToWord[i] is the ith word in the dictionary, for all i
    //        - dictionaryFileName is a valid, writable filename on the filesystem
    // Postconditions:
    //        - The dictionary's contents will be written to dictionaryFileName. Each line is
    //          $index $word
    //          where $index denotes the actual index and $word denotes the corresponding
    //        - If dictionaryFileName does not exist or is not writable, then the following error message is printed
    //          "Error: Unable to open file $dictionaryFileName for writing"
    //          and the system exists with value 1
    public static void printDictionary(String[] indexToWord, String dictionaryFileName) {

        java.io.PrintStream output = null;
      
      try {
             output = new java.io.PrintStream(dictionaryFileName);
             for(int c = 0; c<=total-1; c++){
                output.print(c + " ");
                output.println(indexToWord[c]);
             }
          }        
      catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: unable to open file.");
               System.exit(1);
      }        

      finally {
  
      }

        return;
    }

    // printCounts
    // Preconditions:
    //        - counts[i][j] contains the number of times word j follows word i in the input text
    //        - indexToWord[i] is the ith word in the dictionary, for all i
    //        - countsFileName is a valid, writable filename on the filesystem
    // Postconditions:
    //        - The *non-zero* counts will be written to countsFileName, specifically
    //            - each line with take the form "$word1 $word2 $count" where 
    //              $count is the number of times $word2 followed $word1 in the training data
    //            - word sequences that never appeared in the data are not printed
    //        - If countsFileName does not exist or is not writable, then the following error message is printed
    //          "Error: Unable to open file $countsFileName for writing"
    //          and the system exists with value 1
    public static void printCounts(int[][] counts, String[] indexToWord, String countsFileName) {
    
            java.io.PrintStream output = null;
                 
         try{
             output = new java.io.PrintStream(countsFileName);
             for(int i = 0; i<=total; i++){
                for(int j = 0; j<=total; j++){
                        if(counts [i][j] == 0){
                        }
                        else{
                            output.println(indexToWord[i] + "  " + indexToWord[j] + "  "  + counts[i][j]);                         
                        }                        
                }
             }
           }        
         catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: unable to open file.");
               System.exit(1);
         }        

         finally {
  
        }    
        return;
    }

    // loadCounts 
    // Preconditions:
    //        - wordToIndex is populated such that each word w maps to its index i in the dictionary 
    //        - inputFileName is the name of a readable, space delimited text file, where 
    //        each line begins with the <s> token and ends with the </s> token
    // Postconditions:
    //        - The two-dimensional array object returned contains in its (i,j)th entry the number of times
    //          word i is followed by word j in the file inputFileName
    //        - If inputFileName does not exist or is not readable, then the following error message is printed
    //          "Error: Unable to open file $inputFileName" // $inputFileName denotes the actual file name
    //          and the system exists with value 1
    public static int[][] loadCounts(HashMap<String,Integer> wordToIndex, String inputFileName) {
                 
                int [][] countArray = new int [total+1][total+1];
                int curIndex;
                int nextIndex; 
     try{      
                java.util.Scanner input = new java.util.Scanner(new java.io.File(inputFileName));
                String curWord = input.next();
                String nextWord = curWord; 
  
                while(input.hasNext()){
                    nextWord = input.next();                    
                    curIndex = wordToIndex.get(curWord);
                    nextIndex = wordToIndex.get(nextWord);
                    countArray[curIndex][nextIndex] += 1;
                    curWord = nextWord;                 
                }    
     }          
     catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: unable to open file.");
               System.exit(1);
         }        
     finally {
                  
     }
    
      return countArray;
    }
    
    

    // convertCountsToProbabilities
    // Preconditions:
    //        - counts[i][j] contains the number of times word j follows word i in the input text
    // Postconditions:
    //        - The two-dimensional array object returned contains in its (i,j)th entry the probability
    //          that word j follows word i in the input text
    //          These probabilities are just the relative frequencies of the word sequences; i.e.
    //          P( wj follows wi ) = C(wi wj)/C(wi), where wi stands for word i and wj stands for word j,
    //          C(wi wj) is the number of times wj follows wi, and C(wi) is the number of times wi occurs
    //          Accordingly, each of the rows of the returned two-dimensional array should sum to 1
    public static double[][] convertCountsToProbabilities(int[][] counts) {
        double [][] probabilityArray = new double[total+1][total+1];
        double iCount; 
        for(int i = 0; i<=total; i++){
            iCount = 0;
            for(int j = 0; j<=total; j++){
                iCount+=counts[i][j]; 
            }
            for (int j2 = 0; j2<=total; j2++){
                probabilityArray[i][j2]=counts[i][j2]/iCount; 
            }  
        }
    
        return probabilityArray;
    }

    // generateMostProbableText
    // Preconditions:
    //        - wordToIndex is populated such that each word w maps to its index i in the dictionary 
    //        - indexToWord[i] is the ith word in the dictionary, for all i
    //        - mostProbableNextWord[i] contains the index of the word most likely to follow word i
    // Postconditions:
    //        - The "most probable sentence" will be written to standard output as follows
    //            1) The sentence will start with <s>
    //            2) (Repeatedly) print the most likely word to follow the previous one until
    //            3) Continue generating text until one of the following two situations are encountered
    //               </s> is encountered, in which case a newline is printed and generation stops
    //               a previously printed word is encountered, in which case "!!!" is printed and generation stops
    // Notes:
    //        - Hint: consider using a java.util.HashSet to keep track of which words have already been printed
    public static void generateMostProbableText(HashMap<String,Integer> wordToIndex, String[] indexToWord, int[] mostProbableNextWord) {
        java.util.HashSet<Integer> hs = new java.util.HashSet<Integer>(); 
        int position = 0;           
        boolean inSet;
        
        while (!(indexToWord[position].equals("</s>"))){
            if( inSet = hs.contains(position)){
                System.out.print(indexToWord[position]);
                System.out.println("!!!");
                break; 
            }
            else{
                    hs.add(position);
                    System.out.print(indexToWord[position] + " " );
                    position = mostProbableNextWord[position];
            }
        }
                
        if(indexToWord[position].equals("</s>")){
            System.out.println("</s>");
        }   
        
        return;
    }

    // generateRandomText
    // Preconditions:
    //        - wordToIndex is populated such that each word w maps to its index i in the dictionary 
    //        - indexToWord[i] is the ith word in the dictionary, for all i
    //        - numberOfRandomSentences is the number of sentences to generate
    // Postconditions:
    //        - Each random sentence will be written to standard out as follows:
    //            1) The sentence will start with <s>
    //            2) (Repeatedly) randomly draw (and print) the next work according to the distribution 
    //               of words that follow the previous one
    //            3) Continue generating each sentence until </s> is encountered, then print a 
    //               newline and generation stops
    //        - numberOfRandomSentences sentences will be printed, total
    public static void generateRandomText(double[][] probabilities, HashMap<String,Integer> wordToIndex, String[] indexToWord, int numberOfRandomSentences) {
        java.util.Random generator = new java.util.Random(RANDOM_SEED);
        double distributionArray [] = new double [total];
        int sentenceCount = 1;
        int position = 0;
        
        while (sentenceCount <= numberOfRandomSentences){
            position = 0;
            while (!(indexToWord[position].equals("</s>"))){
                System.out.print(indexToWord[position] + " ");
                for (int i = 0; i<total; i++){
                    distributionArray [i] = probabilities[position][i];
                }
                              
                do{
                    position = drawFromDiscreteDistribution(distributionArray, generator);
                } while (position > total);            
            } 
            System.out.println("</s>"); 
            sentenceCount++;       
        }
               
        return;
    }

    // drawFromDiscreteDistribution
    // Preconditions:
    //        - distribution is an array of non-negative numbers that sum to one
    //          distribution[i] corresponds to the probability that the ith element is drawn
    //        - generator is a Random object, seeded with RANDOM_SEED
    // Postconditions:
    //        - a draw from the discrete distribution is performed, and the result index is returned
    // Notes:
    //        - The random object should be created and seeded only once, and then
    //          used to in each call to drawFromDiscreteDistribution to generate random numbers
    //        - This algorithm for drawing from a discrete distribution is not optimal
    public static int drawFromDiscreteDistribution(double[] distribution, Random generator) {
        double cumulativeSum = 0.0;        
        double draw = generator.nextDouble();
        
        int wordIndex = 0;
        while( cumulativeSum + distribution[wordIndex] < draw ) {
            cumulativeSum += distribution[wordIndex];
            wordIndex++;
        }
        return wordIndex;
    }
}


