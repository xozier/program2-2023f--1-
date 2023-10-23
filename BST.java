// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)

import java.util.ArrayList;

public class BST { // Binary Search Tree implementation

  protected boolean NOBSTified = false;
  protected boolean OBSTified = false;


  public ArrayList<Node> inOrder(Node root){
    ArrayList<Node> result = new ArrayList<>();
    inorderTraversal(root,result);
    return result;
  }

  public void inorderTraversal(Node root, ArrayList<Node> result){
    if(root != null){
      inorderTraversal(root.left, result);
      result.add(root);
//      System.out.println("= " + root.key);
      inorderTraversal(root.right, result);
    }
  }
  static int sum(ArrayList <Node> nodes, int i, int j){
    int s = 0;
    for (int k = i; k<= j; k++){
      if (k >= nodes.size())
        continue;
      s += nodes.get(k).freq;
    }
    return s;
  }

 
  //bst node class
  class Node{
    String key;
    int freq;
    int access;
    int height;
    Node left, right;
    int bf;

    //node contructor
    public Node(String data){
      this.key = data;
      left = right = null;
      freq = access = height = 0;
    }
  }

  Node root;

  //bst constructor
  public BST() { 
    root = null;
  }

  public int size() { 
    int res = recSize(root);
    return res;
  }
  public int recSize(Node root){
    if (root == null){
      return 0;
    }
    return 1 + recSize(root.right) + recSize(root.left);
  }

  public void insert(String key) {
    root = recInsert(root, key);
   }
  public Node recInsert(Node root, String key){
    if (root == null){
      root = new Node(key);
      root.freq = 1;
      return root;
    }
    //chnaged
    int cmp = key.compareTo(root.key);
    if (cmp==0){
      root.freq += 1;
      return root;
    }
    if (cmp>0){
      root.right = recInsert(root.right, key);
    }
    else{

      root.left = recInsert(root.left, key);
    }
    return root;
  } 
  
  public boolean find(String key) { 
    return recFind(root, key) != null;
}

public Node recFind(Node root, String key) {
    if (root == null) {
        return null;
    }
    root.access += 1;
    int cmp = key.compareTo(root.key);
    if (cmp > 0) {
        return recFind(root.right, key);
    } else if (cmp < 0) {
        return recFind(root.left, key);
    } else {
        return root; // Key found, return the node.
    }
}

  public int sumFreq() {
    int sum = recFreq(root);
    return sum;
   }
  public int recFreq(Node root){
    if (root == null){
      return 0;
    }

    int right = recFreq(root.right);
    int left = recFreq(root.left);
    return root.freq + left + right;
  }
  public int sumProbes() { 
    int sum = recProbes(root);
    return sum;
   }
  public int recProbes(Node root){
    if (root == null){
      return 0;
    }

    int right = recProbes(root.right);
    int left = recProbes(root.left);
    return root.access + left + right;
  }
  public int sumWeightedPath() { 
    int sum = recWeight(root, 1);
    return sum;
   }
  public int recWeight(Node root, int lvl){
    if (root == null){
      return 0;
    }

    int right = recWeight(root.right, lvl+1);
    int left = recWeight(root.left, lvl + 1);
    return root.freq * lvl + left + right;
  }
  
  public void resetCounters() {
    root = recReset(root);
   }
  public Node recReset(Node root){
    if (root == null){
      return root;
    }
    root.access = 0;
    root.freq = 0;
    recReset(root.right);
    recReset(root.left);
    return root;
  }
  public void nobst() {
    int treeSize = size();
    ArrayList<Node> nodes = new ArrayList<>();
    nodes = inOrder(root);
    root = recNobst(nodes, 0 , treeSize - 1);
    NOBSTified = true;
   }	// Set NOBSTified to true.

   public Node recNobst(ArrayList<Node> nodes, int i , int j){
    Node root;
    if (j<i){
      root = null;
    }
    else{
      int right = sum(nodes, i+1, j), left = 0;
      int min = Math.abs(right-left);
      int r = i;
      for (int k = i+1; k <= j; k++){
        
        left += nodes.get(k-1).freq;
        right -= nodes.get(k).freq;
        //System.out.printf("*left = %d , right = %d, root is %d \n", left, right, r);

        if (Math.abs(right-left)< min){
          min = Math.abs(right-left);
          r = k;
          //System.out.printf("left = %d , right = %d, root is %d\n", left, right, r);
        }
      }
      //System.out.printf("ENDleft = %d , right = %d, root is %d\n", left, right, r);
      root = nodes.get(r);
      root.right = recNobst(nodes,  r+1, j);
      root.left = recNobst(nodes,i, r-1);

    }
    return root;
   }
  



  public void obst() {
    //initialization
    int treeSize = size();
    ArrayList<Node> nodes = new ArrayList<>();
    nodes = inOrder(root);
    int[][] c = new int[treeSize][treeSize];
    int[][] r = new int[treeSize][treeSize]; //root
    int[][] w = new int[treeSize][treeSize]; // weight 
    for (int i = 0; i < treeSize; i++){
      //c[i][i] = 0;
      c[i][i] = nodes.get(i).freq;
      r[i][i] = i; 
      w[i][i] = nodes.get(i).freq;
    }
    // for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", w[i][j]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();
    // for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", c[i][j]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();
    // for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", r[i][j]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();

    for (int d = 2; d <= treeSize; d++){
      for (int i = 0; i < treeSize-d + 1; i++){
        int j = d+i - 1;
        c[i][j] = Integer.MAX_VALUE;
        w[i][j] = w[i][j-1] + nodes.get(j).freq;
        //System.out.printf(" root from %d to %d \n", ,);
        for (int l = r[i][j-1]; l <= r[i+1][j]; l++){
          int current = ((l > i) ? c[i][l - 1] : 0)+ ((l < j) ? c[l+ 1][j] : 0)+ w[i][j];
          if (current < c[i][j]){
              c[i][j] = current ;
              r[i][j] = l;
            }
              
          }
        }
      
      }
    //   System.out.println("here");

    //     for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", w[i][j]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();
    // for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", c[i][j]);
    //   }
    //   System.out.println();
    // }
    // System.out.println();
    // for (int i = 0; i <treeSize; i++){
    //   for (int j = 0; j < treeSize; j++){
    //     System.out.printf("%d ", r[i][j]);
    //   }
    //   System.out.println();
    // }
    root = buildOBST(0,treeSize-1, r, nodes);
    OBSTified = true;
    //print();
   }	// Set OBSTified to true.

  public Node buildOBST(int i, int j, int[][] r, ArrayList <Node> nodes){
    Node root;
    if (i >j){
      root = null;

    }
    else{
      int index = r[i][j];
      root = nodes.get(index);
      root.right = buildOBST(index+1, j, r, nodes);
      root.left = buildOBST(i, index -1, r, nodes);
    }
    return root;
  }
  public void print() { 
    traversal(root);
  }
  public void traversal (Node root){
    if (root != null){
    traversal(root.left);
    System.out.printf("[%s:%d:%d]\n", root.key, root.freq, root.access);
    traversal(root.right);
  }
  }

}




