/**
 * Created by dung on 9/23/16.
 */
public class SentenceNode<K> extends Node<K>{
    public SentenceNode(K data) {
        super(data);
    }

    public SentenceNode(SentenceNode<K> left, SentenceNode<K> right) {
        super(left, right);
    }

    public SentenceNode<K> makeSentence(K a, K conn, K b) {
        SentenceNode<K> nn = new SentenceNode<K>(conn);
        nn.setLeft(new SentenceNode<K>(a));
        nn.setRight(new SentenceNode<K>(b));
        return nn;
    }

}
