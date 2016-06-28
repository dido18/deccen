package stresscentrality;

import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.IdleProtocol;
import peersim.core.Linkable;
import peersim.core.Node;



public class ReportPhase implements CDProtocol{

    private static final String COUNT_PROT = "protocol";
    private final int protocol;

    // N^2 data structure that store all the report messages received
    public TableReport tableReport;

    public long numReport =0L; //number of report message received

    public ReportPhase(String prefix){
        protocol = Configuration.getInt(prefix+"."+COUNT_PROT);
        init();
	}


  	public void nextCycle(Node node, int protocolID) {

        int linkableID = FastConfig.getLinkable(protocolID);
        Linkable linkable = (Linkable)node.getProtocol(linkableID);
        CountPhase cp = (CountPhase)node.getProtocol(protocol);  // for
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            ReportPhase rpPeern = (ReportPhase) peern.getProtocol(protocolID);
            for(Node s : cp.nodeDistance.keySet()) {
                ReportMessage msg = new ReportMessage(s, node, cp.nodeDistance.get(s));
                rpPeern.receiveReport(peern, msg);
            }
            for(Node n: tableReport.keySetSender()){
                try {
                    for(Node m : tableReport.getTargets(n)) {
                        ReportMessage msg = new ReportMessage(n, m, tableReport.getWeigth(n, m));
                        rpPeern.receiveReport(peern, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


        }

    public void receiveReport(Node receiver, ReportMessage msg){
        numReport++;
        CountPhase cp = (CountPhase)receiver.getProtocol(1);
        Node s = msg.sender;
        Node t = msg.target;
        if(cp.nodeDistance.containsKey(s) && cp.nodeDistance.containsKey(t)) {
            if (cp.nodeDistance.get(s) + cp.nodeDistance.get(t) == msg.weigth) { //contributin message  d(s,v)+d(v,t) == weigth msg
                //System.out.print("\nnode " + receiver.getID() + ": is contributing (" + s.getID() + " " + t.getID() + ")");
                tableReport.put(s, t, msg.weigth);
            }
        }

    }

	
    public void init(){
        tableReport = new TableReport();
        numReport =0L;
    }

    /**
	 * This is the default mechanism of peersim to create 
	 * copies of objects. To generate a new protocol,
	 * peersim will call this clone method.
	 */
	public Object clone()
	{
		ReportPhase p = null;
		try
		{ 
			p = (ReportPhase) super.clone();
			p.init();
		}
		catch( CloneNotSupportedException e ) {} // never happens
		return p;
	}






	

}
