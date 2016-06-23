package stresscentrality;

import peersim.core.Node;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by dido on 02/05/16.
 */
public class TableReport {

    private HashMap<Node, HashMap<Node,Long>> table;


    public TableReport(){
        table = new HashMap<>();
    }

    public Long getWeigth(Node s, Node t) {
        Long weigth;
        if (table.containsKey(s))
            weigth = table.get(s).get(t);
        else
            weigth = 0L;
        return  weigth;
    }

    public Collection<Node> getTargets(Node sender) throws Exception {
        if(table.containsKey(sender))
            return table.get(sender).keySet();
        else
            throw new Exception("THe list is emty");
    }

    public void put( Node s, Node t, Long w){

        if(table.get(s) != null)
            table.get(s).put(t,w);
        else {
            HashMap<Node, Long> tTable = new HashMap<>();
            tTable.put(t,w);
            table.put(s, tTable);
        }
    }

    public boolean contains(Node s, Node t){
        boolean ret = false;
        if(table.containsKey(s)) {
            if(table.get(s).containsKey(t))
                ret = true;
        }
        return ret;
    }

    public Collection<Node> keySetSender(){
        return table.keySet();
    }


    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        //st.append("[");
        for(Node s: table.keySet()){
            for(Node t: table.get(s).keySet())
                st.append("("+s.getID()+":" +t.getID() +"/" + getWeigth(s,t) +")");
        }
        //st.append("]");
        return st.toString();
    }
}
