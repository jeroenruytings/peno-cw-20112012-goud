package pacmansystem.ai.robot.simulatedRobot.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.simulatedRobot.SIMINFO;
import pacmansystem.ai.robot.simulatedRobot.location.components.LocationComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.OpenComponent;
import pacmansystem.ai.robot.simulatedRobot.location.components.WallComponent;
import pacmansystem.ai.robot.simulatedRobot.point.Pointf;
import pacmansystem.ai.robot.simulatedRobot.point.Pointfs;
import pacmansystem.ai.robot.simulatedRobot.point.Vector;

import data.board.Board;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.RealWorld;
import static pacmansystem.ai.robot.simulatedRobot.point.Pointfs.*;

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
		return returnValue;
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
		List<Pointf> convexPoints  ;
		switch (o)
		{
		case NORTH:
			convexPoints=northPointsWall(point);
			break;
		case EAST:
			convexPoints=eastPointsWall(point);
			break;
		case SOUTH:
			convexPoints= southPointsWall(point);
			break;
		case WEST:
			convexPoints= westPointsWall(point);
			break;
		default:
			throw new Error("een nieuwe direction enum waarde is toegevoegd, fok of");
		}
		LocationComponent rv = new WallComponent(convexPoints, WALLPRIOR);
		
		return rv;
	}

	List<Pointf> westPointsWall(Point point)
	{
		List<Pointf> rv = new ArrayList<Pointf>();
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

	List<Pointf> southPointsWall(Point point)
	{
		List<Pointf> rv = new ArrayList<Pointf>();
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

	List<Pointf> eastPointsWall(Point point)
	{
		List<Pointf> rv = new ArrayList<Pointf>();
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

	List<Pointf> northPointsWall(Point point)
	{
		List<Pointf> rv = new ArrayList<Pointf>();
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
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Board board = realworld_.getGlobalBoard();
		for(Point point :board.getFilledPoints())
		{
			Panel panel = board.getPanelAt(point);
			if(!panel.hasBarcode())
				continue;
			Barcode barcode = panel.getBarcode();
			Orientation orientation = panel.getBarcodeOrientation();
			if(barcode==null||orientation==null)
				continue;
			for(LocationComponent component:generateBarcode(point,barcode,orientation))
				rv.add(component);
		}
		return rv;
	}

	private Collection<LocationComponent> generateBarcode(Point point, Barcode barcode,
			Orientation orientation)
	{
		//Generate all the seperate rectangles.
		Collection<LocationComponent> rv;
		switch (orientation)
		{
		case NORTH:
			rv = genNorthBarcodes(point,barcode);
			break;

		case EAST:
			rv = genEastBarcodes(point,barcode);
			break;

		case SOUTH:
			rv = genSouthBarcode(point,barcode);
			break;

		case WEST:
			rv = genWestBarcode(point,barcode);
			break;
			
		default:
			throw new Error("Blahblah orientation realworldview barcodes");
		}
		
		return null;
	}

	private Collection<LocationComponent> genWestBarcode(Point point,
			Barcode barcode)
	{
		//XXX:not finishede
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Pointf origin = new Pointf(point.x*SIMINFO.PANELWIDTH,point.y*SIMINFO.PANELHEIGHT);
		Pointf barcodeUp = new Pointf(SIMINFO.PANELWIDTH, SIMINFO.BARCODEHEIGHT);
		Pointf barcodeRight = new Pointf(SIMINFO.PANELWIDTH, 0);
		Pointf tomiddle = new Pointf(0,SIMINFO.PANELHEIGHT/2);
		origin= translate(origin, tomiddle);
		origin = translate(origin, multiply(barcodeUp,-4));
		
		for(boolean c: barcode.getBinaryValues())
		{
			//Now origin should be in the bottom left corner
			//generating all 8 barcodes 
			List<Pointf> points = new ArrayList<Pointf>();
			points.add(origin);
			points.add(translate(origin, barcodeUp));
			points.add(translate(translate(origin, barcodeRight), barcodeUp));
			points.add(translate(origin, barcodeRight));
			int color = 0;
			if(c)
				color= SIMINFO.WHITE;
			else
				color = SIMINFO.BLACK;
			LocationComponent component = new OpenComponent(points, SIMINFO.BARCODEZ,color);
			rv.add(component);
			origin = translate(origin, barcodeUp);
		}
		return rv;
	}

	private Collection<LocationComponent> genSouthBarcode(Point point,
			Barcode barcode)
	{
		//XXX:not finishede
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Pointf origin = new Pointf(point.x*SIMINFO.PANELWIDTH,point.y*SIMINFO.PANELHEIGHT);
		Pointf barcodeUp = new Pointf(SIMINFO.PANELWIDTH, -SIMINFO.BARCODEHEIGHT);
		Pointf barcodeRight = new Pointf(SIMINFO.PANELWIDTH, 0);
		Pointf tomiddle = new Pointf(0,SIMINFO.PANELHEIGHT/2);
		origin= translate(origin, tomiddle);
		origin = translate(origin, multiply(barcodeUp,-4));
		
		for(boolean c: barcode.getBinaryValues())
		{
			//Now origin should be in the bottom left corner
			//generating all 8 barcodes 
			List<Pointf> points = new ArrayList<Pointf>();
			points.add(origin);
			points.add(translate(origin, barcodeUp));
			points.add(translate(translate(origin, barcodeRight), barcodeUp));
			points.add(translate(origin, barcodeRight));
			int color = 0;
			if(c)
				color= SIMINFO.WHITE;
			else
				color = SIMINFO.BLACK;
			LocationComponent component = new OpenComponent(points, SIMINFO.BARCODEZ,color);
			rv.add(component);
			origin = translate(origin, barcodeUp);
		}
		return rv;
	}

	private Collection<LocationComponent> genEastBarcodes(Point point,
			Barcode barcode)
	{
		//XXX:not finishede
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Pointf origin = new Pointf(point.x*SIMINFO.PANELWIDTH,point.y*SIMINFO.PANELHEIGHT);
		Pointf barcodeUp = new Pointf(-SIMINFO.BARCODEHEIGHT, SIMINFO.PANELHEIGHT);
		Pointf barcodeRight = new Pointf(0, SIMINFO.PANELHEIGHT);
		Pointf tomiddle = new Pointf(SIMINFO.PANELWIDTH/2,0);
		origin= translate(origin, tomiddle);
		origin = translate(origin, multiply(barcodeUp,-4));
		
		for(boolean c: barcode.getBinaryValues())
		{
			//Now origin should be in the bottom left corner
			//generating all 8 barcodes 
			List<Pointf> points = new ArrayList<Pointf>();
			points.add(origin);
			points.add(translate(origin, barcodeUp));
			points.add(translate(translate(origin, barcodeRight), barcodeUp));
			points.add(translate(origin, barcodeRight));
			int color = 0;
			if(c)
				color= SIMINFO.WHITE;
			else
				color = SIMINFO.BLACK;
			LocationComponent component = new OpenComponent(points, SIMINFO.BARCODEZ,color);
			rv.add(component);
			origin = translate(origin, barcodeUp);
		}
		return rv;
	}

	private Collection<LocationComponent> genNorthBarcodes(Point point,
			Barcode barcode)
	{
		Collection<LocationComponent> rv = new ArrayList<LocationComponent>();
		Pointf origin = new Pointf(point.x*SIMINFO.PANELWIDTH,point.y*SIMINFO.PANELHEIGHT);
		Pointf barcodeUp = new Pointf(SIMINFO.PANELWIDTH, SIMINFO.BARCODEHEIGHT);
		Pointf barcodeRight = new Pointf(SIMINFO.PANELWIDTH, 0);
		Pointf tomiddle = new Pointf(0,SIMINFO.PANELHEIGHT/2);
		origin= translate(origin, tomiddle);
		origin = translate(origin, multiply(barcodeUp,-4));
		
		for(boolean c: barcode.getBinaryValues())
		{
			//Now origin should be in the bottom left corner
			//generating all 8 barcodes 
			List<Pointf> points = new ArrayList<Pointf>();
			points.add(origin);
			points.add(translate(origin, barcodeUp));
			points.add(translate(translate(origin, barcodeRight), barcodeUp));
			points.add(translate(origin, barcodeRight));
			int color = 0;
			if(c)
				color= SIMINFO.WHITE;
			else
				color = SIMINFO.BLACK;
			LocationComponent component = new OpenComponent(points, SIMINFO.BARCODEZ,color);
			rv.add(component);
			origin = translate(origin, barcodeUp);
		}
		return rv;
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
		return new OpenComponent(points, NORMALBOARD,SIMINFO.BROWN);
	}
}
