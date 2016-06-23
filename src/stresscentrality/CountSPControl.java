package stresscentrality;

import peersim.cdsim.CDState;
import peersim.config.Configuration;
import peersim.core.*;

import javax.swing.text.html.CSS;

public class CountSPControl implements Control {

	private static final String PAR_PROT = "protocol";

	private final int protocolID;
	
	public CountSPControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	/**
	 * Calculate the number of shortest path and the distance between nodes.
	 * From the NOSP buffered messages received calculate the number of shortest path.
	 * Sum all the NOSP messages received from the same source in order to get the
	 * number of shortest path.
	 * @return
     */
	public boolean execute() {
		;
		System.out.print("\n\n["+ CDState.getCycle()+"]-CountSPControl - node x: (peern : num_sp $ distance) {");
		for(int i =0; i < Network.size(); i++){
			CountPhase sc = ((CountPhase) Network.get(i).getProtocol(protocolID));

            for(Node n :sc.nospBuffer.keySet()){
                if(!sc.spTable.containsKey(n))        //IF IT'S NOT A BACKFIRING MESSAGE
                    sc.spTable.put(n, sc.nospBuffer.get(n));
				if(!sc.nodeDistance.containsKey(n)) {  // update distance if not already received
					sc.nodeDistance.put(n, sc.cycle);
				}
            }
            System.out.print("\n \tNode "+i+ " -> ");
            for(Node n: sc.spTable.keySet()){
                System.out.print("("+n.getID()+" #sp="+sc.spTable.get(n)+" d="+sc.nodeDistance.get(n)+") ");
            }
		}
		System.out.print("\n}");

		return false;
	}

}
