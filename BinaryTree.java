package TestTree;



import java.io.IOException;
import java.util.*;

/**
 * @ClassName BinaryTree
 * @Description 二叉树的相关操作
 * @Auther danni
 * @Date 2019/9/14 14:48]
 * @Version 1.0
 **/

public class BinaryTree {

    //先序遍历递归构建二叉树
    public static Node buildBirnaryTree01(Node root)  {
        Scanner scanner=new Scanner(System.in);
        char c=scanner.nextLine().charAt(0);

        if (c == '#') {
            root = null;
        } else {
            root = new Node();
            root.setVal(c);
            root.left=buildBirnaryTree01(root.left);//java中没有指针的思想，所以需要利用返回值将父节点和子节点的关系连接在一起。
            root.right=buildBirnaryTree01(root.right);
        }
        return root;
    }
    private static class RV{
        private Node root;
        private int used;

        private RV(Node root,int used){
            this.root=root;
            this.used=used;
        }
    }
    //先序递归构建二叉树
    public static RV  buildBinaryTree03(char[] ch){
        if(ch[0]=='#'){
            return new RV(null,1);
        }
        if(ch.length==0){
            return new RV(null,0);
        }
        RV leftReturn=buildBinaryTree03(Arrays.copyOfRange(ch,1,ch.length+1));
        RV rightReturn=buildBinaryTree03(Arrays.copyOfRange(ch,1+leftReturn.used,ch.length));
        Node root= new Node (ch[0]);
        root.left=leftReturn.root;
        root.right=rightReturn.root;
        return new RV(root,1+leftReturn.used+rightReturn.used);
    }
    //先序遍历迭代构建二叉树
    public static Node buildBinaryTree02(Node root){
        Scanner scanner=new Scanner(System.in);
        Stack<Node> stack=new Stack<>();
        Node temp=root;
        System.out.println("请输入先序遍历二叉树序列");
        String str="abd###c#ef###";
        char[] ch=str.toCharArray();
        int i=0;
        while(i<ch.length){
            if(ch[i]!='#'){
                if(root==null){
                    root=new Node();
                    root.setVal(ch[i]);
                    stack.push(root);
                    temp=root;
                }else {
                    temp.left=new Node(ch[i]);
                    temp=temp.left;
                    stack.push(temp);
                }

            }else{
                temp=stack.pop();
                if(ch[i+1]!='#'){
                    i++;
                    temp.right=new Node(ch[i]);
                    temp=temp.right;
                    stack.push(temp);
                }
            }
            i++;
            if(stack.empty()){
                break;
            }
        }
       return root;
    }
    //递归实现先序遍历二叉树
    public void preorder(Node root){
        if(root==null){
            return;
        }
        System.out.print(root);
        preorder(root.left);
        preorder(root.right);
    }
    //迭代先序遍历二叉树
    public void preorder1(Node root){
        if(root==null){
            return;
        }
        Stack<Node> stack=new Stack();
        Stack<Node> rootnode=new Stack<>();
        stack.push(root);
        rootnode.push(root);
        while((!rootnode.empty())){
            if(stack.empty()){
                stack.push(rootnode.pop().right);
            }
            Node temp=stack.pop();
            System.out.print(temp);
           if((temp.left!=null||temp.right!=null)&&(!temp.equals(root))){
               rootnode.push(temp);
           }
            if(temp.left!=null){
                stack.push(temp.left);
            }
            if(temp.left==null&&temp.right==null){
                temp=rootnode.pop();
                if(temp.right!=null){
                    stack.push(temp.right);
                }
            }
        }
    }
    //迭代先序遍历二叉树
    public void preorder2(Node root){
        if(root==null){
            return;
        }
        Stack<Node> stack=new Stack();
        while((root!=null)||(!stack.empty())){
            if(root!=null){
                System.out.print(root);
                stack.push(root);
                root=root.left;
            }else{
                root=stack.pop();
                root=root.right;
            }

        }
    }
    //递归实现中序遍历二叉树
    public void inorder(Node root){
        if(root==null){
            return;
        }
        inorder(root.left);
        System.out.print(root);
        inorder(root.right);
    }
    //迭代中序遍历二叉树
    public void inorder1(Node root){
        if(root==null){
            return;
        }
        Stack<Node> stack=new Stack<>();
        while((root!=null)||(!stack.empty())){
            if(root!=null){
                stack.push(root);
                root=root.left;
            }else{
                root=stack.pop();
                System.out.print(root);
                root=root.right;
            }
        }
    }
    //迭代实现后序遍历二叉树
    public void postorder(Node root){
        if(root==null){
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root);
    }
    //迭代后序遍历二叉树
    public void postorder1(Node root){
        if(root==null){
            return;
        }
       Stack<Node> stack=new Stack<>();
        Node cur=root;
        Node last=null;//上次被遍历完的结点
        while(cur!=null||(!stack.empty())){
            while(cur!=null){
                stack.push(cur);
                cur=cur.left;
            }
            Node top=stack.peek();
            if(top.right==null||top.right==last){
                System.out.print(top);
                stack.pop();
                last=top;
            }else{
                cur=top.right;
            }
        }
    }
    //判断一颗树是否为另一颗树的子树
    public boolean HusSubtree(Node s,Node t){
        boolean result=false;
       if(s!=null&&t!=null){
           if(s.getVal()==t.getVal()){
               result=isSameTree(s,t);
           }
           if(!result){
               result=HusSubtree(s.left,t);
           }
           if(!result){
               result=HusSubtree(s.right,t);
           }
       }
       return result;
    }
    //返回树的最大深度
    public int getLength(Node root){
        if(root==null){
            return 0;
        }
        int left=getLength(root.left);
        int right=getLength(root.right);
        return Math.max(left+1,right+1);
    }
    //判断一颗树是否为平衡二叉树
    public boolean isBanlaced(Node root){
        if(root==null){
            return true;
        }
        if(!(isBanlaced(root.left))){
            return false;
        }
        if(!(isBanlaced(root.right))){
            return false;
        }
        int len=this.getLength(root.left)-this.getLength(root.right);
        if(Math.abs(len)<=1){
            return true;
        }
        return false;
    }
    //遍历思路求结点个数
    static int size=0;
    public  int getSize1(Node root){
        if(root==null){
            return 0;
        }
        size++;
       getSize1(root.left);
       getSize1(root.right);
        return size;
    }
    //子问题思路求结点个数
    public int getSize2(Node root){
        if(root==null){
            return 0;
        }
        int left=getSize2(root.left);
        int right=getSize2(root.right);
        return left+right+1;
    }
    //遍历思路求叶子结点个数
    static  int leafsize;
    public int getLeafsize1(Node root){
        if(root==null){
            return 0;
        }
        if(root.left==null&&root.right==null){
            leafsize++;
        }
        getLeafsize1(root.left);
        getLeafsize1(root.right);
        return leafsize;
    }
    //子问题思路求叶子结点个数
    public int getLeafsize2(Node root){
        if(root==null){
            return 0;
        }
        if(root.right==null&&root.left==null){
            return 1;
        }
        return getLeafsize2(root.left)+getLeafsize2(root.right);
    }
    //子问题思路求第k层叶子结点个数
    public int getLevelsize(Node root,int k){
        if(root==null){
            return 0;
        }
        if(k==1){
            return 1;
        }
        return getLevelsize(root.left,k-1)+getLevelsize(root.right,k-1);
    }
    //递归思路将树的结点添加到list中
    static List<Character> list=new ArrayList<>();
    public void listTree1(Node root){
        if(root!=null){
            list.add(root.getVal());
            listTree1(root.left);
            listTree1((root.right));
        }
    }
    //子问题思路将树中的结点添加到list中；
    public List<Character> listTree2(Node root) {
        List<Character> prelist = new ArrayList<>();
        if (root == null) {
            return prelist;
        }
        prelist.add(root.getVal());
        prelist.addAll(listTree2(root.left));
        prelist.addAll( listTree2(root.right));
        return prelist;
    }
    public List<Character> listTree3(Node root) {
        List<Character> prelist = new ArrayList<>();
        if (root == null) {
            return null;
        }
        Stack<Node> stack=new Stack<>();
        while((root!=null)||(!stack.empty())){
            if(root!=null){
                prelist.add(root.getVal());
                stack.push(root);
                root=root.left;
            }else{
                root=stack.pop();
                root=root.right;
            }
        }
        return prelist;
    }
    public List<Character> listTree4(Node root) {
        List<Character> inlist = new ArrayList<>();
        if (root == null) {
            return null;
        }
        Stack<Node> stack=new Stack<>();
        while((root!=null)||(!stack.empty())){
            if(root!=null){
                stack.push(root);
                root=root.left;
            }else{
                root=stack.pop();
                inlist.add(root.getVal());
                root=root.right;
            }
        }
        return inlist;
    }

