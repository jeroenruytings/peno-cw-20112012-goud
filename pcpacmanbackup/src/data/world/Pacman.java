package data.world;

import java.awt.Point;
import java.util.Date;

public class Pacman {

	private Point position;
	
	private Date timeStamp;
	
	public Pacman(Point position, Date date) {
		this.position = position;
		this.timeStamp = date;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Date getDate() {
		return timeStamp;
	}
}
