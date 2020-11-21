package application;

import java.util.Date;

public class ProductivityTask extends Task {

	private TaskPriority priority;
	private int targetTime;
	private int actualTime;

	public ProductivityTask(int id, String title, Date date, String note) {
		super(id, title, date, note);
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public int getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(int targetTime) {
		this.targetTime = targetTime;
	}

	public int getActualTime() {
		return actualTime;
	}

	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}
}