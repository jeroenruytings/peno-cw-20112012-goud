package util.world;

import java.awt.Point;
import java.io.IOException;

import communicator.be.kuleuven.cs.peno.MessageSender;

import pacmansystem.ai.robot.Barcode;

import util.board.Panel;
import util.enums.Direction;
import util.enums.Orientation;


public class OwnRobotData extends RobotData
{
	public OwnRobotData(String name)
	{
		super(name);
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
					getName() + " BARCODEAT "+pointToString(point)+"" + barcode.getValue() + " "
							+ orientationToString(orientation) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(this.getBoard().getPanelAt(getPosition())==null||this.getBoard().getPanelAt(getPosition()).getBarcode()==null||!this.getBoard().getPanelAt(getPosition()).getBarcode().equals(barcode))
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
	public void plan(Point[] plan)
	{
		
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
			return "1";
		case SOUTH:
			return "3";
		case WEST:
			return "4";
		case EAST:
			return "2";

		}
		return null;
	}
	private Panel noBarcode(Panel panel)
	{
		Panel rv = new Panel();
		for(Orientation dir:Orientation.values())
			rv.setBorder(dir, panel.hasBorder(dir));
		return rv;
	}
}
