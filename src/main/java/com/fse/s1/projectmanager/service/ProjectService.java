package com.fse.s1.projectmanager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.entity.UserEntity;
import com.fse.s1.projectmanager.repo.ProjectRepository;

@Service
public class ProjectService implements IProjectService{

	@Autowired
	private ProjectRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITaskService taskService; 
	
	@Override
	public List<ProjectEntity> getAllProjects() {
		List<ProjectEntity> projects = new LinkedList<>();
		repo.findAll().forEach(e -> {
//			UserEntity manager = userService.getUserByProject(e);
//			e.setManager(manager);
			projects.add(e);
		});
		return projects;
	}

	@Override
	public ProjectEntity addProject(ProjectEntity projectEntity) {
		projectEntity = this.repo.save(projectEntity);
		if(projectEntity != null && projectEntity.getProjectId() != 0L){
			if(projectEntity.getManager() != null){
				projectEntity.getManager().setProjectId(projectEntity);
				this.userService.updateUserFromProject(projectEntity.getManager());
			}
			return projectEntity;
		}
		return new ProjectEntity();
	}

	@Override
	public ProjectEntity updateProject(ProjectEntity projectEntity) {
		boolean exists = this.repo.existsById(projectEntity.getProjectId());
		if(exists){
			this.repo.save(projectEntity/*Util.exchangeUserAndUserTo(projectEntity, project)*/);
			if(projectEntity.getManager() != null){
				UserEntity existinguser = userService.getUserByProject(projectEntity);
				if(existinguser != null){
					existinguser.setProjectId(null);
					userService.updateUserFromProject(existinguser);
				}
				projectEntity.getManager().setProjectId(projectEntity);
				userService.updateUserFromProject(projectEntity.getManager());
			}
		}
		return projectEntity;
	}

	@Override
	public String deleteProjects(long projectId) {
		boolean exists = this.repo.existsById(projectId);
		if(exists){
			ProjectEntity p = new ProjectEntity();
			p.setProjectId(projectId);
			UserEntity manager = userService.getUserByProject(p);
			if(manager != null){
				manager.setProjectId(null);
				userService.updateUserFromProject(manager);
			}
			List<TaskEntity> tasks = taskService.getAllTasksByProjectId(p);
			tasks.parallelStream().forEach(t -> {
				t.setProjectId(null);
				taskService.detachTaskFromProject(t);
			});
			
			this.repo.deleteById(projectId);
			return "success";
		}
		return "missing";
	}

}
