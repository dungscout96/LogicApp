/**
 * Created by dung on 9/22/16.
 */
public class Node<K> {
    private Node<K> left, right;
    private K data;

    public Node() {

    }

    public Node(K data) {
        this.data = data;
    }

    public Node(Node<K> l, Node<K> r) {
        left = l;
        right = r;
    }

    public K getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node<K> left) {
        this.left = left;
    }

    public void setRight(Node<K> right) {
        this.right = right;
    }

    public String inOrder() {
        try {
            return inOrder(this);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    private String inOrder(Node<K> n) throws Exception{
        if (n.isLeaf()) {
            return data.toString();
        }
        else {
            return inOrder(left) + data.toString() + inOrder(right);
        }
    }

    public boolean isLeaf() throws Exception{
        if (getData() == null) {
            throw new Exception("Node is null");
        }
        else {
            return (getLeft() == null && getRight() == null);
        }
    }

}
