package Hw4;
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable <E>> {
    private static class Node<E>{
        public E data;
        public int priority;
        public Node<E> left;
        public Node<E> right;

        public Node(E data, int priority){
            //constructer for node initializes with given values and null pointers to left and right
            if(data == null){
                throw new IllegalArgumentException("data cannot be null");
            }
            this.data = data;
            this.priority = priority;
            left = null;
            right = null;
        }

        public Node<E> rotateRight(){
            //rotates the given node by storing and transfering node pointers and data fields
            E transData = this.data;
            int transPriority = this.priority;
            Node<E> transRNode = right;
            Node<E> transLNode = left.right;

            Node<E> root = new Node<E>(left.data, left.priority);
            this.data = left.data;
            this.priority = left.priority;

            root.left = left.left;
            left = root.left;

            root.right = new Node<E>(transData, transPriority);
            right = root.right;

            root.right.left = transLNode;
            right.left = root.right.left;

            root.right.right = transRNode;
            right.right = root.right.right;

    
            return root;
        }

        public Node<E> rotateLeft(){
            //rotates the given node by storing and transfering node pointers and data fields
            
            E transData = this.data;
            int transPriority = this.priority;
            Node<E> transLNode = left;
            Node<E> transRNode = right.left;

            Node<E> root = new Node<E>(right.data, right.priority);
            this.data = right.data;
            this.priority = right.priority;

            root.left = new Node<E>(transData, transPriority);
            left = root.left;

            root.left.left = transLNode;
            left.left = root.left.left;

            root.left.right = transRNode;
            left.right = root.left.right;

            root.right = right.right;
            right = root.right;
            return root;
        }
        
        public String toString() {
            //prints a pair of node key and priority
            String result = data.toString() + ", " + priority;
			return result;
		}
    }

    private Random priorityGenerator;
    private Node<E> root;

    public Treap(){
        //constructer of treap sets root as null, and creates random number gen
        root = null;
        priorityGenerator = new Random();

    }

    public Treap(long seed){
        /*constructer of treap sets root as null, 
        and creates random number gen with specified limit*/
        
        root = null;
        priorityGenerator = new Random(seed);
    }


    public  Node<E> reHeap(Stack<Treap.Node<E>> nodeStack, Stack<Integer> pathStack){
        /* 
         * takes in two stacks and recreates the treap based 
         * on the actual node and pathing it takes
         */

        Node<E> lastItem = nodeStack.peek();
        Node<E> resTreap = null;
        nodeStack.pop();
        while(!nodeStack.isEmpty()){
            if(lastItem.priority > nodeStack.peek().priority){ //last item has greater priority 
                if(pathStack.peek() > 0){//last  path was to the right
                    nodeStack.peek().right = lastItem;
                    lastItem = nodeStack.peek().rotateLeft();
                    resTreap = lastItem;
                    pathStack.pop();
                    nodeStack.pop();
                }
                else if(pathStack.peek() < 0){ //last path was to the left 
                    nodeStack.peek().left = lastItem;
                    lastItem = nodeStack.peek().rotateRight();
                    resTreap = lastItem;
                    pathStack.pop();
                    nodeStack.pop();
                }
            }
            else{// item in stack has greater priority
                if(pathStack.peek() > 0){
                    nodeStack.peek().right = lastItem;
                    while(nodeStack.size() != 1){
                        nodeStack.pop();
                    }
                    resTreap = nodeStack.peek();
                    return resTreap;                
                }
                else{
                    nodeStack.peek().left = lastItem;
                    while(nodeStack.size() != 1){
                        nodeStack.pop();
                    }
                    resTreap = nodeStack.lastElement();
                    return resTreap;
                }
            }
        }
        return resTreap;
    }
 
    boolean add(E key, int priority){
        /*
         * adds node based on key and priority, 
         * uses two stacks to keep track of passed nodes and direction
         */
        Node<E> track = root;
        Stack<Node<E>> helperStack = new Stack<>();
        Stack<Integer> pathStack = new Stack<>();

        if(root == null){
            root = new Node<E>(key, priority);
            return true;
        }
        else{
            int cmpRes = 0;
            while(track != null){
                cmpRes = key.compareTo(track.data);//compares added key to node keys
                if(cmpRes == 0){
                    return false;
                }
                if(cmpRes > 0){ //-1 key greater
                    helperStack.add(track);
                    pathStack.add(cmpRes);
                    track = track.right;
                }
                if(cmpRes < 0){
                    helperStack.add(track);
                    pathStack.add(cmpRes);
                    track = track.left;
                }
            }
            //track is now empty ready to add new value
            Node<E> lastEntry = helperStack.peek();
            if(cmpRes > 0){//if latest direction is to the right
                lastEntry.right = new Node<E>(key, priority);
                helperStack.add(lastEntry.right);
                reHeap(helperStack, pathStack);
                return true;
            }
            else if(cmpRes < 0){//if latest direction is to the left
                lastEntry.left = new Node<E>(key, priority);
                helperStack.add(lastEntry.left);
                reHeap(helperStack, pathStack);
                return true;
            }
            return false;
        }
    }

    boolean add(E key){
        //adds new value based off of random number gen
        int priority = priorityGenerator.nextInt();
        return add(key, priority);
    }

    public boolean isLeaf(Node<E> node) {
        //helper function, if given node is a leaf
		return (node.left == null && node.right == null);
	}

    boolean remove(E key){
        /*
         * finds indicated key, if key is not a list it rotates until it is
         * uses stacks to maintain the order of Treap
         */
        Stack<Node<E>> helperStack = new Stack<>();
        Stack<Integer> pathStack = new Stack<>();

        Node<E> track = root;

        int cmpRes = 0;
        while(track != null){
            cmpRes = key.compareTo(track.data);

            if(cmpRes > 0 ){
                helperStack.add(track);
                pathStack.add(cmpRes);
                track = track.right;
            }
            if(cmpRes < 0 ){
                helperStack.add(track);
                pathStack.add(cmpRes);
                track = track.left;
            }
            if (cmpRes == 0){
                break;
            }
        }
        //track is either null or indicated key
        if(track == null){
            return false;
        }
        
        if(isLeaf(track)){
            if(pathStack.peek() > 0){
                //helperStack.pop();
                helperStack.peek().right = null;
                while(helperStack.size() != 1){
                    helperStack.pop();
                }
                root = helperStack.peek();
                return true;
            }
            else{
                helperStack.peek().left = null;
                while(helperStack.size() != 1){
                    helperStack.pop();
                }
                root = helperStack.peek();
                return true;
            }
        }
        
        while(!isLeaf(track)){
            //maintains the order and pathing of Treap as it becomes a leaf
            if(track.left == null){
                if(pathStack.peek() < 0){
                    helperStack.peek().left = track.rotateLeft();
                    helperStack.add(helperStack.peek().left);
                    track = track.left;
                    pathStack.add(-1);
                }
                else{
                    helperStack.peek().right = track.rotateLeft();
                    helperStack.add(helperStack.peek().right);
                    track = track.left;
                    pathStack.add(-1);
                }
            }
            else if(track.right == null){
                if(pathStack.peek() < 0){
                    helperStack.peek().left = track.rotateRight();
                    helperStack.add(helperStack.peek().left);
                    track = track.right;
                    pathStack.add(1);
                }
                else{
                    helperStack.peek().right = track.rotateRight();
                    helperStack.add(helperStack.peek().right);
                    track = track.right;
                    pathStack.add(1);
                }
            }
            else if(track.left.priority > track.right.priority){
                if(pathStack.peek() < 0){
                    helperStack.peek().left = track.rotateRight();
                    helperStack.add(helperStack.peek().left);
                    track = track.right;
                    pathStack.add(1);
                }
                else{
                    helperStack.peek().right = track.rotateRight();
                    helperStack.add(helperStack.peek().right);
                    track = track.right;
                    pathStack.add(1);
                }
            }
            else{
                if(pathStack.peek() < 0){
                    helperStack.peek().left = track.rotateLeft();
                    helperStack.add(helperStack.peek().left);
                    track = track.left;
                    pathStack.add(-1);
                }
                else{
                    helperStack.peek().right = track.rotateLeft();
                    helperStack.add(helperStack.peek().right);
                    track = track.left;
                    pathStack.add(-1);
                }
            }
        }

        if(pathStack.peek() > 0){
            helperStack.peek().right = null;
            while(helperStack.size() != 1){
                helperStack.pop();
            }
            root = helperStack.peek();
            return true;
        }
        else{
            helperStack.peek().left = null;
            while(helperStack.size() != 1){
                helperStack.pop();
            }
            root = helperStack.peek();
            return true;
        }
    }

    public boolean find(E key){
        return find(this.root, key);
    }

    private boolean find(Node<E> root, E key){
        //returns true if indicated key exists  
        int cmpRes = 0;
        Node<E> track = root;
        while(track != null){
            cmpRes = key.compareTo(track.data);

            if(cmpRes == 0){
                return true;
            }
            if(cmpRes > 0){ //-1 key greater
                track = track.right;
            }
            if(cmpRes < 0){
                track = track.left;
            }
        }
        return false;
    }

    public String toString(Node<E> current, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<depth;i++) {
			sb.append("-");
		}
		if (current==null) {
			sb.append("null\n");
		} else {
			sb.append(current + "\n");
			sb.append(toString(current.left, depth+1)); //
			sb.append(toString(current.right,depth+1));
		}
		return sb.toString();
	}

    public String toString(){
        return toString(root, 0);
    }

    public static void main(String[] args){
    
        Treap<Integer> test = new Treap<Integer>();
        test.add(1, 50);
        test.add(2, 49);
        test.add(0, 100);
        test.add(5, 60);
        test.add(3, 40);
        test.add(4, 101);

        System.out.println(test);
        test.remove(3);
        System.out.println(test);
    }
}
