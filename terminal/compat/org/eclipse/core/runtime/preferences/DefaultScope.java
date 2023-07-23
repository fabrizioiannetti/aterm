package org.eclipse.core.runtime.preferences;

public class DefaultScope {

	public static final DefaultScope INSTANCE = new DefaultScope();

	public IEclipsePreferences getNode(String pluginId) {
		// TODO Auto-generated method stub
		return new IEclipsePreferences() {

			@Override
			public void putInt(String prefBufferlines, int defaultBufferlines) {
				// TODO Auto-generated method stub

			}

			@Override
			public void putBoolean(String prefInvertColors, boolean defaultInvertColors) {
				// TODO Auto-generated method stub

			}

			@Override
			public void put(String prefFontDefinition, String defaultFontDefinition) {
				// TODO Auto-generated method stub

			}
		};
	}

}
