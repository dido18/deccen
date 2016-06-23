package stresscentrality;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.IdleProtocol;
import peersim.core.Network;

public class ReportControl implements Control {

	private static final String PAR_PROT = "protocol";

	private final int protocolID;

	public ReportControl(String prefix){
		protocolID = Configuration.getInt(prefix+"."+PAR_PROT);
	}

	public boolean execute() {
		/*System.out.print("\n\n[ReportControl] node x: (source:target/distance(s,t){\n");
		for(int n =0; n < Network.size(); n++) {
			ReportPhase rp = ((ReportPhase) Network.get(n).getProtocol(protocolID));
			System.out.println("\tNode "+n+": " +rp.tableReport.toString());
		}
		System.out.print("}");*/
		return false;
	}

}
