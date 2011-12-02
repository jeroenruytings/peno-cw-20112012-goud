package local;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import local.ActionTreeNode;

public class ActionTreeNode implements TreeNode{

	public Action value;
	public BehaviorTreeNode parent;
	
	public ActionTreeNode(BehaviorTreeNode parent, Action act){
		value = act;
		this.parent = parent;
	}
	
	@Override
	public Enumeration<ActionTreeNode> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public TreeNode getChildAt(int arg0){
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode arg0) {
		return -1;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	
	public String toString()
	{
		return value.toString();
	}
	
	
}
