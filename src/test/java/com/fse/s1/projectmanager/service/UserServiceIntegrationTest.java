package com.fse.s1.projectmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.UserEntity;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes ={ProjectManagerApplication.class})
@ActiveProfiles("test")
@EnableConfigurationProperties
public class UserServiceIntegrationTest {
	
	private UserEntity user;
	
	@Autowired
	private IUserService userService;
	
	@Before
	public void Init(){
		user = new UserEntity();
		user.setFirstName("sourav");
		user.setLastName("sinha");
		user.setEmployeeId(123456);
		user.setProjectId(null);
		user.setTaskId(null);
	}
	
	@Test
	public void addUser() {
		user = userService.addUser(user);
		assertTrue(user.getUserId() != 0L);
		getUserById(user.getUserId());
	}

	@Test
	public void getAllUser() {
		List<UserEntity> users = userService.getAllUser();
		assertNotNull(users);
		assertTrue(users.size() > 0);
	}

	@Test
	public void updateUser() {
		this.addUser();
		String newFirstName = user.getFirstName()+ "_updated";
		user.setFirstName(newFirstName);
		UserEntity updatedUser = userService.updateUser(user);
		assertEquals(user.getUserId(), updatedUser.getUserId());
		assertEquals(newFirstName, updatedUser.getFirstName());
	}

	/*@Test
	public void updateUserFromProject() {
		this.addUser();
		UserEntity updatedUser = userService.updateUserFromProject(user);
		assertEquals(user.getUserId(), updatedUser.getUserId());
	}

	@Test
	public void updateUserFromTask() {
		this.addUser();
		UserEntity updatedUser = userService.updateUserFromTask(user);
		assertEquals(user.getUserId(), updatedUser.getUserId());
	}*/

	public void getUserById(long id) {
		UserEntity found = userService.getUserById(id);
		
		assertEquals(user.getFirstName(), found.getFirstName());
		assertEquals(user.getLastName(), found.getLastName());
		assertEquals(user.getEmployeeId(), found.getEmployeeId());
		assertEquals(user.getProjectId(), found.getProjectId());
		assertEquals(user.getTaskId(), found.getTaskId());
		System.out.println(user.toString());
	}
	
	@Test
	public void deleteUser() {
		this.addUser();
		String response = userService.deleteUser(this.user.getUserId());
		assertEquals("success", response);
	}
	
	/*@Test
	public void getUserByProject() {
		this.addUser();
		UserEntity found = userService.getUserByProject(null); 
		assertEquals(user.getUserId(), found.getUserId());
	}

	@Test
	public void getUserByTask() {
		this.addUser();
		UserEntity found = userService.getUserByTask(null); 
		assertEquals(user.getUserId(), found.getUserId());
	}*/

	
/*
	@TestConfiguration
	static class UserServiceTestConfiguration {
		
		@Bean
		public IUserService userService(){
			return new UserService();
		}
	}*/
}
