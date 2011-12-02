package local;

import java.util.HashMap;
import java.util.Map;

import processing.core.PImage;

public class PanelCollection {
	Map<String, PImage> barcodeToImage;
	Map<String,PImage> barcodeToSmallImage;
	public PanelCollection(){
		barcodeToImage=new HashMap<String, PImage>();
		barcodeToSmallImage=new HashMap<String, PImage>();
		
	}
}
