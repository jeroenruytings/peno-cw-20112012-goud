package pacmansystem.ai.robot.fysicalRobot;

import java.io.IOException;

import pacmansystem.ai.robot.fysicalRobot.connector.MoverLayer;

public class BarcodeTester {
public static void main(String[] args) {
	MoverLayer m = new MoverLayer();
	PanelLayer p = new PanelLayer(m);
	while(true)
		{
			p.getBarcode();
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
}
