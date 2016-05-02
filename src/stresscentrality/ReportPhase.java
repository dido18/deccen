package stresscentrality;

import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;
import peersim.core.Node;


import java.util.ArrayList;


public class ReportPhase implements CDProtocol{

    //N^2 data structure that store all the report messages received
    public TableReport tableReport;

    public ArrayList<ReportMessage> reportToSent;

    public ReportPhase(String prefix){
        init();
	}


  	public void nextCycle(Node node, int protocolID) {

        IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
        CountPhase cp = (CountPhase)node.getProtocol(1);
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
            /*//send Report message to peern (old version sent shortest path)
            for (Node s : cp.spTable.keySetSender()) {
                rp.receiveReport(new ReportMessage(s, node, cp.spTable.get(s)));
            }*/
        }


        }

    public void receiveReport(Node receiver, ReportMessage msg){

        CountPhase cp = (CountPhase)receiver.getProtocol(1);
        Node s = msg.sender;
        Node t = msg.target;
        if(cp.nodeDistance.containsKey(s) && cp.nodeDistance.containsKey(t)) {
            if (cp.nodeDistance.get(s) + cp.nodeDistance.get(t) == msg.weigth) { //contributin message  d(s,v)+d(v,t) == weigth msg
                //System.out.print("\nnode " + receiver.getID() + ": is contributing (" + s.getID() + " " + t.getID() + ")");
                tableReport.put(s, t, msg.weigth);
            }
            else{
             //  reportToSent.add(msg);
            }

        }

    }

	
    public void init(){
        tableReport = new TableReport();
        reportToSent = new ArrayList<>();
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
