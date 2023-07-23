package org.eclipse.ui.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.swt.widgets.Event;

public interface IHandlerService {

	void executeCommand(ParameterizedCommand cmd, Event cmdEvent) throws ExecutionException;

}
