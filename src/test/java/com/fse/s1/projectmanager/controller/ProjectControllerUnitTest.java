package com.fse.s1.projectmanager.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=ProjectManagerApplication.class)
public class ProjectControllerUnitTest {

	@Autowired
	private ProjectController controller;
	
	private MockMvc mockMvc;
	
	private ProjectEntity project;

	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		UserEntity manager = new UserEntity();
		manager.setUserId(1);
		manager.setFirstName("manager-name");
		manager.setLastName("manager-surname");
		manager.setEmployeeId(123456);
		
		project = new ProjectEntity();
		project.setProject("Test-project");
		project.setPriority(19);
		project.setStartDate(new Date(System.currentTimeMillis()));
		project.setEndDate(new Date(System.currentTimeMillis() + (24*60*60*1000)));
		project.setManager(manager);
	}
	
	@Test
	public void getAllProjects(){
		this.addProject();
		RequestBuilder request = MockMvcRequestBuilders.get("/project/all").accept(MediaType.APPLICATION_JSON);
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
	public void addProject(){
		RequestBuilder request = MockMvcRequestBuilders.post("/project/add")
				.content(getValueAsString(project))
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
			this.project = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
					ProjectEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateProject(){
		this.project.setStartDate(null);
		this.project.setEndDate(null);
		this.addProject();
		this.project.setProject(this.project.getProject() + "_updated");
		RequestBuilder request = MockMvcRequestBuilders.put("/project/update")
				.content(getValueAsString(this.project))
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
		assertTrue((result.getResponse().getStatus() == HttpStatus.OK.value()) 
				|| (result.getResponse().getStatus() == HttpStatus.NOT_MODIFIED.value()));
	}

	@Test
	public void deleteProjects(){
		this.addProject();
		RequestBuilder request = MockMvcRequestBuilders.delete("/project/delete/" + this.project.getProjectId());
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
		
		request = MockMvcRequestBuilders.delete("/project/delete/" + 999);
		result = null;
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
