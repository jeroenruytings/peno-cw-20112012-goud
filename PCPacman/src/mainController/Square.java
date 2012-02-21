package mainController;

public class Square {
	
	private int wallNorth;
	private int wallEast;
	private int wallWest;
	private int wallSouth;
	private boolean discovered;

	public Square(){
		//0 = onbekend, 1 = muur, 2 = geen muur
		wallNorth = 0;
		wallEast = 0;
		wallWest = 0;
		wallSouth = 0;
		discovered = false;
		//barcodeFromNorth = null;
		//barcodeFromSouth = null;
		
		
	}

}
