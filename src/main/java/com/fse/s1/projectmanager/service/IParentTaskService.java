package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.entity.ParentTaskEntity;

public interface IParentTaskService {

	public ParentTaskEntity getParentTask(long id);
	
	public List<ParentTaskEntity> getAllParentTask();
	
	public boolean parentTaskExists(long pt);
	
	public ParentTaskEntity addParentTask(ParentTaskEntity parentTask);
	
	public ParentTaskEntity getParentByName(String name);
}
