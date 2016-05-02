package stresscentrality;

import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;
import peersim.core.Node;


import java.util.HashMap;


public class ReportPhase implements CDProtocol{

    //N^2 data structure that store all the report messages received
    public TableReport tableReport;

    public ReportPhase(String prefix){
        init();
	}


  	public void nextCycle(Node node, int protocolID) {

        IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        CountPhase cp = (CountPhase)node.getProtocol(1);
        for(int j=0; j < linkable.degree(); j++) {
            Node peern = linkable.getNeighbor(j);
            ReportPhase rp = (ReportPhase) peern.getProtocol(protocolID);
            //send NOSP message received precedently
            for (Node sender : cp.spTable.keySet()) {
                rp.receiveReport(new ReportMessage(sender, node, cp.spTable.get(sender)));
            }
        }


        }

    public void receiveReport(ReportMessage msg ){

        if(!tableReport.contains(msg.sender, msg.target))
            tableReport.put(msg.sender,msg.target,msg.weigth);
//        else


    }

	
    public void init(){
        tableReport = new TableReport();
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
