package stresscentrality;

import messages.NospMsg;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.IdleProtocol;
import peersim.core.Linkable;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

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
        	if(i==0){// only for testing starting with only one node
        	if (linkable.degree() > 0){
        		System.out.print("\nnode "+n.getID() +" sent to node");
        		for(int j=0; j < linkable.degree(); j++){
	               Node peern = linkable.getNeighbor(j);//CommonState.r.nextInt(linkable.degree()));
	               if(!peern.isUp()) return false;
		           ((Transport)n.getProtocol(FastConfig.getTransport(pid))).
		            send(	n,
		                    peern,
		                    new NospMsg(n, sp),
		                    pid);
		           System.out.print(" "+peern.getID()+",");
	        	}
        		}
        	}
    }
		return false;
    }
	
}
