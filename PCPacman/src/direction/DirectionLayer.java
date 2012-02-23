package direction;

import mainController.MainController.Orientation;
import panel.PanelLayer;

public class DirectionLayer {
	private Orientation currentOrientation;


	private PanelLayer layer;

	/**
	 * 
	 * @param layer
	 */
	public DirectionLayer(PanelLayer layer) {
		this.layer = layer;
	}

	public void go(Orientation... o) {
		for (Orientation orient : o)
			go(orient);
	}

	/**
	 * 
	 */
	public void go(Orientation o) {
		for(Direction d:Direction.values())
			if(this.currentOrientation==o.addTo(d))
				{layer.go(d);
				this.currentOrientation = o;
				return;
				}
	}
	public Orientation getDirection()
	{
		return currentOrientation;
	}

}