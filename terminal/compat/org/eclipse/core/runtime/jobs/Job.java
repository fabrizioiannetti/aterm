package org.eclipse.core.runtime.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

public class Job {

	private Thread runner;
	private String name;
	private IProgressMonitor monitor = new NullProgressMonitor() {
		private boolean canceled;

		public void setCanceled(boolean cancelled) {
			this.canceled = cancelled;
		};

		public boolean isCanceled() {
			return canceled;
		};
	};

	public Job(String name) {
		this.name = name;
		this.runner = new Thread(() -> {
			run(monitor);
		});
	}

	public void cancel() {
		monitor.setCanceled(true);
	}

	public Thread getThread() {
		return runner;
	}

	public void join() throws InterruptedException {
		if (runner != null)
			runner.join();
	}

	protected IStatus run(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSystem(boolean b) {
	}

	public void schedule() {
		runner.start();
	}
}
