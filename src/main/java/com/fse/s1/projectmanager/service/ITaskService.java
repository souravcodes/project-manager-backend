package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.to.SearchCriteria;

public interface ITaskService {

	public TaskEntity getTask(long id);
	
	public List<TaskEntity> getFilteredTask(SearchCriteria criteria);

	public TaskEntity saveTask(TaskEntity taskDetails);

	public TaskEntity updateTask(TaskEntity taskDetails);

	public void deleteTask(long id);
	
	public List<TaskEntity> getAllTasks();
	
	public List<TaskEntity> getAllTasksByProjectId(ProjectEntity projectId);
	
	public TaskEntity detachTaskFromProject(TaskEntity task);
	
	public TaskEntity endTask(TaskEntity taskDetails);
}
