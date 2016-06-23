package messages;

import javafx.util.Pair;
import peersim.core.Node;

/**
 * Created by dido on 30/04/16.
 */
public class ReportMessage {
    public Node sender;
    public Node target;
    public long  weigth;  //d(s,t) distance between sender and t

    public ReportMessage(Node s, Node t, long w){
        sender= s;
        target = t;
        weigth = w;
    }
}
