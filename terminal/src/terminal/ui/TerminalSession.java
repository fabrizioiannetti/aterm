package terminal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tm.internal.terminal.control.ITerminalListener;
import org.eclipse.tm.internal.terminal.control.ITerminalViewControl;
import org.eclipse.tm.internal.terminal.control.TerminalViewControlFactory;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalState;

public class TerminalSession extends SashForm {
	private ITerminalListener target;

	private List<ITerminalViewControl> terminals = new ArrayList<ITerminalViewControl>();

	public TerminalSession(Composite parent, ITerminalListener listener) {
		super(parent, SWT.VERTICAL);
		setLayout(new FillLayout());
		target = listener;
	}

	public void newTerminal(ITerminalConnector connector) {
//		Composite composite = new Composite(this, SWT.NONE);
//		composite.setLayout(new BorderLayout());
//		Label titleBar = new Label(composite, SWT.NONE);

		ITerminalListener listener = new ITerminalListener() {
			@Override
			public void setTerminalTitle(String title) {
				getDisplay().asyncExec(() -> {
//					titleBar.setText(title);
					target.setTerminalTitle(title);
				});
			}

			@Override
			public void setState(TerminalState state) {
				getDisplay().asyncExec(() -> {
					target.setState(state);
				});
			}
		};
		ITerminalConnector[] connectors = new ITerminalConnector[0];
		ITerminalViewControl terminal = TerminalViewControlFactory.makeControl(listener, this, connectors, false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(terminal.getRootControl());
		terminal.setConnector(connector);
		terminal.connectTerminal();
		terminals.add(terminal);
	}

}
