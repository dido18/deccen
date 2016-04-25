package stresscentrality;

import java.util.ArrayList;
import java.util.HashMap;

import messages.NospMsg;

import peersim.transport.Transport;
import peersim.cdsim.CDProtocol;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.IdleProtocol;
import peersim.core.Network;

import peersim.core.Node;
import peersim.edsim.EDProtocol;

public class StressCentrality implements EDProtocol{
	
	//EDprotocol allow process incoming messages

	private  final String PAR_INIT_SP ="sp"; 
	
	private int init_sp;
	private  HashMap<Node, Integer> spTable;
	private  int recFromNeighbours=0;
	
	public StressCentrality(String prefix){
		init_sp = Configuration.getInt(prefix+"."+PAR_INIT_SP);
		spTable = new HashMap<Node, Integer>();
	}

	/**
	* This is the standard method to define to process incoming messages.
	*/
	public void processEvent(Node node, int pid, Object event) {
		 NospMsg msg = (NospMsg)event;
		 
		 //if(aem.getSender()!= null){
		System.out.println("Node "+node.getID()+" from "+msg.getSender().getID()+" weight: "+msg.getWeight());
		IdleProtocol linkable =  (IdleProtocol) node.getProtocol(0);
		Node send = msg.getSender();
		
		if(linkable.contains(send)) //i'm the neighbour of the surce node. 
			spTable.put(send, msg.getWeight());
		else{
			if(!spTable.containsKey(send) && recFromNeighbours ==linkable.degree())
				spTable.put(send, recFromNeighbours);
				//must wait all the messages from the neighbour in orderto insert the sp,				
			}
		}
		
		for(int j=0; j < linkable.degree(); j++){
				Node rec = linkable.getNeighbor(j);
				((Transport)node.getProtocol(FastConfig.getTransport(pid))).
		                send(
		                		node,
		                		rec,
		                        new NospMsg(send,spTable.get(msg.sender)),
		                        pid);
		}
		
		 //}          
		// else{ //
		//value = (value + aem.value) / 2;
		//System.out.print(node.getID()+"Sent Init ");

	}

	

	/**
	 * This is the default mechanism of peersim to create 
	 * copies of objects. To generate a new protocol,
	 * peersim will call this clone method.
	 */
	public Object clone()
	{
		StressCentrality p = null;
		try
		{ 
			p = (StressCentrality) super.clone();
			//p.init();
		}
		catch( CloneNotSupportedException e ) {} // never happens
		return p;
	}




	

}
