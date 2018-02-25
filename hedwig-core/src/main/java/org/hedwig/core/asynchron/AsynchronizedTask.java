package org.hedwig.core.asynchron;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AsynchronizedTask extends TimerTask {
	
	private Timer timer = new Timer();

	public final void schedule() {
		this.timer.schedule(this, new Date());
	}

}
