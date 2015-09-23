/* 
 * Lattice.java
 *
 * Defines a new "Lattice" type, which is a directed acyclic graph that
 * compactly represents a very large space of speech recognition hypotheses
 *
 * Note that the Lattice type is immutable: after the fields are initialized
 * in the constructor, they cannot be modified.
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 *     
 * as well as the class java.math.BigInteger
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
 
  
 
 

public class Lattice {
    private String utteranceID;       // A unique ID for the sentence
    private int startIdx, endIdx;     // Indices of the special start and end tokens
    private int numNodes, numEdges;   // The number of nodes and edges, respectively
    private Edge[][] adjMatrix;       // Adjacency matrix representing the lattice
                                      //   Two dimensional array of Edge objects
                                      //   adjMatrix[i][j] == null means no edge (i,j)
    private double nodeTimes[];       // Stores the timestamp for each node

    // Constructor

    // Lattice
    // Preconditions:
    //     - latticeFilename contains the path of a valid lattice file
    // Post-conditions
    //     - Field id is set to the lattice's ID
    //     - Field startIdx contains the node number for the start node
    //     - Field endIdx contains the node number for the end node
    //     - Field numNodes contains the number of nodes in the lattice
    //     - Field numEdges contains the number of edges in the lattice
    //     - Field adjMatrix encodes the edges in the lattice:
    //        If an edge exists from node i to node j, adjMatrix[i][j] contains
    //        the address of an Edge object, which itself contains
    //           1) The edge's label (word)
    //           2) The edge's acoustic model score (amScore)
    //           3) The edge's language model score (lmScore)
    //        If no edge exists from node i to node j, adjMatrix[i][j] == null
    // Notes:
    //     - If you encounter a FileNotFoundException, print to standard error
    //         "Error: Unable to open file " + latticeFilename
    //       and exit with status (return code) 1
    //     - If you encounter a NoSuchElementException, print to standard error
    //         "Error: Not able to parse file " + latticeFilename
    //       and exit with status (return code) 2
    public Lattice(String latticeFilename) {
         int a = 0;
         
         try {Scanner input = new Scanner(new File(latticeFilename));       
                while (input.hasNext()){
                        String token = input.next();
                        if (token.equals("id")){
                            utteranceID = input.next();
                        } else if (token.equals("start")){
                            startIdx = Integer.parseInt(input.next());
                        } else if (token.equals("end")){
                            endIdx = Integer.parseInt(input.next());
                        } else if (token.equals("numNodes")){
                            numNodes = Integer.parseInt(input.next());
                            nodeTimes = new double [numNodes];
                        } else if (token.equals("numEdges")){
                            numEdges = Integer.parseInt(input.next());
                            adjMatrix = new Edge [numNodes] [numNodes];
                            for (int i = 0; i<numNodes; i++){
                                for (int j = 0; j <numNodes; j++){
                                    adjMatrix[i][j] = null;
                                 }
                            }     
                        } else if (token.equals("node")){
                            String node = input.next();
                            nodeTimes[a] = Double.parseDouble(input.next());
                            a++;
                        } else if (token.equals("edge")){
                                int i = Integer.parseInt(input.next());
                                int j = Integer.parseInt(input.next());
                                String label = input.next();
                                int amScore = Integer.parseInt(input.next());
                                int lmScore = Integer.parseInt(input.next());                                
                                adjMatrix[i][j] = new Edge(label, amScore, lmScore);                                  
                        }            
                } 
         } catch(FileNotFoundException e ) {
               System.err.println("Error: unable to open file." + latticeFilename);
               System.exit(1);
         } catch(NoSuchElementException e ) {
               System.err.println("Error: Not able to parse file" + latticeFilename);
               System.exit(2);
         } finally {
                  
         }
         return;
    }
    
    // Accessors 

    // getUtteranceID
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the utterance ID
    public String getUtteranceID() {
        return utteranceID;
    }

    // getNumNodes
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the number of nodes in the lattice
    public int getNumNodes() {
        return numNodes;
    }

    // getNumEdges
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the number of edges in the lattice
    public int getNumEdges() {
        return numEdges;
    }

    // toString
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Constructs and returns a string that is identical to the contents
    //      of the lattice file used in the constructor
    // Notes:
    //    - Do not store the input string verbatim: reconstruct it on they fly
    //      from the class's fields
    //    - toString simply returns a string, it should not print anything itself
    // Hints:
    //    - java.text.DecimalFormat("0.00").format(x) returns the string
    //      representation of floating point value x with two decimal places
    public String toString() {
        String s = new String();
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjMatrix[i][j] != null) {
                    s = (s + "edge " + i + " " + j + " " + adjMatrix[i][j].getLabel() + " " + adjMatrix[i][j].getAmScore() + " " + adjMatrix[i][j].getLmScore() + "\n");
                }
            }
        }
        String t = new String();
        for (int i = 0; i <numNodes; i++) {
            t = (t + "node " + i + " " + (new java.text.DecimalFormat("0.00").format(nodeTimes[i])) + "\n");
        }
        return ("id " + utteranceID + "\n" + "start " + startIdx + "\n" + "end " + endIdx + "\n" + "numNodes " + numNodes + "\n" + "numEdges " + numEdges + "\n" +  t + s);
    }

    // decode
    // Pre-conditions:
    //    - lmScale specifies how much lmScore should be weighted
    //        the overall weight for an edge is amScale + lmScale * lmScore
    // Post-conditions:
    //    - A new Hypothesis object is return that contains the shortest path
    //      (aka most probable path) from the startIdx to the endIdx
    // Hints:
    //    - You can create a new empty Hypothesis object and then
    //      repeatedly call Hypothesis's addWord method to add the words and 
    //      weights, but this needs to be done in order (first to last word)
    //      Backtracking will give you words in reverse order.
    //    - java.lang.Double.POSITIVE_INFINITY represents positive infinity
    public Hypothesis decode(double lmScale) {
        int [] topSort = topologicalSort();
        double [] costArray = new double [numNodes];
        Hypothesis newH = new Hypothesis();
        int [] parentArray = new int [numNodes];
        ArrayList <Integer> finalList = new ArrayList<Integer>();
        for(int i = 0; i < numNodes; i++){
            costArray[i] = java.lang.Double.POSITIVE_INFINITY;
        }
        costArray[startIdx] = 0.0;
        
        for(int n : topSort){
            for(int i = 0; i < numNodes; i++){
                if(adjMatrix[i][n] != null && (adjMatrix[i][n].getCombinedScore(lmScale) + costArray[i]) < costArray[n]){
                    costArray[n] = adjMatrix[i][n].getCombinedScore(lmScale) + costArray[i];
                    parentArray[n] = i;
                }
            }
        }
        
        int node = endIdx;
                
        while(node != startIdx){                //creates a list where the number infront of num "x" is num xs parent so nodes are in correct order
            finalList.add(0,node);
            node = parentArray[node];
        }    
        int i = 0;
        while(!finalList.isEmpty()){
            newH.addWord(adjMatrix[i][finalList.get(0)].getLabel(), costArray[finalList.get(0)]);       // creates hypothesis using the correctly sorted list created in previous loop
            i = finalList.get(0);
            finalList.remove(0);
        }   
        
        return  newH;
    }
    
    // topologicalSort
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - A new int[] is returned with a topological sort of the nodes
    //      For example, the 0'th element of the returned array has no 
    //      incoming edges.  More generally, the node in the i'th element 
    //      has no incoming edges from nodes in the i+1'th or later elements
    public int[] topologicalSort() {
        int inDegrees = 0;
        int [] inDegreeArray = new int [numNodes]; 
        LinkedList<Integer> sortList = new LinkedList<Integer>(); 
        LinkedList<Integer> resultList = new LinkedList<Integer>(); 
        int [] result = new int [numNodes];
        
        for (int i = 0; i < numNodes; i++){
            for (int j = 0; j < numNodes; j++){
                if (adjMatrix[j][i] != (null)) {
                    inDegrees +=1;
                }
            }
            inDegreeArray[i] = inDegrees;
            inDegrees = 0;
        }
        
        for(int m = 0; m < inDegreeArray.length; m++){          
            if(inDegreeArray[m] == 0){
                sortList.add(m);
            }
        }
    
        int k = 0;
        int n = 0;
        while (!sortList.isEmpty()){
            n = sortList.remove(0);             
            result[k] = n;                      
            resultList.add(n);                  // created a result linkedlist because Array.conatains method wasnt working for us 
            k++;  
            for(int e = 0; e < numNodes; e++){
                if(adjMatrix[n][e] != (null)) {      
                    inDegreeArray[e]--;
                }
                if((inDegreeArray[e] == 0 && !resultList.contains(e)) && !sortList.contains(e)){
                    sortList.add(e);
                }
            }   
        }              
        
        int sum = 0;
        for(int i : inDegreeArray){
            sum+= i;
        }
        if(sum > 0){
            System.out.println(" lattice has cycle!");
            System.exit(3);        
        }            
    
        return result;
    }
    

    // countAllPaths - [OPTIONAL EXTRA CREDIT]
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the total number of distinct paths from startIdx to endIdx
    // Hints:
    //    - The straightforward recursive traversal is prohibitively slow
    //    - This can be solved efficiently using something similar to the 
    //        shortest path algorithm used in decode
    //        Instead of min'ing scores over the incoming edges, you'll want to 
    //        do some other operation...
    public java.math.BigInteger countAllPaths() {
        /*int [] topSort = topologicalSort();
        java.math.BigInteger j = java.math.BigInteger.ZERO;
        java.math.BigInteger bigInt1 = new java.math.BigInteger("1");
        java.math.BigInteger outDegree = java.math.BigInteger.ZERO;
        java.math.BigInteger numps = java.math.BigInteger.ZERO;
        for(int n: topSort){
            for(int i = 0; i < numNodes; i++){
                if(adjMatrix[n][i] != null){
                    outDegree = outDegree.add(bigInt1);
                }   
            }
            if((outDegree.add(j)).compareTo((j.add(bigInt1))) > 0){
                    j = outDegree.add(j);
            }
            //outDegree = java.math.BigInteger.ZERO;     
        }
    
        */return null;
    }

    // getLatticeDensity
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the lattice density, which is defined to be:
    //      (# of non -silence- words) / (# seconds from start to end index)
    public double getLatticeDensity() {
        double numWords = 0.0;
        double latticeDensity = 0.0;                              
        for (int i = 0; i < numNodes; i++){
            for (int j = 0; j< numNodes; j++){    
                if (adjMatrix[i][j] != (null)) {
                    if  (!(adjMatrix[i][j].getLabel().equals("-silence-"))){                
                        numWords = numWords + 1.0;   
                    }
                }
            }
        latticeDensity = numWords/(nodeTimes[endIdx] - nodeTimes[startIdx]);
        }
        
        return latticeDensity;
    }

    // writeAsDot - write lattice in dot format
    // Pre-conditions:
    //    - dotFilename is the name of the intended output file
    // Post-conditions:
    //    - The lattice is written in the specified dot format to dotFilename
    // Notes:
    //    - See the assignment description for the exact formatting to use
    //    - For context on the dot format, see    
    //        - http://en.wikipedia.org/wiki/DOT_%28graph_description_language%29
    //        - http://www.graphviz.org/pdf/dotguide.pdf
    public void writeAsDot(String dotFilename) {
        
        java.io.PrintStream output = null;
     
     try{  
            output = new java.io.PrintStream(dotFilename);
            
            output.println("digraph g {");
            output.println("    rankdir=\"LR\"");
            for (int i = 0; i<numNodes; i++){
                for (int j = 0; j <numNodes; j++){
                    if (adjMatrix[i][j] != null){                    
                    output.print(i);
                    output.print(" -> ");
                    output.print(j);
                    output.println("  " + "[label = "  + "\"" + adjMatrix[i][j].getLabel() + "\"" + "]");                                    
                    }
                }   
            }    
            output.print("}");   
    } catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: Unable to open file." + dotFilename);
               System.exit(1);
    } finally {
    }
        
        return;
    }

    // saveAsFile - write in the simplified lattice format (same as input format)
    // Pre-conditions:
    //    - latticeOutputFilename is the name of the intended output file
    // Post-conditions:
    //    - The lattice's toString() representation is written to the output file
    // Note:
    //    - This output file should be identical to the original input .lattice file
    public void saveAsFile(String latticeOutputFilename) {
    
        PrintStream output = null;
        try{  
                output = new PrintStream(latticeOutputFilename);
                output.print(toString());
        } catch( java.io.FileNotFoundException e ) {
               System.err.println("Error: Unable to open file.");
               System.exit(1);
        } finally {
        }
       
            return;
    }
}
