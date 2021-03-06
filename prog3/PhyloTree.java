/* 
 * PhyloTree.java
 *
 * Defines a phylogenetic tree, which is a strictly binary tree 
 * that represents inferred hierarchical relationships between species
 * 
 * There are weights along each edge; the weight from parent to left child
 * is the same as parent to right child.
 *
 * Students may only use functionality provided in the packages
 *     java.lang
 *     java.util 
 *     java.io
 *     
 * Use of any additional Java Class Library components is not permitted 
 * 
 * J.P.Sohal
 *
 */

  import java.io.*;
  import java.util.*;
  import java.lang.*;
 




public class PhyloTree {
    private PhyloTreeNode overallRoot;    // The actual root of the overall tree
    private int printingDepth;            // How many spaces to indent the highest 
                                        // depth node when printing

    // CONSTRUCTOR

    // PhyloTree
    // Pre-conditions:
    //        - speciesFile contains the path of a valid FASTA input file
    //        - printingDepth is a positive number
    // Post-conditions:
    //        - this.printingDepth has been set to printingDepth
    //        - A linked tree structure representing the inferred hierarchical
    //          species relationship has been created, and overallRoot points to
    //          the root of this tree
    // Notes:
    //        - A lot happens in this step!  See assignment description for details
    //          on the input format file and how to construct the tree
    //        - If you encounter a FileNotFoundException, print to standard error
    //          "Error: Unable to open file " + speciesFilename
    //          and exit with status (return code) 1
    //        - Most of this should be accomplished by calls to loadSpeciesFile and buildTree
    public PhyloTree(String speciesFile,int printingDepth) {
            this.printingDepth = printingDepth;
            Species [] speciesArray = loadSpeciesFile(speciesFile);
              
            buildTree(speciesArray);
    
            
        return;
    }

    // ACCESSORS

    // getOverallRoot
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the overall root
    public PhyloTreeNode getOverallRoot() {
        return null;
    }

    // toString 
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns a string representation of the tree
    // Notes:
    //    - See assignment description for proper format
    //    - Can be a simple wrapper around the following toString
    public String toString() {
        return "";
    }

    // toString 
    // Pre-conditions:
    //    - node points to the root of a tree you intend to print
    //    - weightedDepth is the sum of the edge weights from the
    //      overall root to the current root
    //    - maxDepth is the weighted depth of the overall tree
    // Post-conditions:
    //    - Returns a string representation of the tree
    // Notes:
    //    - See assignment description for proper format
    private String toString(PhyloTreeNode node, double weightedDepth, double maxDepth) {
        return "";
    }

    // toTreeString 
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns a string representation in tree format
    // Notes:
    //    - See assignment description for format details
    //    - Can be a simple wrapper around the following toTreeString
    public String toTreeString() {
        return "";
    }

    // toTreeString 
    // Pre-conditions:
    //    - node points to the root of a tree you intend to print
    // Post-conditions:
    //    - Returns a string representation in tree format
    // Notes:
    //    - See assignment description for proper format
    private String toTreeString(PhyloTreeNode node) {
        return "";
    }

    // getHeight
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the tree height as defined in class
    // Notes:
    //    - Can be a simple wrapper on nodeHeight
    public int getHeight() {
    
        //int height = new nodeHeigh();
        
        return 0;
    }

    // getWeightedHeight
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the sum of the edge weights along the
    //      "longest" (highest weight) path from the root
    //      to any leaf node.
    // Notes:
    //   - Can be a simple wrapper for weightedNodeHeight
    public double getWeightedHeight() {
        return 0.0;
    }

    // countAllSpecies
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns the number of species in the tree
    // Notes:
    //    - Non-terminals do not represent species
    //    - This functionality is provided for you elsewhere
    //      just call the appropriate method
    public int countAllSpecies() {
        return 0;
    }

    // getAllSpecies
    // Pre-conditions:
    //    - None
    // Post-conditions:
    //    - Returns an ArrayList containing all species in the tree
    // Notes:
    //    - Non-terminals do not represent species
    public java.util.ArrayList<Species> getAllSpecies() {
        return null;
    }

    // findTreeNodeByLabel
    // Pre-conditions:
    //    - label is the label of a tree node you intend to find
    //    - Assumes labels are unique in the tree
    // Post-conditions:
    //    - If found: returns the PhyloTreeNode with the specified label
    //    - If not found: returns null
    public PhyloTreeNode findTreeNodeByLabel(String label) {
        return null;
    }

    // findLeastCommonAncestor
    // Pre-conditions:
    //    - label1 and label2 are the labels of two species in the tree
    // Post-conditions:
    //    - If either node cannot be found: returns null
    //    - If both nodes can be found: returns the PhyloTreeNode of their
    //      common ancestor with the largest depth
    // Notes:
    //    - Can be a wrapper around the static findLeastCommonAncestor
     public PhyloTreeNode findLeastCommonAncestor(String label1, String label2) {
        return null;
    }
    
