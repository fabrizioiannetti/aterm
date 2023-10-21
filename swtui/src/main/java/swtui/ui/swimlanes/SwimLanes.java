package swtui.ui.swimlanes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class SwimLanes {
	private List<SwimLane> lanes = new ArrayList<SwimLane>();
	private int[] weights;

	public void add(SwimLane lane) {
		lanes.add(lane);
		weights = new int[lanes.size()];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 100;
		}
	}

	public void setWeights(int[] widths) {
		this.weights = widths;
	}

	public Rectangle getBounds(double scaleTsToPixels, int width) {
		Rectangle bounds = new Rectangle(0, 0, 0, 0);
		for (SwimLane lane : lanes) {
			bounds.add(lane.getBounds(scaleTsToPixels, width));
		}
		return bounds;
	}

	public void paint(GC gc, Rectangle rect, double scaleTsToPixels) {
		Rectangle r = new Rectangle(rect.x, rect.y, 0, rect.height);
		int wsum = 0;
		for (int w : weights) {
			wsum += w;
		}
		for (int i = 0; i < lanes.size(); i++) {
			var lane = lanes.get(i);
			r.width = rect.width * weights[i] / wsum;
			lane.paint(gc, r, scaleTsToPixels);
			r.x += r.width;
		}
	}
}
