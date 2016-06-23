package messages;

import peersim.core.Node;

public class NospMessage {
	 /** If not null,
    this has to be answered, otherwise this is the answer. */
	public Node sender;   // ID(S): source node
	private int weight;  // number of shortest path from s to the currently sending node.
	// initial weigth is 1 : the number of shortest path froma node to itself is one.
	
	public NospMessage(Node sender, int sp){
		this.sender = sender;
		this.weight= sp;
	}
	
	public Node getSender(){
		return  sender;
	}
	

	public int getWeight() {
		return weight;
	}
	public void setWeight(int sp) {
		this.weight = sp;
	}
	
	public String toString(){
		if(sender != null)
			return "<"+ ":"+weight +">";
		else 
			return "<" +sender.getID() + ":"+weight +">";
		
	}
	
	
}
