package messages;

import javafx.util.Pair;
import peersim.core.Node;

/**
 * Created by dido on 30/04/16.
 */
public class ReportMessage {
    public Node sender;
    public Node target;
    public int  weigth;

    public ReportMessage(Node s, Node t, int w){
        sender= s;
        target = t;
        weigth = w;
    }
}
