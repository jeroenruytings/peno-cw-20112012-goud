package local;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import local.ActionLogDecoder.action_type;

public class Action {
	private static Queue<Action> actionsTaken = new LinkedList<Action>();
	public static Collection<Action> getActionsTaken() {
		return actionsTaken;
	}
	public action_type getType() {
		return type;
	}
	public void setType(action_type type) {
		this.type = type;
	}
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	private action_type type;
	private byte value;
	public Action(action_type type, byte value) {
		this.type=type;
		this.value=value;
		actionsTaken.add(this);
		// TODO Auto-generated constructor stub
	}

}
