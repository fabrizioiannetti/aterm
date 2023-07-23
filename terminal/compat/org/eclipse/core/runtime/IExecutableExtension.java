package org.eclipse.core.runtime;

public interface IExecutableExtension {
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException;

}
