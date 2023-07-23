package org.eclipse.ui.keys;

import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;

public interface IBindingService {

	boolean isKeyFilterEnabled();

	void setKeyFilterEnabled(boolean enableKeyFilter);

	Binding getPerfectMatch(KeySequence instance);

	TriggerSequence[] getActiveBindingsFor(String commandId);

}
