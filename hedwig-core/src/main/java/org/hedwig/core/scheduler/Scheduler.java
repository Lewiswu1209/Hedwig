package org.hedwig.core.scheduler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

public final class Scheduler {
	
	public static final int PERIOD_MINITE = 60;
	
	public static final int PERIOD_FIVE_MINITE = 5 * PERIOD_MINITE;
	
	public static final int PERIOD_QUARTER = 15 * PERIOD_MINITE;
	
	public static final int PERIOD_HALF_HOUR = 30 * PERIOD_MINITE;
	
	public static final int PERIOD_HOUR = 60 * PERIOD_MINITE;
	
	public static final int PERIOD_SIX_HOUR = 6 * PERIOD_HOUR;
	
	public static final int PERIOD_TWELVE_HOUR = 12 * PERIOD_HOUR;
	
	public static final int PERIOD_DAY = 24 * PERIOD_HOUR;
	
	private static final Timer timer = new Timer();

	private static final Map<String, TimerTask> taskList = new LinkedHashMap<String, TimerTask>();
	
	public static void schedule(TimerTask task) {
		Scheduler.schedule(task, 0);
	}
	
	public static void schedule(TimerTask task, long delay) {
		Scheduler.schedule(task, delay, 0);
	}
	
	public static void schedule(TimerTask task, long delay, long period) {
		Scheduler.timer.scheduleAtFixedRate(task, delay * 1000, period * 1000);
		addIntoList(task);
	}
	
	public static void schedule(TimerTask task, Date time) {
		Scheduler.schedule(task, time, 0);
	}
	
	public static void schedule(TimerTask task, Date firstTime, long period) {
		Scheduler.timer.scheduleAtFixedRate(task, firstTime, period * 1000);
		addIntoList(task);
	}
	
	private static void addIntoList(TimerTask task) {
		Scheduler.taskList.putIfAbsent(task.getTaskName(), task);
	}
	
	public static Map<String, TimerTask> getTaskList() {
		return Scheduler.taskList;
	}

	public static void cancel() {
		for (TimerTask task : Scheduler.taskList.values()) {
			task.cancel();
		}
		Scheduler.timer.cancel();
		Scheduler.timer.purge();
		Scheduler.taskList.clear();
	}

}
