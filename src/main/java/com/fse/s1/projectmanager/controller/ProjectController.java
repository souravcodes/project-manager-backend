package com.fse.s1.projectmanager.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.service.IProjectService;

@RestController
@RequestMapping(value="/project")
@CrossOrigin("*")
public class ProjectController {

	@Autowired
	private IProjectService projectService;
	
	@RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<ProjectEntity>> getAllProjects(){
		List<ProjectEntity> projects = projectService.getAllProjects();
		if(projects != null && projects.size() > 0){
			return ResponseEntity.ok(projects);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<ProjectEntity> addProject(@RequestBody ProjectEntity project){
		if(project.getStartDate() == null)
			project.setStartDate(new Date(System.currentTimeMillis()));
		if(project.getEndDate() == null)
			project.setEndDate(new Date(System.currentTimeMillis() + 86400000));
		ProjectEntity ProjectEntity = this.projectService.addProject(project);
		if(ProjectEntity != null && ProjectEntity.getProjectId() != 0L){
			return ResponseEntity.status(HttpStatus.CREATED).body(ProjectEntity);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<ProjectEntity> updateProject(@RequestBody ProjectEntity project){
		ProjectEntity ProjectEntity = this.projectService.updateProject(project);
		if(ProjectEntity != null && ProjectEntity.getProjectId() != 0L){
			return ResponseEntity.ok(ProjectEntity);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}
	
	@RequestMapping(value="/delete/{projectId}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteProjects(@PathVariable(name="projectId") long projectId){
		String response = this.projectService.deleteProjects(projectId);
		if(response != null && response.equalsIgnoreCase("success")){
			return ResponseEntity.status(HttpStatus.OK).build();
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
}
