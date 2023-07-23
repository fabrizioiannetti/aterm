package org.eclipse.tm.terminal.view.ui.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.tm.internal.terminal.provisional.api.ITerminalControl;

public class ConsoleManager {

	List<CTabItem> consoles = new ArrayList<CTabItem>();

	public static ConsoleManager getInstance() {
		// TODO Auto-generated method stub
		return new ConsoleManager();
	}

	public CTabItem findConsole(ITerminalControl control) {
		return consoles.stream().filter(console -> (console == control)).findFirst().get();
	}

}
