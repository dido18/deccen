package stresscentrality;

import messages.NospMessage;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.*;

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
			int linkableID = FastConfig.getLinkable(1);
			Linkable linkable = (Linkable)Network.get(i).getProtocol(linkableID);
            //if(i==0)// only for testing starting with only few nodes
				if (linkable.degree() > 0){
					for(int j=0; j < linkable.degree(); j++){
					   Node peern = linkable.getNeighbor(j);
					   if(!peern.isUp()) return false;
						CountPhase scPeer = (CountPhase) peern.getProtocol(1);
						scPeer.receiveNOSP(new NospMessage(n,sp));
					}}
        	}
   	 //}
		return false;
    }
	
}
