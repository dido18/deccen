package stresscentrality;

import messages.NospMsg;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.IdleProtocol;
import peersim.core.Network;
import peersim.core.Node;

public class StressInit implements Control{

	// Parameters
    // ------------------------------------------------------------------------
    private static final String PAR_PROT = "protocol";

    // ------------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------------
    /** Protocol identifier, obtained from config property {@link #PAR_PROT}. */
    private static int pid;

    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    public StressInit(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);
    }
    
    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------
    /**
     *  Sends the initial value of shortest path (sp) to all the neihbours.
     */
    public boolean execute() {
        
        int sp = 1;
     
        for (int i = 0; i < Network.size(); i++) { 
        	Node n = Network.get(i);
        	IdleProtocol linkable =  (IdleProtocol) Network.get(i).getProtocol(0);
        	if(i==1) {//  || i ==1){// only for testing starting with only one node
				if (linkable.degree() > 0){
					System.out.print("\nnode "+n.getID() +" ->");
					for(int j=0; j < linkable.degree(); j++){
					   Node peern = linkable.getNeighbor(j);//CommonState.r.nextInt(linkable.degree()));
					   if(!peern.isUp()) return false;
						CountPhase scPeer = (CountPhase) peern.getProtocol(1);
						scPeer.receiveNOSP(new NospMsg(n,sp));
						//scPeer.nospBuffer.put(n, sp);
					   //	System.out.print(" "+peern.getID()+",");
					}
					}
        	}
    }
		return false;
    }
	
}
