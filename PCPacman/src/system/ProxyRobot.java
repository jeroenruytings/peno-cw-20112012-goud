package system;

import java.awt.Point;
import java.util.Map;

import mainController.Orientation;

import panel.Barcode;

import board.Board;
import board.Panel;

public interface ProxyRobot {

	public Board getBoard();
	public Point getLocation();
	public Map<Barcode, Point> 	getKnownPoint();
	public Orientation getOrientation();
	public Point getPacmanGuess();
	
	public void setPanel(Panel panel,Point point);
	
	
}
