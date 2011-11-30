package local;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;
import local.BehaviourDecoder.behaviour_enum;

public class BehaviorTreeNode implements TreeNode {
	ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	private BehaviorTreeNode parent;
	private behaviour_enum type;
	
	public BehaviorTreeNode(BehaviorTreeNode parent, behaviour_enum type){
		this.parent = parent;
		this.type = type;
	}
	
	public behaviour_enum getType(){
		return type;
	}
	
	
	@Override
	public Enumeration<TreeNode> children() {
		return Collections.enumeration(children);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount(){
		return children.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	public BehaviorTreeNode addChild(behaviour_enum type){
		BehaviorTreeNode newNode = new BehaviorTreeNode(this, type); 
		children.add(newNode);
		return newNode;
	}
	
	public void addChild(Action act){
		children.add(new ActionTreeNode(this, act));
	}
	
	@Override
	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	public String toString(){
		return type.toString();
	}
	
}