    // findEvolutionaryDistance
    // Pre-conditions:
    //    - label1 and label2 are the labels of two species in the tree
    // Post-conditions:
    //    - If either node cannot be found: returns POSITIVE_INFINITY
    //    - If both nodes can be found: returns the sum of the weights 
    //      along the paths from their least common ancestor to each of
    //      the two nodes
     public double findEvolutionaryDistance(String label1, String label2) {
        return 0.0;
    }

    // MODIFIER

    // buildTree
    // Pre-conditions:
    //    - species contains the set of species for which you want to infer
    //      a phylogenetic tree
    // Post-conditions:
    //    - A linked tree structure representing the inferred hierarchical
    //      species relationship has been created, and overallRoot points to
    //      the root of said tree
    // Notes:
    //    - A lot happens in this step!  See assignment description for details
    //      on how to construct the tree
    private void buildTree(Species[] species) {
         ArrayList<PhyloTreeNode> forest = new ArrayList<PhyloTreeNode>();
         MultiKeyMap<Double> distanceMap = new MultiKeyMap<Double>();
         PhyloTreeNode leftChild = null;
         PhyloTreeNode rightChild = null;
         PhyloTreeNode temp1 = null;
         PhyloTreeNode temp2 = null;;
         int speciesL = 1;
         int speciesR = 1;;
         double shortestD = 10000000.0;
         double leftNodeDistance = 0.0;
         double rightNodeDistance = 0.0;
         double distanceFrmNtree = 0.0;
         int o = 0;
         int n = 1;
         int r = 1;
         PhyloTreeNode parent = null;
        while(!(species[o] == null)){
            PhyloTreeNode node  = new PhyloTreeNode (null,species[o]);
            forest.add(node);
            o++;
        }
              
        for(int i = 0; i <forest.size(); i++){
            for(int j = 0; j<forest.size(); j++){
                if(!species[i].getName().equals(species[j].getName())){
                         double distance = Species.distance(species[i], species[j]);
                         distanceMap.put(species[i].getName(), species[j].getName(), distance);
                }
            }    
        }                
        
        
        while(forest.size() > 1){
            shortestD = 1000000000.0;
            for(int i = 0; i <forest.size(); i++){
                for(int j = 0; j<forest.size(); j++){
                    if(!forest.get(i).getLabel().equals(forest.get(j).getLabel())){    
                        System.out.println(forest.get(i).getLabel() + "      " +forest.get(j).getLabel() + " J ");
                        double distanceBNodes = distanceMap.get(forest.get(i).getLabel(),forest.get(j).getLabel());
                    
                        if(distanceBNodes < shortestD){
                                shortestD = distanceBNodes;
                                leftChild = forest.get(j);
                                rightChild = forest.get(i);
                        }    
                   }
                }   
            }
                        
    
            forest.remove(leftChild);
            temp1 = leftChild;                  
            forest.remove(rightChild);
            temp2 = rightChild;                 
            parent = new PhyloTreeNode(leftChild.getLabel()+"+"+rightChild.getLabel(),parent,leftChild,rightChild,shortestD);
            forest.add(parent);
             
            while(!temp1.isLeaf()){
                temp1 = temp1.getLeftChild();
                speciesL = speciesL + (n*2);
                n++;
            }    
        
            while(!temp2.isLeaf()){
                temp2 = temp2.getRightChild();
                speciesR = speciesR + (r*2);
                r++;
            }    
        
            for(int i = 0; i <forest.size()-1; i++){
                double leftDistance = distanceMap.get(leftChild.getLabel(),forest.get(i).getLabel());
                double rightDistance = distanceMap.get(rightChild.getLabel(),forest.get(i).getLabel());
                distanceFrmNtree = (speciesL/(speciesL + speciesR)) * leftNodeDistance + (speciesR/(speciesL + speciesR)) * rightNodeDistance;
                distanceMap.put(forest.get(i).getLabel(),parent.getLabel(),distanceFrmNtree);

            }   

      }
        
    
           
        return;
    }

    // STATIC

    // nodeDepthax
    // Pre-conditions:
    //    - node is null or the root of tree (possibly subtree)
    // Post-conditions:
    //    - If null: returns -1
    //    - Else: returns the depth of the node within the overall tree
    public static int nodeDepth(PhyloTreeNode node) {
   
             
            
    
    
    
    
    
    
    
        return -1;
    }

