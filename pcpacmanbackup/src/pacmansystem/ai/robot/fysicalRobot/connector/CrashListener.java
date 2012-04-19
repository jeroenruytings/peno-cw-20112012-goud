package pacmansystem.ai.robot.fysicalRobot.connector;

import java.util.Observable;
import java.util.Observer;

public class CrashListener implements Observer {
	
	private MoverLayer mover;
	
	public CrashListener(MoverLayer mover){
		this.mover = mover;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (mover.getIsCrashed() == true){
			// TODO jeroen!!!
		}
		
	}

}
