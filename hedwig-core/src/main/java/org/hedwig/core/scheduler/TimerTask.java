package org.hedwig.core.scheduler;

public abstract class TimerTask extends java.util.TimerTask {

	private String taskName = null;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public boolean cancel() {
		onCancel();
		return super.cancel();
	}
	
	public void onCancel() {
	}

}
