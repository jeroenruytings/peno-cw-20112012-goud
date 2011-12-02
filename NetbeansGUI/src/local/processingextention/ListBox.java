package local.processingextention;

import java.util.ArrayList;
import java.util.Collection;

import local.RobotGui;

public class ListBox<T> implements Drawable {
	int Xoffset;
	int Yoffset;
	int width;
	int heigth;
	Collection<T> elems;
	ArrayList<Label> labels;

	public ListBox(Collection<T> elems, int Xoffset, int Yoffset) {
		this.elems = elems;
		labels = new ArrayList<Label>();
		for (int i = 0; i < 10; i++) {
			Label L = new Label(RobotGui.instance(), "");
			L.setXOffset(Xoffset);
			L.setYOffset(30 * i + Yoffset);
			labels.add(L);
		}
	}

	@Override
	public void draw() {
		int i = 0;
		int c = elems.size();
		int label = 0;
		for (T t : elems) {
			i++;
			if ((c - i) < should_draw()) {
				// ten elements are left in the collection atm
				labels.get(label).setText(t.toString());
				labels.get(label).draw();
				label++;
			}
		}
	}

	private int should_draw() {
		return 10;
	}

}
