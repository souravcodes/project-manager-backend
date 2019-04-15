package com.fse.s1.projectmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=ProjectManagerApplication.class)
public class ProjectServiceUnitTest {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProjectService projectService;
	
	private ProjectEntity project;
	
	@Before
	public void init(){
		//Prerequisite to have a user added beforehand
		UserEntity manager = new UserEntity();
		manager.setFirstName("manager-name");
		manager.setLastName("manager-surname");
		manager.setEmployeeId(123456);
		userService.addUser(manager);
		
		project = new ProjectEntity();
		project.setProject("Test-project");
		project.setPriority(19);
		project.setStartDate(new Date(System.currentTimeMillis()));
		project.setEndDate(new Date(System.currentTimeMillis() + (24*60*60*1000)));
		project.setManager(manager);
	}

	@Test
	public void addProject() {
		project = this.projectService.addProject(project);
		assertTrue(project.getProjectId() != 0L);
	}
	
	@Test
	public void getAllProjects() {
		this.addProject();
		List<ProjectEntity> newProject = this.projectService.getAllProjects();
		assertTrue(newProject != null && newProject.size() > 0);
		ProjectEntity pt = newProject.get(0);
		assertNotNull(pt.getStartDate());
		assertNotNull(pt.getEndDate());
		assertNotNull(pt.getPriority());
		pt.toString();
	}

	@Test
	public void updateProject() {
		this.addProject();
		String newProjectName = this.project.getProject() + "_updated";
		this.project.setProject(newProjectName);
		ProjectEntity updatedProject = this.projectService.updateProject(project);
		assertEquals(updatedProject.getProject(), updatedProject.getProject());
	}

	@Test
	public void deleteProjects() {
		this.addProject();
		String response = this.projectService.deleteProjects(this.project.getProjectId());
		assertEquals("success", response);
	}

}
