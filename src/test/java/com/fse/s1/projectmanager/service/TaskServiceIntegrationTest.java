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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.entity.UserEntity;
import com.fse.s1.projectmanager.to.SearchCriteria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProjectManagerApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceIntegrationTest{

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProjectService projectService;

	@Autowired
	private IParentTaskService parentService;

	@Autowired
	private ITaskService taskService;
	
	private long parentId = 99999;
	
	private TaskEntity task;
	
	@Before
	public void init(){
		UserEntity manager = new UserEntity();
		manager.setFirstName("manager-name");
		manager.setLastName("manager-surname");
		manager.setEmployeeId(123456);
		manager.setFirstName("manager-name");
		manager = userService.addUser(manager);
		
		ProjectEntity project = new ProjectEntity();
		project.setProject("Test-project");
		project.setPriority(19);
		project.setStartDate(new Date(System.currentTimeMillis()));
		project.setEndDate(new Date(System.currentTimeMillis() + (24*60*60*1000)));
		project.setManager(manager);
		project = projectService.addProject(project);
		
//		ParentTaskEntity parentTask = new ParentTaskEntity();
//		parentTask.setParentTask("Parent-task");
//		parentService.addParentTask(parentTask);
		
		task = new TaskEntity();
		task.setEndDate(new Date(System.currentTimeMillis()));
//		task.setParent(parentTask);
		task.setPriority(24);
		task.setProjectId(project);
		task.setStartDate(new Date(System.currentTimeMillis() + (24*60*60*1000)));
		task.setStatus("NEW");
		task.setTask("Test-task");
		task.setUserId(manager);
		
	}

	@Test
	public void saveTask(){
		ParentTaskEntity parentTask = new ParentTaskEntity();
		parentTask.setParentTask("Parent-task_" + parentId);
		parentTask.setParentId(parentId++);
		parentTask = parentService.addParentTask(parentTask);
		this.task.setParent(parentTask);
		this.task = taskService.saveTask(this.task);
		assertTrue(this.task != null && this.task.getTaskId() != 0L);
		this.task.toString();
		this.task.toJson();
	}
	
	@Test
	public void getTask(){
		this.saveTask();
		TaskEntity found = taskService.getTask(this.task.getTaskId());
		assertEquals(this.task.getTask(), found.getTask());
		assertEquals(this.task.getTaskId(), found.getTaskId());
		assertNotNull(found.getStartDate());
		assertNotNull(found.getEndDate());
		assertEquals(this.task.getPriority(), found.getPriority());
		assertEquals(this.task.getParentName(), found.getParentName());
	}

	@Test
	public void getAllTasks() {
		this.saveTask();
		this.saveTask();
		
		List<TaskEntity> tasks = this.taskService.getAllTasks();
		assertTrue(tasks != null && tasks.size() > 1);
	}
	
	@Test
	public void getFilteredTask(){
		this.saveTask();
		
		SearchCriteria criteria = new SearchCriteria();
		criteria.setTask(this.task.getTaskId() + "");
		
		List<TaskEntity> found = taskService.getFilteredTask(criteria);
		assertTrue(found != null && found.size() > 0);
		
		criteria = new SearchCriteria();
		criteria.setTask(this.task.getTask());
		
		found = taskService.getFilteredTask(criteria);
		assertTrue(found != null && found.size() > 0);
		
		criteria = new SearchCriteria();
		criteria.setPriorityTo(30);
		criteria.setPriorityFrom(0);
		
		found = taskService.getFilteredTask(criteria);
		assertTrue(found != null && found.size() > 0);
		
		criteria = new SearchCriteria();
		criteria.setParentTask(task.getParent().getParentTask());

		found = taskService.getFilteredTask(criteria);
		assertTrue(found != null && found.size() > 0);
		
		criteria = new SearchCriteria();
		criteria.setParentTask(task.getParent().getParentId() + "");

		found = taskService.getFilteredTask(criteria);
		assertTrue(found != null && found.size() > 0);
	}

	@Test
	public void updateTask(){
		this.saveTask();
		this.task.setStatus("completed");
		TaskEntity updatedTask = taskService.updateTask(this.task);
		assertEquals("completed", updatedTask.getStatus());
	}

	@Test
	public void getAllTasksByProjectId() {
		this.saveTask();
		
		ProjectEntity project = this.task.getProjectId();
		
		List<TaskEntity> tasks = this.taskService.getAllTasksByProjectId(project);
		assertTrue(tasks != null && tasks.size() > 0);
	}

	@Test
	public void deleteTask(){
		this.saveTask();
		this.taskService.deleteTask(this.task.getTaskId());
	}
}
