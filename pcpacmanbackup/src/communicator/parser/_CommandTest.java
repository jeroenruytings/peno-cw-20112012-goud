package communicator.parser;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.color.CMMException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communicator.parser.command.CommandBarcode;
import communicator.parser.command.CommandBarcodeAt;
import communicator.parser.command.CommandCancelPlan;
import communicator.parser.command.CommandCaptured;
import communicator.parser.command.CommandDiscover;
import communicator.parser.command.CommandJoin;
import communicator.parser.command.CommandName;
import communicator.parser.command.CommandPacman;
import communicator.parser.command.CommandPing;
import communicator.parser.command.CommandPlan;
import communicator.parser.command.CommandPong;
import communicator.parser.command.CommandPosition;

public class _CommandTest {

	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetMessageType() {
		Command cmd;
		cmd = new CommandJoin();
		assertEquals(cmd.getMessageType(), MessageType.JOIN);
		cmd = new CommandName(null, null);
		assertEquals(cmd.getMessageType(), MessageType.NAME);
		cmd = new CommandPosition(null, null);
		assertEquals(cmd.getMessageType(), MessageType.POSITION);
		cmd = new CommandDiscover(null, null, 0, 0, 0, 0);
		assertEquals(cmd.getMessageType(), MessageType.DISCOVER);
		cmd = new CommandBarcode(null,0,0);
		assertEquals(cmd.getMessageType(), MessageType.BARCODE);
		cmd = new CommandBarcodeAt(null, null, 0, 0);
		assertEquals(cmd.getMessageType(), MessageType.BARCODEAT);
		cmd = new CommandPacman(null, null);
		assertEquals(cmd.getMessageType(), MessageType.PACMAN);
		cmd = new CommandCaptured(null);
		assertEquals(cmd.getMessageType(), MessageType.CAPTURED);
		cmd = new CommandPlan(null,new Point(1,1));
		assertEquals(cmd.getMessageType(), MessageType.PLAN);
		cmd = new CommandCancelPlan(null);
		assertEquals(cmd.getMessageType(), MessageType.CANCELPLAN);
		cmd = new CommandPing(null, null, null);
		assertEquals(cmd.getMessageType(), MessageType.PING);
		cmd = new CommandPong(null, null, null);
		assertEquals(cmd.getMessageType(), MessageType.PONG);
	}

	@Test
	public void testEquals() {
		CommandJoin cmdJ1 = new CommandJoin();
		CommandJoin cmdJ2 = new CommandJoin();
		assertTrue(cmdJ1.equals(cmdJ2));
		CommandName cmdN1 = new CommandName("ik", "versie 3.1");
		CommandName cmdN2 = new CommandName("ik", "versie 3.1");
		assertTrue(cmdN1.equals(cmdN2));
		CommandPosition cmdP1 = new CommandPosition("jij", new Point(1,1));
		CommandPosition cmdP2 = new CommandPosition("jij", new Point(1,1));
		assertTrue(cmdP1.equals(cmdP2));
		CommandDiscover cmdD1 = new CommandDiscover("ik", new Point(2, 2), 1, 1,0,0);
		CommandDiscover cmdD2 = new CommandDiscover("ik", new Point(2, 2), 1, 1,0,0);
		assertTrue(cmdD1.equals(cmdD2));
		CommandBarcode cmdB1 = new CommandBarcode("jij", 9, 4);
		CommandBarcode cmdB2 = new CommandBarcode("jij", 9, 4);
		assertTrue(cmdB1.equals(cmdB2));
		CommandBarcodeAt cmdBA1 = new CommandBarcodeAt("ik", new Point(0,0),9,4);
		CommandBarcodeAt cmdBA2 = new CommandBarcodeAt("ik", new Point(0,0),9,4);
		assertTrue(cmdBA1.equals(cmdBA2));
		CommandPacman cmdPC1 = new CommandPacman("ik", new Point(3,3));
		CommandPacman cmdPC2 = new CommandPacman("ik", new Point(3,3));
		assertTrue(cmdPC1.equals(cmdPC2));
		CommandCaptured cmdC1 = new CommandCaptured("jij");
		CommandCaptured cmdC2 = new CommandCaptured("jij");
		assertTrue(cmdC1.equals(cmdC2));
		CommandPlan cmdPl1 = new CommandPlan("ik", new Point(1,1),new Point(2,2),new Point(3, 3));
		CommandPlan cmdPl2 = new CommandPlan("ik", new Point(1,1),new Point(2,2),new Point(3, 3));
		assertTrue(cmdPl1.equals(cmdPl2));
		CommandCancelPlan cmdCP1 = new CommandCancelPlan("jij");
		CommandCancelPlan cmdCP2 = new CommandCancelPlan("jij");
		assertTrue(cmdCP1.equals(cmdCP2));
		CommandPing cmdPi1 = new CommandPing("ik","andere","zeg eens iets!");
		CommandPing cmdPi2 = new CommandPing("ik","andere","zeg eens iets!");
		assertTrue(cmdPi1.equals(cmdPi2));
		CommandPong cmdPo1 = new CommandPong("jij", "andere", "zeg eens iets");
		CommandPong cmdPo2 = new CommandPong("jij", "andere", "zeg eens iets");
		assertTrue(cmdPo1.equals(cmdPo2));
		
		
		
	}

}
