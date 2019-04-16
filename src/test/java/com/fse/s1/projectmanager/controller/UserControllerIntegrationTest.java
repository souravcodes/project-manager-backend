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
import com.fse.s1.projectmanager.entity.UserEntity;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProjectManagerApplication.class)
@SpringBootTest
public class UserControllerIntegrationTest {

	@Autowired
	private UserController controller;

	private MockMvc mockMvc;
	private UserEntity user;

	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		user = new UserEntity();
		user.setFirstName("sourav");
		user.setLastName("sinha");
		user.setEmployeeId(123456);
		user.setProjectId(null);
		user.setTaskId(null);
	}

	@Test
	public void addUser(){
		RequestBuilder request = MockMvcRequestBuilders.post("/user/add")
				.content(getValueAsString(user))
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
			this.user = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
					UserEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAllUser(){
		RequestBuilder request = MockMvcRequestBuilders.get("/user/").accept(MediaType.APPLICATION_JSON);
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
	public void getUserById(){
		this.addUser();
		RequestBuilder request = MockMvcRequestBuilders.get("/user/" + this.user.getUserId())
				.content(getValueAsString(user))
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
	public void getUnAssignedUsers(){
		RequestBuilder request = MockMvcRequestBuilders.get("/user/unassigned").accept(MediaType.APPLICATION_JSON);
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
	public void updateUser(){
		this.addUser();
		this.user.setFirstName(this.user.getFirstName() + "_updated");
		RequestBuilder request = MockMvcRequestBuilders.put("/user/update")
				.content(getValueAsString(this.user))
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
	public void deleteUser(){
		this.addUser();
		RequestBuilder request = MockMvcRequestBuilders.delete("/user/delete/" + this.user.getUserId());
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

	/*@RequestMapping(value="/project", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<UserEntity> getUserByProject(@RequestBody ProjectEntity project){
		UserEntity user = this.userService.getUserByProject(project);
		if(user != null && user.getUserId() != 0L){
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@RequestMapping(value="/update/project", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<UserEntity> updateUserFromProject(@RequestBody UserEntity userEntity){
		UserEntity user = this.userService.updateUserFromProject(userEntity);
		if(user != null && user.getUserId() != 0L){
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}*/

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
