// AVL Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
  
public class AVL extends BST
{
 
  public AVL() { 
    super();
    //System.out.println(root);
  }
  


  public Node rotateLeft(Node root){
    //System.out.println("rl");
    //right right
    //System.out.println("rl on " + root.key);
    Node temp = root.right; 
    root.right = temp.left;
    temp.left = root;
    update(root);
    update(temp);
    return temp;
    
  }
  public Node rotateLeftRight(Node root ){
    //System.out.println("rlr");
    root.left = rotateLeft(root.left);
    return rotateRight(root);
  }
  public Node rotateRightLeft(Node root ){
    //System.out.println("*rrl");
    root.right = rotateRight(root.right);
    return rotateLeft(root);
  }
  public Node rotateRight(Node root ){
    //System.out.println("rr");
    //left left
    Node temp = root.left;
    root.left = temp.right;
    temp.right = root;
    update(root);
    update(temp);
    return temp;
  }
 
  @Override
  public void insert(String key) {
    //System.out.println("take new");
    root = recNode(root, key);
   }
  public Node recNode(Node root, String key){
    
  
    if (root == null){
      root = new Node(key);
      root.freq = 1;
      //System.out.println(root.key+ " made a node") ;
      //System.out.println(root.height);
      return root;
    }
    
    int cmp = key.compareTo(root.key);
    if (cmp == 0){
      root.freq += 1;
      return root;
    }
    if(cmp > 0){
      //System.out.println("bigger");
      root.right = recNode(root.right, key);
      }
    else{
      //System.out.println("smaller");
      root.left = recNode(root.left, key);
    }
    update(root);
    return balance(root);
  }
  public Node balance(Node root) {
    if (root.bf == -2) {
      if (root.left.bf <= 0) {
        root = rotateRight(root);
      } 
      else {
        root = rotateLeftRight(root);
      }

    }
    else if (root.bf == +2) {
      if (root.right.bf >= 0) {
        root = rotateLeft(root);
      } 
      else {
        root = rotateRightLeft(root);
      }
    }

    return root;
  }
  public void update(Node root){
    int lh = (root.left != null) ? root.left.height : -1;
    int rh = (root.right != null) ? root.right.height : -1;
    root.height = 1+ Math.max(rh, lh);
    root.bf = rh - lh;
  }
 
}

