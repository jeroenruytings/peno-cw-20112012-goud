package local;

import java.awt.Color;

public enum SensorColor {
	WHITE(0xffffff),BROWN(0xdaa520),BLACK(0);
	public int rgb;
	private SensorColor(int rgb){
		this.rgb=rgb;
	}
}
