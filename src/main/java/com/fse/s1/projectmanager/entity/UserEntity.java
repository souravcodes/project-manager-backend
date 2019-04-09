package com.fse.s1.projectmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="T_USER_DETAILS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UD_USER_ID")
	public long userId;

	@Column(name="UD_FIRST_NAME")
	public String firstName;

	@Column(name="UD_LAST_NAME")
	public String lastName;

	@Column(name="UD_EMPLOYEE_ID")
	public long employeeId;

	@JsonBackReference(value="projectId")
	@OneToOne
	@JoinColumn(name="UD_PROJECT_ID")
	public ProjectEntity projectId;

	@JsonBackReference(value="taskId")
	@OneToOne
	@JoinColumn(name="UD_TASK_ID")
	public TaskEntity taskId;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public ProjectEntity getProjectId() {
		return projectId;
	}
	public void setProjectId(ProjectEntity projectId) {
		this.projectId = projectId;
	}
	public TaskEntity getTaskId() {
		return taskId;
	}
	public void setTaskId(TaskEntity taskId) {
		this.taskId = taskId;
	}
	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", employeeId="
				+ employeeId + ", projectId=" + projectId + ", taskId=" + taskId + "]";
	}
}
