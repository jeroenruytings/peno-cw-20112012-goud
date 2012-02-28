package pacmansystem.board;

import java.awt.Point;

import pacmansystem.board.enums.Orientation;

public class BoardCreator
{
	public static Board createWithEdges(int x, int y)
	{
		Board returnValue = new Board(x, y);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				returnValue.add(new Panel(), new Point(i, j));
			}
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.NORTH, true);
			returnValue.addForced(p, new Point(i, 0));
		}
		for (int i = 0; i < y; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.EAST, true);
			returnValue.addForced(p, new Point(x, i));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.SOUTH, true);
			returnValue.addForced(p, new Point(i, y));
		}
		for (int i = 0; i < x; i++) {
			Panel p = new Panel();
			p.setBorder(Orientation.WEST, true);
			returnValue.addForced(p, new Point(i, 0));
		}
		Panel p = new Panel();
		p.setBorder(Orientation.NORTH, true);
		p.setBorder(Orientation.WEST, true);
		returnValue.addForced(p, new Point(0, 0));
		p = new Panel();
		p.setBorder(Orientation.NORTH, true);
		p.setBorder(Orientation.EAST, true);
		returnValue.addForced(p, new Point(x, 0));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, true);
		p.setBorder(Orientation.WEST, true);
		returnValue.addForced(p, new Point(x, y));
		p = new Panel();
		p.setBorder(Orientation.SOUTH, true);
		p.setBorder(Orientation.EAST, true);
		returnValue.addForced(p, new Point(0, y));
		return returnValue;
	}
}
