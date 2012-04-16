package communicator.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communicator.parser.messages.BarcodeAtMessage;
import communicator.parser.messages.CancelPlanMessage;
import communicator.parser.messages.CapturedMessage;
import communicator.parser.messages.DiscoverMessage;
import communicator.parser.messages.JoinMessage;
import communicator.parser.messages.Message;
import communicator.parser.messages.NameMessage;
import communicator.parser.messages.PacmanMessage;
import communicator.parser.messages.PingMessage;
import communicator.parser.messages.PlanMessage;
import communicator.parser.messages.PongMessage;
import communicator.parser.messages.PositionMessage;

public class _CommandTest {

	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetMessageType() {
		Message cmd;
		cmd = new JoinMessage();
		assertEquals(cmd.getKeyword(), "JOIN");
		cmd = new NameMessage(null, null);
		assertEquals(cmd.getKeyword(), "NAME");
		cmd = new PositionMessage(null, null);
		assertEquals(cmd.getKeyword(), "POSITION");
		cmd = new DiscoverMessage(null, null, 0, 0, 0, 0);
		assertEquals(cmd.getKeyword(), "DISCOVER");
		cmd = new BarcodeAtMessage(null, null, 0, 0);
		assertEquals(cmd.getKeyword(), "BARCODEAT");
		cmd = new PacmanMessage(null, null);
		assertEquals(cmd.getKeyword(), "PACMAN");
		cmd = new CapturedMessage(null);
		assertEquals(cmd.getKeyword(), "CAPTURED");
		cmd = new PlanMessage(null,new Point(1,1));
		assertEquals(cmd.getKeyword(), "PLAN");
		cmd = new CancelPlanMessage(null);
		assertEquals(cmd.getKeyword(), "CANCELPLAN");
		cmd = new PingMessage(null, null, null);
		assertEquals(cmd.getKeyword(), "PING");
		cmd = new PongMessage(null, null, null);
		assertEquals(cmd.getKeyword(), "PONG");
	}

	@Test
	public void testEquals() {
		JoinMessage cmdJ1 = new JoinMessage();
		JoinMessage cmdJ2 = new JoinMessage();
		assertTrue(cmdJ1.equals(cmdJ2));
		NameMessage cmdN1 = new NameMessage("ik", "versie 3.1");
		NameMessage cmdN2 = new NameMessage("ik", "versie 3.1");
		assertTrue(cmdN1.equals(cmdN2));
		PositionMessage cmdP1 = new PositionMessage("jij", new Point(1,1));
		PositionMessage cmdP2 = new PositionMessage("jij", new Point(1,1));
		assertTrue(cmdP1.equals(cmdP2));
		DiscoverMessage cmdD1 = new DiscoverMessage("ik", new Point(2, 2), 1, 1,0,0);
		DiscoverMessage cmdD2 = new DiscoverMessage("ik", new Point(2, 2), 1, 1,0,0);
		assertTrue(cmdD1.equals(cmdD2));
		BarcodeAtMessage cmdBA1 = new BarcodeAtMessage("ik", new Point(0,0),9,4);
		BarcodeAtMessage cmdBA2 = new BarcodeAtMessage("ik", new Point(0,0),9,4);
		assertTrue(cmdBA1.equals(cmdBA2));
		PacmanMessage cmdPC1 = new PacmanMessage("ik", new Point(3,3));
		PacmanMessage cmdPC2 = new PacmanMessage("ik", new Point(3,3));
		assertTrue(cmdPC1.equals(cmdPC2));
		CapturedMessage cmdC1 = new CapturedMessage("jij");
		CapturedMessage cmdC2 = new CapturedMessage("jij");
		assertTrue(cmdC1.equals(cmdC2));
		PlanMessage cmdPl1 = new PlanMessage("ik", new Point(1,1),new Point(2,2),new Point(3, 3));
		PlanMessage cmdPl2 = new PlanMessage("ik", new Point(1,1),new Point(2,2),new Point(3, 3));
		assertTrue(cmdPl1.equals(cmdPl2));
		CancelPlanMessage cmdCP1 = new CancelPlanMessage("jij");
		CancelPlanMessage cmdCP2 = new CancelPlanMessage("jij");
		assertTrue(cmdCP1.equals(cmdCP2));
		PingMessage cmdPi1 = new PingMessage("ik","andere","zeg eens iets!");
		PingMessage cmdPi2 = new PingMessage("ik","andere","zeg eens iets!");
		assertTrue(cmdPi1.equals(cmdPi2));
		PongMessage cmdPo1 = new PongMessage("jij", "andere", "zeg eens iets");
		PongMessage cmdPo2 = new PongMessage("jij", "andere", "zeg eens iets");
		assertTrue(cmdPo1.equals(cmdPo2));
		
		
		
	}

}
