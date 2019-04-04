package com.fse.s1.projectmanager.to;

import java.io.Serializable;

public class SearchCriteria implements Serializable{

	private static final long serialVersionUID = 7400328091014684906L;
	
	private String task = "";
	private String parentTask = "0";
	private int priorityFrom = 0;
	private int priorityTo = 0;
	private String startDate = "";
	private String endDate = "";

	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getParentTask() {
		return parentTask;
	}
	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}
	public int getPriorityFrom() {
		return priorityFrom;
	}
	public void setPriorityFrom(int priorityFrom) {
		this.priorityFrom = priorityFrom;
	}
	public int getPriorityTo() {
		return priorityTo;
	}
	public void setPriorityTo(int priorityTo) {
		this.priorityTo = priorityTo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
