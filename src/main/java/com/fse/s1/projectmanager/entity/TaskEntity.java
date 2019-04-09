package com.fse.s1.projectmanager.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="T_TASK_DETAILS")
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TD_TASK_ID")
	private long taskId;

	@JoinColumn(name="TD_PARENT_ID", nullable=true)
	@ManyToOne(fetch=FetchType.EAGER)
	private ParentTaskEntity parent;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TD_PROJECT_ID")
	private ProjectEntity projectId;
	
	@Column(name="TD_TASK", nullable=false)
	private String task;
	
	@Column(name="TD_START_DATE", nullable=true)
	private Date startDate;
	
	@Column(name="TD_END_DATE", nullable=true)
	private Date endDate;

	@Column(name="TD_PRIORITY", nullable=true)
	private int priority;
	
	@Column(name="TD_STATUS")
	private String status;

	@JsonManagedReference(value="taskId")
	@OneToOne(fetch=FetchType.EAGER, mappedBy="taskId")
	private UserEntity userId;
	
	private transient String parentName;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ParentTaskEntity getParent() {
		return parent;
	}

	public void setParent(ParentTaskEntity parent) {
		this.parent = parent;
	}

	public String getParentName() {
		if(this.parent != null)
			return this.parent.getParentTask();
		return null;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public ProjectEntity getProjectId() {
		return projectId;
	}

	public void setProjectId(ProjectEntity projectId) {
		this.projectId = projectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toJson() {
		return "{'taskId':" + taskId + ", 'task':" + task + ", 'parent':" + parent + ", 'priority':" + priority
				+ ", 'startDate':" + startDate + ", 'endDate':" + endDate + ", 'parentName':" + parentName + "}";
	}

	@Override
	public String toString() {
		return "TaskEntity [taskId=" + taskId + ", parent=" + parent + ", projectId=" + projectId + ", task=" + task
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", priority=" + priority + ", status=" + status
				+ ", parentName=" + parentName + "]";
	}
}
