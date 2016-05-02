package stresscentrality;

import peersim.config.Configuration;
import peersim.core.*;

public class SwitchNospControl implements Control {
	// IN event based can be used to react to some events (incoming messages)
	
	// ------------------------------------------------------------------------
	// Parameters
	// ------------------------------------------------------------------------
	private static final String PAR_PROT = "protocol";
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private final int protocolID;
	
	public SwitchNospControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	/**
	 * Claculate the number of shortest path and calculate the distance between nodes.
	 * From the NOSP buffered messages received calculate the number of shortest path.
	 * Sum all the NOSP messages received fro the same source in orde to get the
	 * number of shortest path.
	 * @return
     */
	public boolean execute() {
		System.out.print("\n\nCount control  node: (peern:#sp) {");
		for(int i =0; i < Network.size(); i++){
			CountPhase sc = ((CountPhase) Network.get(i).getProtocol(protocolID));
            for(Node n :sc.nospBuffer.keySet()){
                if(!sc.spTable.containsKey(n)) //IT'S NOT A BACKFIRING MESSAGE
                    sc.spTable.put(n, sc.nospBuffer.get(n));
				if(!sc.nodeDistance.containsKey(n))
					sc.nodeDistance.put(n,sc.cycle);
            }
            System.out.print("\n \tNode "+i+ ": ");
            for(Node n: sc.spTable.keySet()){
                System.out.print("("+n.getID()+":"+sc.spTable.get(n)+")");
            }
		}
		System.out.print("\n}");
		return false;
	}

}
