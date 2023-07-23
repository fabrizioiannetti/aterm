package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.keys.IBindingService;

public class Workbench implements IWorkbench, IAdaptable {

	public HelpSystem getHelpSystem() {
		// TODO@fab
		return new HelpSystem();
	}

	public Display getDisplay() {
		// TODO@fab
		return Display.getDefault();
	}

	public <T> T getAdapter(Class<T> adapter) {
		if (adapter.equals(IContextService.class)) {
			return (T) new IContextService() {

				@Override
				public void deactivateContext(IContextActivation terminalContextActivation) {
					// TODO Auto-generated method stub

				}

				@Override
				public IContextActivation activateContext(String string) {
					// TODO Auto-generated method stub
					return null;
				}
			};
		} else if (adapter.equals(IBindingService.class)) {
			return (T) new IBindingService() {

				@Override
				public void setKeyFilterEnabled(boolean enableKeyFilter) {
					// TODO Auto-generated method stub

				}

				@Override
				public boolean isKeyFilterEnabled() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public Binding getPerfectMatch(KeySequence instance) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public TriggerSequence[] getActiveBindingsFor(String commandId) {
					// TODO Auto-generated method stub
					return new TriggerSequence[0];
				}
			};		}
		// TODO Auto-generated method stub
		return null;
	}

	public ISharedImages getSharedImages() {
		return new ISharedImages() {

			@Override
			public String getImageDescriptor(String imgName) {
				// TODO@fab
				return null;
			}
		};
	}

}
