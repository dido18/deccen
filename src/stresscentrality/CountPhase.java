package stresscentrality;

import java.util.HashMap;

import messages.NospMessage;
import peersim.cdsim.CDProtocol;
import peersim.config.FastConfig;
import peersim.core.IdleProtocol;

import peersim.core.Linkable;
import peersim.core.*;
import peersim.cdsim.CDState;


public class CountPhase implements CDProtocol{

	public HashMap<Node, Integer> nospBuffer;  // Number of NOSP message received with the same source ID(s) (sum of all mes received from the same source node)
    public HashMap<Node, Integer> spTable;     // store the number of sorthest path to any node.
    public HashMap<Node, Long> nodeDistance;   // store the distance of all the nodes in the network.
    public long cycle;                         // #number of cycles -> distance from the node when receive message

    public long numNOSP = 0L;  //for plotting and statistics

	public CountPhase(String prefix){
        init();
	}


    public void init(){
        nospBuffer = new HashMap<>();
        spTable = new HashMap<>();
        nodeDistance = new HashMap<>();
        cycle = 1;

    }


    /**
     *
     * @param node   is the called node
     * @param protocolID    the protocol ID
     */
  	public void nextCycle(Node node, int protocolID) {
        cycle += 1;
        int linkableID = FastConfig.getLinkable(protocolID);
        Linkable linkable = (Linkable)node.getProtocol(linkableID);
       // IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            for (Node k : spTable.keySet()) {
                if (peern.getID() != k.getID()) { // not send to itself
                    CountPhase sc = (CountPhase) peern.getProtocol(protocolID);
                    sc.receiveNOSP(new NospMessage(k, spTable.get(k)));
                }
            }
        }
	}

    /**
     *
     * @param msg
     */
    public void receiveNOSP(NospMessage msg){
        numNOSP++;
        Node from = msg.getSender();
        if(nospBuffer.containsKey(from))
            nospBuffer.put(from, nospBuffer.get(from)+1);
        else
            nospBuffer.put(from, msg.getWeight()); // weigth == number of shortest path from the source s
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
