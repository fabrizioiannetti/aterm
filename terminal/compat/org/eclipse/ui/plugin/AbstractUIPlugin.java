package org.eclipse.ui.plugin;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.osgi.framework.BundleContext;

public abstract class AbstractUIPlugin {

	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	protected void initializeImageRegistry(ImageRegistry imageRegistry) {
		// TODO Auto-generated method stub

	}

	public Bundle getBundle() {
		return new Bundle();
	}

	public IPreferenceStore getPreferenceStore() {
		// TODO@fab
		return new PreferenceStore();
	}

	public Log getLog() {
		return new Log();
	}

	public ImageRegistry getImageRegistry() {
		// TODO@fab
		return JFaceResources.getImageRegistry();
	}
}
