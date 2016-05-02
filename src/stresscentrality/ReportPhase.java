package stresscentrality;

import messages.ReportMessage;
import peersim.cdsim.CDProtocol;
import peersim.core.IdleProtocol;
import peersim.core.Node;


import java.util.ArrayList;
import java.util.HashMap;


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
            ReportPhase rp = (ReportPhase) peern.getProtocol(protocolID);
            //CountPhase cp = (CountPhase) peern.getProtocol(protocolID);
            for(Node s : cp.nodeDistance.keySet()) {
                ReportMessage msg = new ReportMessage(s, node, cp.nodeDistance.get(s));
                rp.receiveReport(peern, msg);
            }

            /*//send Report message to peern (old version sent shortesta path)
            for (Node s : cp.spTable.keySet()) {
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
                System.out.print("\nnode " + receiver.getID() + ": is contributing (" + s.getID() + " " + t.getID() + ")");
                tableReport.put(s, t, msg.weigth);
            }
            else{
               // reportToSent.add(msg);
            }
            /*else{
                IdleProtocol linkable =  (IdleProtocol) receiver.getProtocol(0);
                System.out.print(" sent broabacart");
                for(int j=0; j < linkable.degree(); j++) {
                    Node peern = linkable.getNeighbor(j);
                    ReportPhase rp = (ReportPhase) peern.getProtocol(2);// ReportPhase
                    rp.receiveReport(peern, msg);
                }

                }
*/

        }
            /*
        if(!tableReport.contains(msg.sender, msg.target))
            tableReport.put(msg.sender,msg.target,msg.weigth);*/
       /*
        if (tableReport.contains(s,v) && rp.tableReport.contains(v,t)) {
            int sv = rp.tableReport.getWeigth(s,v);
            int vt = rp.tableReport.getWeigth(v,t);
            total += sv*vt;
            System.out.print("\t (" + s.getID()+ " " + v.getID() + "|"+ t.getID()+")/"+sv+" "+vt);
*/


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
