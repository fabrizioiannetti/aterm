package org.eclipse.core.runtime.preferences;

public interface IEclipsePreferences {

	void putBoolean(String prefInvertColors, boolean defaultInvertColors);

	void putInt(String prefBufferlines, int defaultBufferlines);

	void put(String prefFontDefinition, String defaultFontDefinition);

}
