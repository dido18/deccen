package stresscentrality;

import java.util.ArrayList;
import java.util.HashMap;

import messages.NospMsg;
import peersim.Simulator;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;

import peersim.core.Network;
import peersim.core.Node;


public class CountPhase implements CDProtocol{

	public HashMap<Node, Integer> nospBuffer;  // bugffer og the recieved NOSP msg, in order to sum from the same source id.
    public HashMap<Node,Integer> spTable;   //store the number of sorthest path to any node
    public HashMap<Node, Long> nodeDistance; //store the distance of all the nodes in the network
    public long cycle;  //# number of cycles -> distance from the node
	
	public CountPhase(String prefix){
        init();
	}

    /**
     *
     * @param node   is the called node
     * @param protocolID    the protocol ID
     */
  	public void nextCycle(Node node, int protocolID) {
        cycle += 1;
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

    /**
     * PPut the received NOSP messsages in  buffer. The SwitchNOSP controll will
     * calculate the shortest path starting from the buffered messages.
     * @param msg
     */
    public void receiveNOSP(NospMsg msg ){
        Node from = msg.getSender();
        if(nospBuffer.containsKey(from))
            nospBuffer.put(from, nospBuffer.get(from)+1);
        else
            nospBuffer.put(from, msg.getWeight());
    }


	
    public void init(){
        nospBuffer = new HashMap<>();
        spTable = new HashMap<>();
        nodeDistance = new HashMap<>();
        cycle =1;
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
