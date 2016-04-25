package stresscentrality;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.IdleProtocol;
import peersim.core.Linkable;
import peersim.core.Network;

public class StressControl implements Control {
	// IN event based can be used to react to some events (incoming messages)
	
	// ------------------------------------------------------------------------
	// Parameters
	// ------------------------------------------------------------------------
	private static final String PAR_PROT = "protocol";
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private final int protocolID;
	
	public StressControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	public boolean execute() {
		System.out.println("EXECUTE control");
		for(int i =0; i < Network.size(); i++){
			//StressCentrality sc = ((StressCentrality) Network.get(i).getProtocol(protocolID));
			//System.out.println(Network.get(i));
			IdleProtocol l = (IdleProtocol)Network.get(i).getProtocol(0);
			System.out.print("\nNode "+i+ " neighbour :");
			for(int j=0; j <l.degree();j++)
				System.out.print(l.getNeighbor(j).getID()+", ");
			
		}
		return false;
	}

}