    // nodeHeight
    // Pre-conditions:
    //    - node is null or the root of tree (possibly subtree)
    // Post-conditions:
    //    - If null: returns -1
    //    - Else: returns the height subtree rooted at node
    public static int nodeHeight(PhyloTreeNode node) {
        if(node == null){
            return -1;
        }   
        else if(node.getLeftChild() == null){
            return 1 + nodeHeight(node.getRightChild());
        }
        else if(node.getRightChild() == null){
            return 1 + nodeHeight(node.getLeftChild());
        }        
        else{   
    
            return 1 + Math.max(nodeHeight(node.getLeftChild()), nodeHeight(node.getRightChild()));
        }
    }

    // weightedNodeHeight 
    // Pre-conditions:
    //    - node is null or the root of tree (possibly subtree)
    // Post-conditions:
    //    - If null: returns NEGATIVE_INFINITY
    //    - Else: returns the weighted height subtree rooted at node
    //     (i.e. the sum of the largest weight path from node
    //     to a leaf)
    public static double weightedNodeHeight(PhyloTreeNode node) {
        return 0;
    }

    // loadSpeciesFile
    // Pre-conditions:
    //    - filename contains the path of a valid FASTA input file
    // Post-conditions:
    //    - Creates and returns an array of species objects representing
    //      all valid species in the input file
    // Notes:
    //    - Species without names are skipped
    //    - See assignment description for details on the FASTA format
    public static Species[] loadSpeciesFile(String filename) {
        String name = null; 
        java.util.ArrayList<String> names = new ArrayList<String>();
        String [] sequence = null;
        Species [] speciesArray = new Species [500];
        String combinedS = new String();
        String[] splitWords = null;
        String sName = null;
        String previousName = new String();
        String finalS = null;
        String temp = null;
        int j = 0;
      try {Scanner input = new Scanner(new File(filename)); 
             
                while (input.hasNext()){
                        String token = input.next();

                        if (token.contains(">")){                          
                            splitWords = token.split("\\|");  if(splitWords.length == 7){
                                name = splitWords[6];
                                names.add(name);
                            }
                            else{
                                    name = "not included";
                            }                       
                       }
            
                       else{ 
                          if(splitWords.length == 7){
                              combinedS = combinedS + token;
                          }
                                
                             
                                     
                         if((!name.equals(previousName) && !name.equals("not included")) ||(!previousName.equals(" ") && !name.equals("not included"))){
                            sequence = combinedS.split("|");   
                            speciesArray[j] = new Species(name, sequence);
                            j++;
                            for(int i = 0; i<sequence.length; i++){

                            }  
                            combinedS = new String();
                        }  
                        else{              
                                if(previousName.equals(" ") && !name.equals("not included")){
                                        combinedS = combinedS;
                                }       
                        }
                                              
                         previousName = name; 
                    }                
                            
                                
               } 
               
            
         } catch(FileNotFoundException e ) {
               System.err.println("Error: unable to open file." + filename);
               System.exit(1);
         } catch(NoSuchElementException e ) {
               System.err.println("Error: Not able to parse file " + filename);
               System.exit(2);
         } finally {
                  
         }
       
    
     
    
    

    
    
    
        return speciesArray;
    }

    // getAllDescendantSpecies
    // Pre-conditions:
    //    - node points to a node in a phylogenetic tree structure
    //    - descendants is a non-null reference variable to an empty arraylist object
    // Post-conditions:
    //    - descendants is populated with all species in the subtree rooted at node
    //      in in-/pre-/post-order (they are equivalent here)
    private static void getAllDescendantSpecies(PhyloTreeNode node,java.util.ArrayList<Species> descendants) {
        return;
    }

    // findTreeNodeByLabel
    // Pre-conditions:
    //    - node points to a node in a phylogenetic tree structure
    //    - label is the label of a tree node that you intend to locate
    // Post-conditions:
    //    - If no node with the label exists in the subtree, return null
    //    - Else: return the PhyloTreeNode with the specified label 
    // Notes:
    //    - Assumes labels are unique in the tree
    private static PhyloTreeNode findTreeNodeByLabel(PhyloTreeNode node,String label) {
        PhyloTreeNode match = null;
        
        do{
           if(node.getLabel().equals(label)){
                match = node;
           }
           else{
                findTreeNodeByLabel(node.getLeftChild(),label);
           }  
        }while(!node.isLeaf());
         
              
        do{
           if(node.getLabel().equals(label)){
                match = node;
           }
           else{
                findTreeNodeByLabel(node.getRightChild(),label);
           }  
        }while(!node.isLeaf());
          
                    
               
               
    
        return match;
    }

    // findLeastCommonAncestor
    // Pre-conditions:
    //    - node1 and node2 point to nodes in the phylogenetic tree
    // Post-conditions:
    //    - If node1 or node2 are null, return null
    //    - Else: returns the PhyloTreeNode of their common ancestor 
    //      with the largest depth
     private static PhyloTreeNode findLeastCommonAncestor(PhyloTreeNode node1, PhyloTreeNode node2) {
        return null;
    }
}
