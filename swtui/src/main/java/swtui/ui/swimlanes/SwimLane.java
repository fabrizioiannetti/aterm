package swtui.ui.swimlanes;

import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class SwimLane {

	public interface SectionProvider {
		List<Section> get(long begTs, long endTs);
	}

	private long firstTs; /// < timestamp [ns] of the first event in the lane
	private long lastTs; /// < timestamp [ns] of the last event in the lane

	private Color[] bg = new Color[2];

	private SectionProvider provider;
	private String name;

	public SwimLane(String name, SectionProvider provider) {
		this.name = name;
		this.provider = provider;
		this.bg[0] = new Color(Display.getDefault(), 255, 255, 255);
		this.bg[1] = new Color(Display.getDefault(), 220, 240, 255);
	}

	public void updateRange(long firstTs, long lastTs) {
		this.firstTs = firstTs;
		this.lastTs = lastTs;
		// TODO@multithread
	}

	public Rectangle getBounds(double scaleTsToPixels, int width) {
		return new Rectangle(0, 0, width, (int) ((lastTs - firstTs) * scaleTsToPixels));
	}

	/**
	 * Paint a rectangle of the lane on the given graphic context.
	 *
	 * @param gc              the graphic context to paint on
	 * @param rect            the portion of the lane to paint in lane display
	 *                        coordinates
	 * @param scaleTsToPixels the scale to transform timestamp to pixel units
	 */
	public void paint(GC gc, Rectangle rect, double scaleTsToPixels) {
		long begTs = getTsForY(rect.y, scaleTsToPixels);
		long endTs = getTsForY(rect.y + rect.height, scaleTsToPixels);
//		System.out.printf("PAINT[%s]: begTs=%d, endTs=%d  span=%d view{.x=%d .y=%d .width=%d .height=%d}\n", name,
//				begTs, endTs, endTs - begTs, rect.x, rect.y, rect.width, rect.height);
		var sections = provider.get(begTs, endTs);

		if (sections.size() == 0)
			return;

		if (begTs < firstTs) {
			// paint unkwnon background in grey
			gc.setBackground(new Color(gc.getDevice(), 128, 128, 128));
			gc.fillRectangle(rect.x, 0, rect.width, getYForTs(sections.get(0).getTs() - rect.y, scaleTsToPixels));
		}

		Rectangle sectionBounds = new Rectangle(rect.x, 0, rect.width, 0);
		for (int i = 0; i < sections.size(); i++) {
			Section section = sections.get(i);
			gc.setBackground(bg[section.getCount() % 2]);
			sectionBounds.y = getYForTs(section.getTs(), scaleTsToPixels) - rect.y;
			sectionBounds.height = (i < sections.size() - 1 ? getYForTs(sections.get(i + 1).getTs(), scaleTsToPixels)
					: rect.y + rect.height) - sectionBounds.y;
			gc.fillRectangle(sectionBounds);
			if (section != null)
				section.paint(gc, sectionBounds);
		}
	}

	/**
	 * Get the display y coordinate for the given timestamp, relative to the
	 *
	 * @param ts              the timestamp
	 * @param scaleTsToPixels the scale factor to convert ts into pixels
	 * @return the display y coordinate
	 */
	final private int getYForTs(long ts, double scaleTsToPixels) {
		return (int) (ts * scaleTsToPixels);
	}

	final private long getTsForY(int y, double scaleTsToPixels) {
		return (long) (y / scaleTsToPixels);
	}
}
