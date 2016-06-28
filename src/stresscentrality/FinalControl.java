package stresscentrality;

import javafx.scene.media.SubtitleTrack;
import peersim.cdsim.CDProtocol;
import peersim.cdsim.CDSimulator;
import peersim.cdsim.CDState;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.CDScheduler;

public class FinalControl implements Control {

	private static final String REPORT_PROT = "protocolReport";
	private static final String COUNT_PROT = "protocolCount";


	private final int protocolReport;
	private final int protocolCount;

	public FinalControl(String prefix){

		protocolReport = Configuration.getInt(prefix+"."+REPORT_PROT);
		protocolCount = Configuration.getInt(prefix+"."+COUNT_PROT);
	}

	public boolean execute() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n["+CDState.getCycle()+"]-Final Control- {" );
		for(int n =0; n < Network.size(); n++) {
            //Node v = Network.get(n);
			ReportPhase rp = ((ReportPhase) Network.get(n).getProtocol(protocolReport));
			CountPhase cp = ((CountPhase) Network.get(n).getProtocol(protocolCount));
			sb.append("\n\t"+ n + " ->");


			long cc = 0L; // clossness centrality
			for (Node node: cp.nodeDistance.keySet()) {
				cc += cp.nodeDistance.get(node);

			}
			sb.append("\tCc:"+1+"/"+cc+" ");


			long gc = 0; // graph centrality
			Node nMax = null;
			for (Node node: cp.nodeDistance.keySet()) {
				if(cp.nodeDistance.get(node) > gc) {
					gc = cp.nodeDistance.get(node);
					nMax = node;
				}
			}
			if(nMax !=null)
			sb.append("\tGc:"+1+"/"+gc+" (from "+nMax.getID()+")");


            long sc = 0; // stress centrality
			long bc = 0;  // betwennws centrality

			for (int i = 0; i < Network.size(); i++) {
				for (int j = 0; j < Network.size(); j++) {
                    Node s = Network.get(i);
                    Node t = Network.get(j);
						if (rp.tableReport.contains(s, t)) {
							int sv = cp.spTable.get(s);
							int vt = cp.spTable.get(t);
							sc += sv * vt;
							bc += sc / rp.tableReport.getWeigth(s, t);
						}

				}
			}


			sb.append("\tCs:"+sc);
			sb.append("\tBs:"+bc);
			System.out.print(sb);
		}
		sb.append("}");

		return false;
	}

}
