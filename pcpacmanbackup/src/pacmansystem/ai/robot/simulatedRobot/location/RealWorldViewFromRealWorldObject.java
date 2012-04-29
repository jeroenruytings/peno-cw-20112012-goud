package pacmansystem.ai.robot.simulatedRobot.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import data.board.Board;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.RealWorld;
import static pacmansystem.ai.robot.simulatedRobot.location.Pointfs.*;

public class RealWorldViewFromRealWorldObject implements RealWorldViewBuilder
{
	static final int WALLPRIOR = 10;

	static final int NORMALBOARD = 0;

	RealWorld realworld_;

	/**
	 * Creates a builder object for this realworld.
	 * 
	 * @param world
	 */
	public RealWorldViewFromRealWorldObject(RealWorld world)
	{
		this.realworld_ = world;
	}

	/**
	 * Throws exceptions if realworld_ is null;
	 */
	@Override
	public RealWorldViewNoMoving build()
	{
		if (realworld_ == null)
			throw new IllegalArgumentException(
					"Realworld was null, failed building the realworld view");
		RealWorldViewNoMoving view = new RealWorldViewNoMoving();
		for (LocationComponent component : generateComponents())
			view.add(component);
		return view;
	}

	Collection<LocationComponent> generateComponents()
	{
		Collection<LocationComponent> returnValue = new ArrayList<LocationComponent>();
		for (LocationComponent component : generatePanels())
			returnValue.add(component);
		for (LocationComponent component : generateBarcodes())
			returnValue.add(component);
		for (LocationComponent component : generateWalls())
			returnValue.add(component);

		return null;
	}

	Collection<LocationComponent> generateWalls()
	{
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		for (Point point : realworld_.getGlobalBoard().getFilledPoints()) {
			Panel panel = realworld_.getGlobalBoard().getPanelAt(point);
			for (Orientation o : Orientation.values()) {
				WallState state = panel.getWallState(o);
				switch (state)
				{
				case WALL:
					rv.add(genOneWallComponent(point, o));
					break;
				case PASSAGE:
					break;
				case UNKNOWN:
					break;
				}

			}
		}
		return rv;
	}

	LocationComponent genOneWallComponent(Point point, Orientation o)
	{
		List<Pointf> convexPoints = new ArrayList<Pointf>();
		switch (o)
		{
		case NORTH:
			convexPoints.addAll(northPointsWall(point));
			break;
		case EAST:
			convexPoints.addAll(eastPointsWall(point));
			break;
		case SOUTH:
			convexPoints.addAll(southPointsWall(point));
			break;
		case WEST:
			convexPoints.addAll(westPointsWall(point));
			break;
		}
		LocationComponent rv = new WallComponent(convexPoints, WALLPRIOR);
		
		return rv;
	}

	Collection<Pointf> westPointsWall(Point point)
	{
		Collection<Pointf> rv = new ArrayList<Pointf>();
		// vertically placed panel
		Pointf vectorUP = new Pointf(0, SIMINFO.WALLWIDTH);
		Pointf vectorRight = new Pointf(SIMINFO.WALLHEIGHT, 0);
		Pointf origin = origin(point);
		origin = translate(origin, multiply(vectorRight, -0.5f));
		// the true origin has been reached.
		rv.add(origin);
		rv.add(translate(origin, vectorUP));
		rv.add(translate(translate(origin, vectorUP), vectorRight));
		rv.add(translate(origin, vectorRight));
		return rv;
	}

	Collection<Pointf> southPointsWall(Point point)
	{
		Collection<Pointf> rv = new ArrayList<Pointf>();
		Pointf vectorUP = new Pointf(0, SIMINFO.WALLHEIGHT);
		Pointf vectorRight = new Pointf(SIMINFO.WALLWIDTH, 0);
		Pointf origin = origin(point);
		origin = translate(origin, multiply(vectorUP, -0.5f));
		// the true origin has been reached.
		rv.add(origin);
		rv.add(translate(origin, vectorUP));
		rv.add(translate(translate(origin, vectorUP), vectorRight));
		rv.add(translate(origin, vectorRight));
		return rv;
	}

	Collection<Pointf> eastPointsWall(Point point)
	{
		Collection<Pointf> rv = new ArrayList<Pointf>();
		// vertically placed panel
		Pointf vectorUP = new Pointf(0, SIMINFO.WALLWIDTH);
		Pointf vectorRight = new Pointf(SIMINFO.WALLHEIGHT, 0);
		Pointf origin = origin(point);
		origin = translate(origin, new Pointf(SIMINFO.PANELWIDTH, 0));
		origin = translate(origin, multiply(vectorRight, -0.5f));
		// the true origin has been reached.
		rv.add(origin);
		rv.add(translate(origin, vectorUP));
		rv.add(translate(translate(origin, vectorUP), vectorRight));
		rv.add(translate(origin, vectorRight));
		return rv;
	}

	Collection<Pointf> northPointsWall(Point point)
	{
		Collection<Pointf> rv = new ArrayList<Pointf>();
		Pointf vectorUP = new Pointf(0, SIMINFO.WALLHEIGHT);
		Pointf vectorRight = new Pointf(SIMINFO.WALLWIDTH, 0);
		Pointf origin = origin(point);
		origin = translate(origin, new Pointf(0, SIMINFO.PANELHEIGHT));
		origin = translate(origin, multiply(vectorUP, -0.5f));
		// the true origin has been reached.
		rv.add(origin);
		rv.add(translate(origin, vectorUP));
		rv.add(translate(translate(origin, vectorUP), vectorRight));
		rv.add(translate(origin, vectorRight));
		return rv;
	}

	Pointf origin(Point point)
	{
		return new Pointf(point.x * SIMINFO.PANELWIDTH, point.y
				* SIMINFO.PANELHEIGHT);
	}

	Collection<LocationComponent> generateBarcodes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	Collection<LocationComponent> generatePanels()
	{
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Board board = realworld_.getGlobalBoard();
		for (Point point : board.getFilledPoints()) {
			rv.add(panelComp(point));
		}
		return rv;
	}

	private LocationComponent panelComp(Point point)
	{
		List<Pointf> points = new ArrayList<Pointf>();
		Pointf vectorUp = new Pointf(0,SIMINFO.PANELHEIGHT);
		Pointf vectorRight = new Pointf(SIMINFO.PANELWIDTH, 0);
		Pointf origin = origin(point);
		points.add(origin);
		points.add(translate(origin, vectorUp));
		points.add(translate(translate(origin, vectorUp), vectorRight));
		points.add(translate(origin, vectorRight));
		return new OpenComponent(points, NORMALBOARD);
	}
}
