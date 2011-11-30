package local;


import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import local.ActionLogDecoder.action_type;
import local.BehaviourDecoder.behaviour_enum;

public class Action {
	private static Queue<Action> actionsTaken = new LinkedList<Action>();
	private static DefaultTreeModel tree = new DefaultTreeModel(new BehaviorTreeNode(null, behaviour_enum.GOSTRAIGTH),true);
	private static BehaviorTreeNode lastNode;
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
	private behaviour_enum invoker;
	public Action(behaviour_enum invoker,action_type type, byte value) {
		this.type=type;
		this.value=value;
		this.invoker = invoker;
		actionsTaken.add(this);
		// TODO Auto-generated constructor stub
	}
	
	public static TreeModel getTree(){
		return tree;
	}

	
	public void addAction(Action a){
		if ((lastNode == null) || (lastNode.getType() != invoker)){
			BehaviorTreeNode root = (BehaviorTreeNode) tree.getRoot();
			root.addChild(invoker);
		}
		else
		{
			lastNode.addChild(this);
		}
	}
	
	public String toString(){
		return type.toString() + " :" + value;
	}
	
	
}