    //递归遍历查找树中是否存在指定结点，存在返回该结点，否则返回null
   public Node findl(Node root,int val){
        if(root==null){
            return null;
        }
        if((Character)root.getVal()==val) {
            return root;
        }
        Node nodel=findl(root.left,val);
        if(nodel!=null){
            return nodel;
        }
        Node noder=findl(root.right,val);
        if(noder!=null){
            return noder;
        }
        return null;
    }
    //递归遍历查找树中是否存在指定结点，存在返回true，否则返回false
    public boolean find(Node root,int val){
        if(root==null){
            return false;
        }
        if((Character)root.getVal()==val){
            return false;
        }
        boolean b=find(root.left,val);
        if(b!=false){
            return true;
        }
        return find(root.right,val);
    }
    //判断两颗树是否互为镜像
    public boolean isMirrorTree(Node p,Node q){
        if(p==null&&q==null){
            return true;
        }
        if(p==null||q==null){
            return true;
        }
        return p.getVal()==q.getVal()&&isMirrorTree(p.left,q.right)&& isMirrorTree(p.right,q.left);
    }
    //判断两颗数是否相同
    public boolean isSameTree(Node p,Node q){
        if(p==null&&q==null){
            return true;
        }
        if(q==null||q==null){
            return false;
        }
        return p.getVal()==q.getVal()&&isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);
    }
    //后序遍历
    public  List<Character> postOrderTraversal(Node root){
        List<Character> list=new ArrayList<>();
        if(root==null){
            return list;
        }
        list.addAll(postOrderTraversal(root.left));
        list.addAll(postOrderTraversal(root.right));
        list.add(root.getVal());
        return list;
    }
    //判断t是不是s的一颗子树
    public boolean isSubTree(Node s,Node t){
        boolean result=false;
        if(s!=null&&t!=null){
            if(s.getVal()==t.getVal()){
                result=isSameTree(s,t);
            }
            if(!result){
                result=isSubTree(s.left,t);
            }
            if(!result){
                result=isSubTree(s.right,t);
            }
        }
        return result;
   }
   public boolean search(Node root,Node p){
        if(root==null){
            return false;
        }
        if(root==p){
            return true;
        }
        if(search(root.left,p)){
            return true;
        }
        return search(root.right,p);
   }
   //找出两个结点最近公共结点
    public Node lowestCommonAncestor(Node root,Node p,Node q){
        if(p==root||q==root){
            return root;
        }
        while(search(root.left,p)&&search(root.left,q)){
            root=lowestCommonAncestor(root.left,p,q);
        }
        while(search(root.right,p)&&search(root.right,q)){
            root=lowestCommonAncestor(root.right,p,q);
        }
        return root;
    }
   //层序遍历二叉树
    public void levelOrderTraversal(Node root){
       if(root==null){
           return;
       }
       List<Node> queuen=new ArrayList();
       Node temp=root;
       queuen.add(temp);
       while(!queuen.isEmpty()){
           Node nodel=queuen.remove(0);
           System.out.print(nodel);
          if(nodel.left!=null) {
                 queuen.add(nodel.left);
           }
           if(nodel.right!=null){
                  queuen.add(nodel.right);
               }
           }
       }
       //判断一颗树是否为完全二叉树
    public boolean isComTree(Node root){
        if(root==null){
            return true ;
        }
        List<Node> queuen=new LinkedList<>();
        Node temp=root;
        queuen.add(temp);
        int size=0;
        boolean result=true;
        while(result){
            Node node1=((LinkedList<Node>) queuen).pollFirst();
            if(node1==null){
                break;
            }
            queuen.add(node1.left);
            queuen.add(node1.right);
        }
        while(queuen.isEmpty()){
            Node node1=((LinkedList<Node>) queuen).pollFirst();
            if(node1!=null){
                return false;
            }
        }
        return result;
    }
       private class Rnode{
        Node root;
        int level;

           public Rnode(Node root, int level) {
               this.root = root;
               this.level = level;
           }
       }
       //层序遍历二叉树并打印层数。
    public void levelOrderTraversal01(Node root){
        if(root==null){
            return;
        }
        Queue<Rnode> queuen=new LinkedList<>();
        Rnode temp=new Rnode(root,1);
        queuen.add(temp);
        while(!queuen.isEmpty()){
            Rnode nodel=queuen.poll();
            System.out.print(nodel.level+":"+nodel.root);
            if(nodel.root.left!=null) {
                Rnode left=new Rnode(nodel.root.left,nodel.level+1);
                queuen.add(left);
            }
            if(nodel.root.right!=null){
                Rnode right=new Rnode(nodel.root.right,nodel.level+1);
                queuen.add(right);
            }
        }
    }
    //层序遍历，第一层从左往右遍历，第二层从右往左遍历
    public void levelOrderTraversal03(Node root){
        if(root==null){
            return;
        }
        Queue<Rnode> queuen=new LinkedList<>();
        Rnode temp=new Rnode(root,1);
        queuen.add(temp);
        while(!queuen.isEmpty()){
            Rnode nodel=queuen.peek();
            if( nodel.level%2==1){
                System.out.print(queuen.poll().root);
            }else if(nodel.level%2==0){
                nodel=((LinkedList<Rnode>) queuen).pollLast();
                System.out.print(nodel.root);
                if(nodel.root.right!=null){
                    Rnode right=new Rnode(nodel.root.right,nodel.level+1);
                    ((LinkedList<Rnode>) queuen).addFirst(right);
                }
                if(nodel.root.left!=null) {
                    Rnode left=new Rnode(nodel.root.left,nodel.level+1);
                    ((LinkedList<Rnode>) queuen).addFirst(left);
                }
                continue;
            }
            if(nodel.root.left!=null) {
                Rnode left=new Rnode(nodel.root.left,nodel.level+1);
                queuen.add(left);
            }
            if(nodel.root.right!=null){
                Rnode right=new Rnode(nodel.root.right,nodel.level+1);
                queuen.add(right);
            }
        }
    }
    //层序遍历将每层的结点数据存储在list中
    public  List<List<Rnode>> levelOrderTraversal02(Node root){
        if(root==null){
            return null;
        }
        List<List<Rnode>>  list=new ArrayList<>();
        Queue<Rnode> queuen=new LinkedList<>();
        Rnode temp=new Rnode(root,0);
        queuen.add(temp);
        while(!queuen.isEmpty()){
            Rnode nodel=queuen.poll();
            if(list.size()==nodel.level){
                list.add(new ArrayList<>());
            }
            List l=list.get(nodel.level);
            l.add(nodel.root.getVal());
            if(nodel.root.left!=null) {
                Rnode left=new Rnode(nodel.root.left,nodel.level+1);
                queuen.add(left);
            }
            if(nodel.root.right!=null){
                Rnode right=new Rnode(nodel.root.right,nodel.level+1);
                queuen.add(right);
            }
        }
        return list;
    }
    //根据先序和中序构建二叉树
    public Node buildBinarytree01(char[] preorder,char[] inorder){
        if(preorder.length==0){
            return null;
        }
        char rootVal=preorder[0];
        int leftCount=0;
        for(leftCount=0;leftCount<inorder.length;leftCount++){
            if(inorder[leftCount]==rootVal){
                break;
            }
        }
        Node root=new Node(rootVal);
        char[] preorderLeft= Arrays.copyOfRange(preorder,1,leftCount+1);
        char[] inorderLeft=Arrays.copyOfRange(inorder,0,leftCount-1);
        root.left=buildBinarytree01(preorderLeft,inorderLeft);
        char[] preorderRight= Arrays.copyOfRange(preorder,leftCount+2,preorder.length-1);
        char[] inorderRight=Arrays.copyOfRange(inorder,leftCount+1,inorder.length-1);
        root.right=buildBinarytree01(preorderRight,inorderRight);
        return root;
    }
    //根据后序和中序构建二叉树
    public Node buildBinarytree02(char[] postorder,char[] inorder){

        if(postorder.length==0){
            return null;
        }
        char rootVal=postorder[postorder.length-1];
        int leftCount=0;
        for(leftCount=0;leftCount<inorder.length;leftCount++){
            if(inorder[leftCount]==rootVal){
                break;
            }
        }
        Node root=new Node(rootVal);
        char[] postorderLeft= Arrays.copyOfRange(postorder,0,leftCount);
        char[] inorderLeft=Arrays.copyOfRange(inorder,0,leftCount);
        root.left=buildBinarytree02(postorderLeft,inorderLeft);
        char[] postorderRight= Arrays.copyOfRange(postorder,leftCount,postorder.length-1);
        char[] inorderRight=Arrays.copyOfRange(inorder,leftCount+1,inorder.length);
        root.right=buildBinarytree02(postorderLeft,inorderLeft);
        return root;
    }
    public static void main(String[] args) {
        BinaryTree bt=new BinaryTree();
        Node root=null;
        /*root=bt.buildBirnaryTree01(root);
        System.out.println("先序遍历");
        bt.preorder(root);
        System.out.println();
        System.out.println("中序遍历");
        bt.inorder(root);
        System.out.println();
        System.out.println("后序遍历");
        bt.postorder(root);*/

        root=buildBinaryTree02(root);
        //List<Character> list=bt.listTree4(root);
        //System.out.println(list);
       // bt.inorder(root);
        //System.out.println();
        //bt.inorder1(root);
        bt.levelOrderTraversal(root);
        System.out.println();
        bt.levelOrderTraversal03(root);
       // bt.preorder(root);
        //System.out.println();
        //bt.preorder1(root);
        //System.out.println();
        //bt.preorder2(root);
        //bt.levelOrderTraversal(root);
        //System.out.println();
        //System.out.println( bt.levelOrderTraversal02(root));
        /*System.out.println(bt.getLength(root));
        size=0;
        System.out.println(bt.getSize1(root));
        System.out.println(bt.getSize2(root));
        leafsize=0;
        System.out.println(bt.getLeafsize1(root));
        System.out.println(bt.getLeafsize2(root));
        System.out.println(bt.getLevelsize(root,3));
        bt.listTree1(root);
        System.out.println(list);
        System.out.println(bt.listTree2(root));*/

        /*System.out.println("先序遍历");
        bt.preorder(root);
        System.out.println();
        System.out.println("中序遍历");
        bt.inorder(root);
        System.out.println();
        System.out.println("后序遍历");
        bt.postorder(root);*/

    }
}
