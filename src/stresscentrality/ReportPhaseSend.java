package stresscentrality;

import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;
import peersim.core.Node;


public class ReportPhaseSend implements CDProtocol{


    public ReportPhaseSend(String prefix){

	}

  	public void nextCycle(Node node, int protocolID) {

        IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        ReportPhase myrp = (ReportPhase) node.getProtocol(2);
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            ReportPhase rp = (ReportPhase) peern.getProtocol(2);
            //send REPORT phase
            try {
                for (Node sender : myrp.tableReport.keySet())
                    for (Node target : myrp.tableReport.getTargets(sender)) {
                        rp.receiveReport(new ReportMessage(sender, target, myrp.tableReport.getWeigth(sender, target)));
                    }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        }


    /**
	 * This is the default mechanism of peersim to create 
	 * copies of objects. To generate a new protocol,
	 * peersim will call this clone method.
	 */
	public Object clone()
	{
		ReportPhaseSend p = null;
		try
		{ 
			p = (ReportPhaseSend) super.clone();
		}
		catch( CloneNotSupportedException e ) {} // never happens
		return p;
	}






	

}
