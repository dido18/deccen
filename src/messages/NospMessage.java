package messages;

import peersim.core.Node;

public class NospMessage {
	 /** If not null,
    this has to be answered, otherwise this is the answer. */
	public Node sender; 
	private int weight;  //shortest path
	
	public NospMessage(Node sender, int sp){
		this.sender = sender;
		this.weight=sp;
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
