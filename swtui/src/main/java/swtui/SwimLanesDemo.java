package swtui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;

import swtui.ui.swimlanes.Section;
import swtui.ui.swimlanes.SwimLane;
import swtui.ui.swimlanes.SwimLane.SectionProvider;
import swtui.ui.swimlanes.SwimLanes;

public class SwimLanesDemo extends ApplicationWindow {
	private SwimLanes lanes = new SwimLanes();
	private double scaleTsToPixels = 2.0;

	public SwimLanesDemo() {
		super(null);
		var lane1 = createLane("lane1", 80, 100);
		var lane2 = createLane("lane2", 40, 200);
		lanes.add(lane1);
		lanes.add(lane2);
	}

	private SwimLane createLane(String name, long sectionDuration, int count) {
		final var sections = new ArrayList<Section>();
		final long firstTs = 0;
		long lastTs = firstTs;
		for (int i = 0; i < count; i++) {
			lastTs = sectionDuration * i;
			sections.add(new Section(lastTs, i));
		}
		var lane = new SwimLane(name, new SectionProvider() {
			@Override
			public List<Section> get(long begTs, long endTs) {
				// TODO: this is inefficient
				var coll = new ArrayList<Section>();
				int i = 0;
				while (i + 1 < sections.size() && sections.get(i).getTs() < begTs) {
					i++;
					if (sections.get(i).getTs() > begTs) {
						coll.add(sections.get(i - 1));
						break;
					}
				}
				while (i < sections.size() && sections.get(i).getTs() < endTs) {
					coll.add(sections.get(i));
					i++;
				}
				return coll;
			}
		});
		lastTs += 10; // to include the last section ts
		lane.updateRange(firstTs, lastTs);
		return lane;
	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setText("SwimLanes Demo");
		getShell().setSize(600, 400);

		final Point origin = new Point(0, 0);
		final Canvas canvas = new Canvas(parent, SWT.NO_BACKGROUND | SWT.V_SCROLL);
		final ScrollBar vBar = canvas.getVerticalBar();
		vBar.addListener(SWT.Selection, e -> {
			int vSelection = vBar.getSelection();
			int destY = -vSelection - origin.y;
			Rectangle rect = lanes.getBounds(scaleTsToPixels, canvas.getClientArea().width);
			canvas.scroll(0, destY, 0, 0, rect.width, rect.height, false);
			origin.y = -vSelection;
		});
		canvas.addListener(SWT.Resize, e -> {
			Rectangle client = canvas.getClientArea();

			Rectangle rect = lanes.getBounds(scaleTsToPixels, client.width);

			vBar.setMaximum(rect.height);
			vBar.setThumb(Math.min(rect.height, client.height));
			int vPage = rect.height - client.height;
			int vSelection = vBar.getSelection();
			if (vSelection >= vPage) {
				if (vPage <= 0)
					vSelection = 0;
				origin.y = -vSelection;
			}
			canvas.redraw();
		});
		canvas.addListener(SWT.Paint, e -> {
			GC gc = e.gc;
			Rectangle client = canvas.getClientArea();
			Rectangle rect = client.intersection(client); // duplicate
			rect.y -= origin.y;
//			System.out.printf("CLIENT: .x=%d .width=%d\n", client.x, client.width);
			int marginHeight = client.height - rect.height;
			if (marginHeight > 0) {
				gc.fillRectangle(0, rect.height, client.width, marginHeight);
			}
			lanes.paint(gc, rect, scaleTsToPixels);
		});
		canvas.addListener(SWT.MouseVerticalWheel, e -> {
			if ((e.stateMask & SWT.MOD1) != 0) {
				// ctrl + wheel -> do zoom
				var zoomIncrement = e.count > 0 ? 1.3 : 1.0 / 1.3;
				scaleTsToPixels *= zoomIncrement;
				canvas.redraw();
				e.doit = false;
			}
		});
		return parent;
	}

	public static void main(String[] args) {
		SwimLanesDemo app = new SwimLanesDemo();
		app.setBlockOnOpen(true);
		app.open();
		Display.getCurrent().dispose();
	}
}