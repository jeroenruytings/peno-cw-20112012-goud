package data.world;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import pacmansystem.ai.robot.Barcode;
import data.board.Board;
import data.enums.Orientation;

public interface RobotDataView
{

	public abstract Map<Barcode, Point> getBarcodes();

	public abstract String getName();

	public abstract boolean isCapturedPacman();

	/**
	 * @return The board this robot has created.
	 */
	public abstract Board getBoard();

	/**
	 * @return The location pacman was last seen by this robot.
	 */
	public abstract Point getPacmanLastSighted();

	/**
	 * @return The position of the robot.
	 */
	public abstract Point getPosition();

	public abstract Orientation getOrientation();

	public abstract void clearPlan();

	public abstract void addPlan(ArrayList<Point> newPlan);

	public abstract String toString();

	public abstract Color getRobotColor();
	
	public abstract void pong(String name, String message);

}