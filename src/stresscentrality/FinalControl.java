package stresscentrality;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

public class FinalControl implements Control {
	// IN event based can be used to react to some events (incoming messages)

	// ------------------------------------------------------------------------
	// Parameters
	// ------------------------------------------------------------------------
	private static final String PAR_PROT = "protocol";
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private final int protocolID;

	public FinalControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	public boolean execute() {
		System.out.print("\nFinal Control {");
		for(int n =0; n < Network.size(); n++) {
            Node v = Network.get(n);
			ReportPhase rp = ((ReportPhase) Network.get(n).getProtocol(2));
            System.out.print(" \n SC (" + n + "):");
            int total = 0;
			for (int i = 0; i < Network.size(); i++) {
				for (int j = 0; j < Network.size(); j++) {
                    Node s = Network.get(i);
                    Node t = Network.get(j);
					if (rp.tableReport.contains(s,v) && rp.tableReport.contains(v,t)) {
                        int sv = rp.tableReport.getWeigth(s,v);
                        int vt = rp.tableReport.getWeigth(v,t);
                        total += sv*vt;
                        System.out.print("\t (" + s.getID()+ " " + v.getID() + "|"+ t.getID()+")/"+sv+" "+vt);
                        }
				}
			}
            System.out.print("\t TOTALE :  "+total);

            System.out.print(" Distance :  ");
            CountPhase cp = ((CountPhase) Network.get(n).getProtocol(1));
            for(Node node : cp.nodeDistance.keySet()){
                System.out.print( "("+node.getID()+ ":"+ +cp.nodeDistance.get(node)+")");
            }

		}
		System.out.print("}");

		return false;
	}

}
