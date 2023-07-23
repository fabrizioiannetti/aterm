package org.eclipse.ui.contexts;

public interface IContextService {

	IContextActivation activateContext(String string);

	void deactivateContext(IContextActivation terminalContextActivation);

}
