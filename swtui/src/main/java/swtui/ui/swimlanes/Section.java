package swtui.ui.swimlanes;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class Section {
	private long ts;
	private int count;
	private List<Span> spans;

	public Section(long ts, int count) {
		super();
		this.ts = ts;
		this.count = count;
	}

	public void addSpan(Span span) {
		spans.add(span);
	}

	@Override
	public void finalize() {
		spans.sort((s1, s2) -> (int) (s1.begTs - s2.begTs));
	}

	public final long getTs() {
		return ts;
	}

	public void paint(GC gc, Rectangle rect) {
//		System.out.printf("  SECTION-PAINT[%d]: rect{.x= %d .y=%d .width=%d .height=%d}\n", count, rect.x, rect.y,
//				rect.width, rect.height);
		gc.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y);
		String desc = "count=%s (ts = %d)".formatted(count, ts);
		var extent = gc.textExtent(desc);
		if (extent.y + 2 <= rect.height)
			gc.drawText(desc, rect.x, rect.y + 2);
	}

	public final int getCount() {
		return count;
	}
}
