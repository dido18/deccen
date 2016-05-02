package stresscentrality;

import messages.NospMsg;
import messages.ReportMessage;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.IdleProtocol;
import peersim.core.Network;
import peersim.core.Node;

public class ReportControl implements Control {
	// IN event based can be used to react to some events (incoming messages)

	// ------------------------------------------------------------------------
	// Parameters
	// ------------------------------------------------------------------------
	private static final String PAR_PROT = "protocol";
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private final int protocolID;

	public ReportControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	public boolean execute() {
		System.out.print("\nReport control, contributing message {\n");

		for(int n =0; n < Network.size(); n++) {
			ReportPhase rp = ((ReportPhase) Network.get(n).getProtocol(protocolID));
			IdleProtocol linkable =  (IdleProtocol) Network.get(n).getProtocol(0);
            //for(Node node: rp.tableReport.keySetSender()){
                System.out.println("\tNode"+n+"(" +rp.tableReport.toString()+ ")");
            //}
/*			for (int i = 0; i < Network.size(); i++) {
				for (int j = 0; j < Network.size(); j++) {
                    Node s = Network.get(i);
                    Node t = Network.get(j);
					if (rp.tableReport.contains(s,t)) {
                            System.out.print(" node " + n + ":");
                            System.out.print("(" + i + " " + j + ")/" + rp.tableReport.getWeigth(s,t) + ", ");
                        }
				}
			}*/

		}
		System.out.print("}");

		return false;
	}

}
