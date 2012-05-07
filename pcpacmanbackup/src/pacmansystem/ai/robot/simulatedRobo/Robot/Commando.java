package pacmansystem.ai.robot.simulatedRobo.Robot;


public class Commando {
	
	Action action;
	String reason;
	int argument;
	
	public Commando(Action action,int argument, String reason){
		this.action = action;
		this.reason = reason;
		this.argument = argument;
	}
	
	public Action getAction(){
		return action;
	}
	
	public String getReason(){
		return reason;
	}
	
	public int getArgument(){
		return argument;
	}
	
}
