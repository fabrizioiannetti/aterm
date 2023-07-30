package terminal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.tm.internal.terminal.control.ITerminalListener;
import org.eclipse.tm.internal.terminal.control.ITerminalViewControl;
import org.eclipse.tm.internal.terminal.control.TerminalViewControlFactory;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalState;

public class TerminalSession extends SashForm {
	private ITerminalListener target;

	private List<ITerminalViewControl> terminals = new ArrayList<ITerminalViewControl>();

	private class KeyBindings implements Listener {
		private Shell shell;
		private Display display;

		public KeyBindings(Shell shell) {
			this.shell = shell;
			display = shell.getDisplay();
			display.addFilter(SWT.KeyDown, this);
		}

		@Override
		public void handleEvent(Event event) {
			Shell activeShell = display.getActiveShell();
//			System.out.printf("handleEvent(keyCode = )\n");
			if (activeShell == shell) {
				if ((event.stateMask & SWT.ALT) != 0) {
					if (event.keyCode == SWT.ARROW_DOWN) {
						event.type = SWT.None;
						selectTerminal(event, 1);
					} else if (event.keyCode == SWT.ARROW_UP) {
						event.type = SWT.None;
						selectTerminal(event, -1);
					}
				}
			}
		}

		private void selectTerminal(Event event, int increment) {
			int i = 0;
			for (ITerminalViewControl terminal : terminals) {
				if (terminal.getControl() == event.widget) {
					int next_i = i + increment;
					if (next_i >= 0 && next_i < terminals.size()) {
						ITerminalViewControl nextTerminal = terminals.get(next_i);
						if (nextTerminal != terminal) {
							nextTerminal.setFocus();
							break;
						}
					}
				}
				i++;
			}
		}

	}

	public TerminalSession(Composite parent, ITerminalListener listener) {
		super(parent, SWT.VERTICAL);
		setLayout(new FillLayout());
		target = listener;

		new KeyBindings(getShell());
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
