package org.eclipse.core.runtime;

import org.eclipse.tm.internal.terminal.provisional.api.provider.TerminalConnectorImpl;

public interface IConfigurationElement {

	String getAttribute(String string);

	TerminalConnectorImpl createExecutableExtension(String string);

	Contributor getContributor();

}
