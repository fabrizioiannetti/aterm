package swtui.ui.swimlanes;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class SwimLaneWidget extends Canvas {
	private SwimLane lane;

	public SwimLaneWidget(Composite parent, int style, SwimLane lane) {
		super(parent, style);
		this.lane = lane;
	}

}
