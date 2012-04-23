package data.world;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import pacmansystem.ai.robot.Barcode;

import communicator.be.kuleuven.cs.peno.MessageSender;

import data.board.Board;
import data.board.Panel;
import data.enums.Orientation;



public class OwnRobotData extends RobotData
{
	private Board mergedBoard;
	
	public OwnRobotData(String name)
	{
		super(name);
		mergedBoard = new Board();
	}
	private String pointToString(Point p)
	{
		return p.x + "," + p.y;
	}
	public void discover(Point point,Panel panel)
	{
		try {
			MessageSender.getInstance().sendMessage(
					getName()
					+ " DISCOVER "
					+ pointToString(point)
					+ ""
					+ panel
					.bordersToString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(this.getBoard().getPanelAt(point) == null || !noBarcode(this.getBoard().getPanelAt(point)).equals(noBarcode(panel)))
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void barcode(Barcode barcode,Orientation orientation, Point point)
	{
		try {
			MessageSender.getInstance().sendMessage(
					getName() + " BARCODEAT "+pointToString(point)+" " + barcode.getValue() + " "
							+ orientationToString(orientation) + "\n");
			System.out.println("barcode: "+barcode.getValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(this.getBoard().getPanelAt(point)==null||this.getBoard().getPanelAt(point).getBarcode()==null||!this.getBoard().getPanelAt(point).getBarcode().equals(barcode))
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void plan(List<Point> plan)
	{
		try{
			String result = "";
			for (Point point : plan) {
				result += " " + pointToString(point);
			}
			MessageSender.getInstance().sendMessage(
					getName()
					+ " PLAN"
					+ result
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!getPlan().equals(plan))
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void position(Point point)
	{
		try{
			MessageSender.getInstance().sendMessage(
					getName()
					+ " POSITION "
					+ pointToString(point)
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!getPosition().equals(point))
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private String orientationToString(Orientation o){
		switch (o) {
		case NORTH:
			return "3";
		case SOUTH:
			return "1";
		case WEST:
			return "2";
		case EAST:
			return "4";

		}
		return null;
	}
	private Panel noBarcode(Panel panel)
	{
		Panel rv = new Panel();
		for(Orientation dir:Orientation.values())
			rv.setBorder(dir, panel.getWallState(dir));
		return rv;
	}
	
	public void pacman(Point point)
	{
		try{
			MessageSender.getInstance().sendMessage(
					getName()
					+ " PACMAN "
					+ pointToString(point)
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(this.getPacmanLastSighted()==null||!this.getPacmanLastSighted().equals(point))
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void pong(String name, String message){
		try{
			MessageSender.getInstance().sendMessage(
					getName()
					+ " PONG "
					+ name
					+ " "
					+ message
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ping(String bestemmeling, String uniekeString){
		try{
			MessageSender.getInstance().sendMessage(
					getName()
					+ " PING "
					+ bestemmeling
					+ " "
					+ uniekeString
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelPlan() {
		try{
			MessageSender.getInstance().sendMessage(
					getName()
					+ " CANCELPLAN"
					+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(getPlan().size() != 0)
		{
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Board getMergedBoard() {
		return mergedBoard;
	}
	
	public void setMergedBoard(Board newBoard) {
		mergedBoard = newBoard;
	}
}
