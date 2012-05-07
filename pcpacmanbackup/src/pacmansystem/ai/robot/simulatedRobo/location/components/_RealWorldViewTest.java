package pacmansystem.ai.robot.simulatedRobo.location.components;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.board.Board;
import data.board.Panel;
import data.board.Panel.WallState;
import data.enums.Orientation;
import data.world.RealWorld;

import pacmansystem.ai.robot.Barcode;
import pacmansystem.ai.robot.simulatedRobo.location.RealWorldView;
import pacmansystem.ai.robot.simulatedRobo.location.RealWorldViewFromRealWorldObject;
import pacmansystem.ai.robot.simulatedRobo.location.RealWorldViewNoMoving;
import pacmansystem.ai.robot.simulatedRobo.point.Pointf;

public class _RealWorldViewTest
{
	private RealWorldView realworld;
	@Before
	public void init()
	{
		RealWorld world = new RealWorld();
		Board board =world.getGlobalBoard();
		
		Panel panel = new Panel();
		panel.setBorder(Orientation.EAST,WallState.WALL);
		panel.setBorder(Orientation.WEST,WallState.WALL);
		panel.setBarcode(new Barcode(25),Orientation.NORTH);
		board.add(panel , new Point(0,0));
		RealWorldViewFromRealWorldObject builder = new RealWorldViewFromRealWorldObject(world);
		realworld = builder.build();
	}
	@Test
	public void panel0Test()
	{
		
		List<LocationComponent> comps = realworld.getIn(new Pointf(50,120), new Pointf(-50,120));
		System.out.println(comps.size());
		
	}
}
