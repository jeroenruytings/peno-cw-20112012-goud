package Robot;


public class Commando {
	
	Action action;
	String reason;
	
	public Commando(Action action, String reason){
		this.action = action;
		this.reason = reason;
	}
	
	public Action getAction(){
		return action;
	}
	
	public String getReason(){
		return reason;
	}
	
}
