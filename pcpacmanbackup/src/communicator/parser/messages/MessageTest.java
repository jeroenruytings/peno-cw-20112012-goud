package communicator.parser.messages;

import static org.junit.Assert.*;

import java.awt.Point;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communicator.parser.decoder.ProtocolDecoder;

public class MessageTest {

	private static BarcodeAtMessage barcodeAtMessage;
	private static CancelPlanMessage cancelPlanMessage;
	private static CapturedMessage capturedMessage;
	private static DiscoverMessage discoverMessage;
	private static JoinMessage joinMessage;
	private static NameMessage nameMessage;
	private static PacmanMessage pacmanMessage;
	private static PingMessage pingMessage;
	private static PlanMessage planMessage;
	private static PongMessage pongMessage;
	private static PositionMessage positionMessage;
	private static RebarcodeAtMessage rebarcodeAtMessage;
	private static RediscoverMessage rediscoverMessage;
	private static RenameMessage renameMessage;
	private static ReUndoBarcodeMessage reUndoBarcodeMessage;
	private static ShowmapMessage showmapMessage;
	private static UndoBarcodeMessage undoBarcodeMessage;
	private static List<Message> messagelist;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		barcodeAtMessage = new BarcodeAtMessage("naam", new Point(5,5), 25, 2);
		cancelPlanMessage = new CancelPlanMessage("naam");
		capturedMessage = new CapturedMessage("naam");
		discoverMessage = new DiscoverMessage("naam", new Point(5, 5), 1, 1, 1, 0);
		joinMessage = new JoinMessage();
		nameMessage = new NameMessage("naam", "2.2");
		pacmanMessage = new PacmanMessage("naam", new Point(4,4));
		pingMessage = new PingMessage("naam", "andere", "hallo");
		planMessage = new PlanMessage("naam", new Point(1,4),new Point(6,4), new Point(5,7));
		pongMessage = new PongMessage("naam", "andere", "daaag");
		positionMessage = new PositionMessage("naam", new Point(4,7));
		rebarcodeAtMessage = new RebarcodeAtMessage("naam", new Point(45,4), 25, 3);
		rediscoverMessage = new RediscoverMessage("naam", new Point(5,4), 1, 0, 1, 1);
		renameMessage = new RenameMessage("naam", "2.21");
		reUndoBarcodeMessage = new ReUndoBarcodeMessage("ookeenseenanderenaam", new Point(4, 4));
		showmapMessage = new ShowmapMessage("anderenaaam", "nogeenandere");
		undoBarcodeMessage = new UndoBarcodeMessage("robot", new Point(4,5));
		messagelist = new ArrayList<Message>();
		messagelist.add(barcodeAtMessage);
		messagelist.add(cancelPlanMessage);
		messagelist.add(capturedMessage);
		messagelist.add(discoverMessage);
		messagelist.add(joinMessage);
		messagelist.add(nameMessage);
		messagelist.add(pacmanMessage);
		messagelist.add(pingMessage);
		messagelist.add(planMessage);
		messagelist.add(pongMessage);
		messagelist.add(positionMessage);
		messagelist.add(rebarcodeAtMessage);
		messagelist.add(rediscoverMessage);
		messagelist.add(renameMessage);
		messagelist.add(reUndoBarcodeMessage);
		messagelist.add(showmapMessage);
		messagelist.add(undoBarcodeMessage);
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSentString() {
		ProtocolDecoder decoder = new ProtocolDecoder();
		Message decodedMessage = null;
		for (Message msg : messagelist){
			try {
				decodedMessage = decoder.parse(msg.getSentString());
			} catch (ParseException e) {
				fail("Parse Exception gegooit!");
			}
			assertTrue(msg.equals(decodedMessage));
		}
	}
	
	

}
