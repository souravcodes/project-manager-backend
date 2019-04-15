package com.fse.s1.projectmanager.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

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
import com.fse.s1.projectmanager.entity.ParentTaskEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=ProjectManagerApplication.class)
public class ParentTaskControllerUnitTest {

	@Autowired
	private ParentTaskController controller;
	
	private MockMvc mockMvc;
	private ParentTaskEntity parentTask;
	
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		parentTask = new ParentTaskEntity();
		parentTask.setParentId(999999);
		parentTask.setParentTask("Parent-task");
	}
	
	@Test
	public void getParentById(){
		this.addParentTask();
		RequestBuilder request = MockMvcRequestBuilders.get("/task/parent/" + this.parentTask.getParentId())
				.content(getValueAsString(parentTask))
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
	public void getAllParentTask(){
		this.addParentTask();
		RequestBuilder request = MockMvcRequestBuilders.get("/task/parent/all").accept(MediaType.APPLICATION_JSON);
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
	public void addParentTask(){
		
		RequestBuilder request = MockMvcRequestBuilders.post("/task/parent/add")
				.content(getValueAsString(parentTask))
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
			this.parentTask = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
					ParentTaskEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
