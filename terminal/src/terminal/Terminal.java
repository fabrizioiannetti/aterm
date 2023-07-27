package terminal;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.tm.internal.terminal.connector.TerminalConnector;
import org.eclipse.tm.internal.terminal.connector.TerminalConnector.Factory;
import org.eclipse.tm.internal.terminal.control.ITerminalListener;
import org.eclipse.tm.internal.terminal.control.impl.TerminalPlugin;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalConnector;
import org.eclipse.tm.internal.terminal.provisional.api.TerminalState;
import org.eclipse.tm.terminal.connector.process.ProcessConnector;
import org.eclipse.tm.terminal.connector.process.ProcessSettings;

import terminal.ui.TerminalSession;

public class Terminal {

	private static int counter = 0;

	// a factory to create Local connectors, i.e. to start the shell configured
	// in the system
	private static Factory localConnectorFactory = () -> {
		ProcessSettings processSettings = new ProcessSettings();
		String shell;
		if (System.getenv("SHELL") != null && !"".equals(System.getenv("SHELL").trim())) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			shell = System.getenv("SHELL").trim(); //$NON-NLS-1$
		} else {
			shell = "/bin/sh"; //$NON-NLS-1$
		}
		processSettings.setImage(shell);
		return new ProcessConnector(processSettings);
	};

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell();
		shell.setText("Terminal");

		ITerminalListener target = new ITerminalListener() {
			@Override
			public void setTerminalTitle(String title) {
				display.asyncExec(() -> {
					shell.setText("Terminal - " + title);
				});
			}
			@Override
			public void setState(TerminalState state) {
				// TODO Auto-generated method stub
			}
		};
		// terminal widget
		new TerminalPlugin(); // TODO@fab is this necessary to create an instance?
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(shell);
		TerminalSession session = new TerminalSession(shell, target);
		session.newTerminal(makeLocalTerminalConnector());
		session.newTerminal(makeLocalTerminalConnector());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(session);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static ITerminalConnector makeLocalTerminalConnector() {
		counter++;
		return new TerminalConnector(localConnectorFactory, "terminal-" + counter, "Local", false);
	}

}
