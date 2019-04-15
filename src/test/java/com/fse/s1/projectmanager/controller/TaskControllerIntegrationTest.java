package com.fse.s1.projectmanager.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.to.SearchCriteria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProjectManagerApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
public class TaskControllerIntegrationTest {

	@Autowired
	private TaskController controller;
	
	private TaskEntity task;
	private MockMvc mockMvc;

	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		ParentTaskEntity parentTask = new ParentTaskEntity();
		parentTask.setParentTask("Parent-task");
		
		task = new TaskEntity();
		task.setEndDate(new Date(System.currentTimeMillis()));
		task.setParent(parentTask);
		task.setPriority(24);
		task.setProjectId(null);
		task.setStartDate(new Date(System.currentTimeMillis() + (24*60*60*1000)));
		task.setStatus("NEW");
		task.setTask("Test-task");
		task.setUserId(null);
		
	}

	@Test
	public void addTaskDetails(){
		RequestBuilder request = MockMvcRequestBuilders.post("/task/add")
				.content(getValueAsString(task))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.CREATED.value());
		try {
			this.task = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
					TaskEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTaskDetailsById(){
		this.addTaskDetails();
		RequestBuilder request = MockMvcRequestBuilders.get("/task/" + this.task.getTaskId())
				.content(getValueAsString(task))
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
	}
	
	@Test
	public void getAllTasks(){
		
		RequestBuilder request = MockMvcRequestBuilders.get("/task/all").accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue((result.getResponse().getStatus() == HttpStatus.OK.value()) 
				|| (result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value()));
	}
	
	@Test
	public void getTaskDetails(){
		this.addTaskDetails();
		
		SearchCriteria criteria = new SearchCriteria();
		criteria.setTask(this.task.getTaskId() + "");
		RequestBuilder request = MockMvcRequestBuilders.put("/task/filtered")
				.content(getValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
		
		criteria = new SearchCriteria();
		criteria.setTask(this.task.getTask());
		request = MockMvcRequestBuilders.put("/task/filtered")
				.content(getValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
		
		criteria = new SearchCriteria();
		criteria.setPriorityTo(30);
		criteria.setPriorityFrom(0);
		request = MockMvcRequestBuilders.put("/task/filtered")
				.content(getValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
		
		criteria = new SearchCriteria();
		criteria.setParentTask(task.getParent().getParentId() + "");
		request = MockMvcRequestBuilders.put("/task/filtered")
				.content(getValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
	}
	
	@Test
	public void updateTaskDetails(){
		this.addTaskDetails();
		
		this.task.setStatus("completed");
		RequestBuilder request = MockMvcRequestBuilders.put("/task/update")
				.content(getValueAsString(task))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
	}
	
	@Test
	public void deleteTaskDetails(){
		this.addTaskDetails();
		RequestBuilder request = MockMvcRequestBuilders.delete("/task/delete/" + this.task.getTaskId());
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void endTask(){
		this.addTaskDetails();
		
		RequestBuilder request = MockMvcRequestBuilders.put("/task/end", this.task)
				.content(getValueAsString(task))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);;
		MvcResult result = null;
		try {
			result = mockMvc.perform(request).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
		assertTrue(result.getResponse() != null);
		assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
	}
	
	/*@RequestMapping(value="/project", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<List<TaskEntity>> taskByProject(@RequestBody ProjectEntity project){
		List<TaskEntity> tasks = taskService.getAllTasksByProjectId(project);
		if(tasks != null && tasks.size() > 0)
			return ResponseEntity.ok(tasks);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LinkedList<>());
	}*/
	
	/**
	 * Handle any SQLException during db operation.
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value=SQLException.class)
	public ResponseEntity<String> handleException(SQLException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB exception occured: " + ex.getMessage());
	}
	

	private String getValueAsString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
