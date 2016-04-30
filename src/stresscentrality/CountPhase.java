package stresscentrality;

import java.util.ArrayList;
import java.util.HashMap;

import messages.NospMsg;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;

import peersim.core.Node;


public class CountPhase implements CDProtocol{

	public HashMap<Node, Integer> nospBuffer;
    public HashMap<Node,Integer> spTable;
	
	public CountPhase(String prefix){
		nospBuffer = new HashMap<>();
        spTable = new HashMap<>();
	}

    /**
     *
     * @param node   is the called node
     * @param protocolID    the protocol ID
     */
  	public void nextCycle(Node node, int protocolID) {
        //System.out.println("node "+ node.getID()+ " :"+nospBuffer.values().toString());
        IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            for (Node k : spTable.keySet()) {
                if (peern.getID() != k.getID()) {
                    CountPhase sc = (CountPhase) peern.getProtocol(protocolID);
                    sc.receiveNOSP(new NospMsg(k, spTable.get(k)));
                }
            }
        }

	}

    //receive msg of the count phase
    public void receiveNOSP(NospMsg msg ){//Node from, int shortestPath){
        //nospBuffer.add(msg);
        Node from = msg.getSender();
        if(nospBuffer.containsKey(from))
            nospBuffer.put(from, nospBuffer.get(from)+1);
        else
            nospBuffer.put(from, msg.getWeight());
    }


	
    public void init(){
        nospBuffer = new HashMap<>();
        spTable = new HashMap<>();
    }

    /**
	 * This is the default mechanism of peersim to create 
	 * copies of objects. To generate a new protocol,
	 * peersim will call this clone method.
	 */
	public Object clone()
	{
		CountPhase p = null;
		try
		{ 
			p = (CountPhase) super.clone();
			p.init();
		}
		catch( CloneNotSupportedException e ) {} // never happens
		return p;
	}






	

}
