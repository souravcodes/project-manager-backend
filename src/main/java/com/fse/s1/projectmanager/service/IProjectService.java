package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.entity.ProjectEntity;

public interface IProjectService {

	public List<ProjectEntity> getAllProjects();
	
	public ProjectEntity addProject(ProjectEntity project);
	
	public ProjectEntity updateProject(ProjectEntity project);
	
	public String deleteProjects(long projectId);
}
