import java.io.*;
import java.util.*;

class TNode {
  String data;
  // 0 -- non visited
  // 1 -- visited
  int isVisited = 0;
  public List<TNode> children = new ArrayList<TNode>();
  public TNode parent;
  public List<TNode> adjacentNodes = new ArrayList<TNode>();
  public List<TNode> getAdjacentNodes() {
    return this.adjacentNodes;    
  }
    
  /**
   * Gets the non visited adjacent nodes.
   */
  public List<TNode> getNonVisitedAdjacentNodes() {
    List<TNode> nonVisitedAdjacentNodes = new ArrayList<TNode>();
    for (TNode n : this.adjacentNodes) {
      if(n.isVisited == 0) {
        nonVisitedAdjacentNodes.add(n);      
      }      
    }
    return nonVisitedAdjacentNodes;
  }  
    
}

public class Solution {
    
    public static Map<String, TNode> nodes = new HashMap<String, TNode>();
    public static String root = new String();
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        String noOfEdgesVertices = in.nextLine();
        int noOfEdges = Integer.parseInt(noOfEdgesVertices.split(" ")[1]);
        
        // Iterate over all the edges and build the adjacent matrix.
        // Also add each node to the nodes variable.
        for(int i=0; i<noOfEdges; i++) {
          String edge = in.nextLine();
          String src = edge.split(" ")[0];
          String dest = edge.split(" ")[1];
          
          //Assigining the root string.  
          if(i == 0) {
            root = src;      
          }
            
          // Check if the src and dest is aldready in nodes Map.
          // If not create new ones and add to the nodes map.
          if(!nodes.containsKey(src)) {
            TNode tempNode = new TNode();
            tempNode.data = src;
            nodes.put(src, tempNode);  
          }
          
          if(!nodes.containsKey(dest)) {
            TNode tempNode = new TNode();
            tempNode.data = dest;
            nodes.put(dest, tempNode);    
          }
          
          // Create the adjacency relation ship between two nodes.
          nodes.get(src).getAdjacentNodes().add(nodes.get(dest));
          nodes.get(dest).getAdjacentNodes().add(nodes.get(src)); 
        }
        buildTreeUsingBreadthFirstSearch();
    }
    
    
    /**
     * Builds the tree using breadth first search.
     */
    public static void buildTreeUsingBreadthFirstSearch() {
      //Build Tree using breadth first search.
      
        
      List<TNode> queue = new ArrayList<TNode>();
      
      // Add root to the queue.
      queue.add(nodes.get(root));
        
      // Iterate until all nodes are visited.
      while(!(queue.isEmpty())) {
        TNode presentNode = queue.remove(0);
        
        // Modifying the node.
        // Assigining the isVisited value.
        // Assigning the children to the present node.
        // Assinging the parent to all the children.
        // Rolling up to the root.
        presentNode.isVisited = 1;
        List<TNode> childNodes = presentNode.getNonVisitedAdjacentNodes();
            
        presentNode.children.addAll(childNodes);
        for (TNode node : childNodes) {
          node.parent = presentNode;    
        }
        rollup(presentNode);
        queue.addAll(childNodes);
      }
      
      int noOfBreaks = 0;
      for(Map.Entry<String, TNode> node : nodes.entrySet()){
        TNode tempNode = node.getValue();
        int noOfChildren = tempNode.children.size();
        //If an node is not root and its children are odd, then increment the no of breaks.
        if(noOfChildren%2 != 0 && !tempNode.data.equals(root)) {
          noOfBreaks++;    
        }
        //System.out.println(node.getValue().children.size());
      }
      System.out.println(noOfBreaks);
      
    }
    
    /**
     * Adds the its child nodes to all its parents
     */
    public static void rollup(TNode node) {
      List<TNode> childNodes = node.children;
      TNode tempNode = node;
      while(!(tempNode.data.equals(root))) {
        tempNode = tempNode.parent;
        tempNode.children.addAll(childNodes);
      }      
    }
}