package stresscentrality;

import javafx.util.Pair;
import messages.NospMsg;
import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;
import peersim.core.Network;
import peersim.core.Node;

import java.util.ArrayList;
import java.util.HashMap;


public class ReportPhase implements CDProtocol{


    //n^2 table size
    public HashMap<Node, ArrayList<Integer>> reportTable;

	public ReportPhase(String prefix){
        reportTable = new HashMap<>();
        for (int i = 0; i < Network.size(); i++) {
            Node n = Network.get(i);
            reportTable.put(n, new ArrayList<>(Network.size()));
        }
	}

    /**
     *
     * @param node   is the called node
     * @param protocolID    the protocol ID
     */
  	public void nextCycle(Node node, int protocolID) {

        //System.out.println("node "+ node.getID()+ " :"+nospBuffer.values().toString());
        IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        CountPhase countPhase = (CountPhase)node.getProtocol(1);
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            for (Node k : countPhase.spTable.keySet()) {
                if (peern.getID() != k.getID()) {
                    ReportPhase rp = (ReportPhase) peern.getProtocol(protocolID);
                    rp.receiveReport(new ReportMessage(k, node, countPhase.spTable.get(k)));
                }
            }
        }

	}

    //receive msg of the count phase
    public void receiveReport(ReportMessage msg ){//Node from, int shortestPath){

        //TODO: msg getIndex isntead od getID() can be prblem ?
        Node s = msg.sender;
        Node t = msg.target;
        if(reportTable.get(msg.sender).get(0) == null)
            System.out.print("received new report masssage");
            //nospBuffer.put(from, nospBuffer.get(from)+1);
        else
            System.out.print("recieved already messag");
           // nospBuffer.put(from, msg.getWeight());

    }

	
    public void init(){
        reportTable = new HashMap<>();
    }

    /**
	 * This is the default mechanism of peersim to create 
	 * copies of objects. To generate a new protocol,
	 * peersim will call this clone method.
	 */
	public Object clone()
	{
		ReportPhase p = null;
		try
		{ 
			p = (ReportPhase) super.clone();
			p.init();
		}
		catch( CloneNotSupportedException e ) {} // never happens
		return p;
	}






	

}
