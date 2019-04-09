package com.fse.s1.projectmanager.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="T_PROJECT_DETAILS")
public class ProjectEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PD_PROJECT_ID")
	private long projectId;

	@Column(name="PD_PROJECT")
	private String project;

	@Column(name="PD_START_DATE")
	private Date startDate;

	@Column(name="PD_END_DATE")
	private Date endDate;

	@Column(name="PD_PRIORITY")
	private int priority;

	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="userId")
	@OneToOne(fetch=FetchType.EAGER, mappedBy="projectId")
	private UserEntity manager;
	
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="taskId")
	@OneToMany(fetch=FetchType.EAGER, mappedBy="projectId")
	private List<TaskEntity> tasks;
	
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;	
	}

	@Override
	public String toString() {
		return "ProjectEntity [projectId=" + projectId + ", project=" + project + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + "]";
	}

	public UserEntity getManager() {
		return manager;
	}

	public void setManager(UserEntity manager) {
		this.manager = manager;
	}

	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}
}
