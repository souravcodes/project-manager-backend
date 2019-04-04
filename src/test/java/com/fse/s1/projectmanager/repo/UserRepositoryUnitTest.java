package com.fse.s1.projectmanager.repo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.s1.projectmanager.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	private UserEntity user;
	
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
	public void whenAddUser(){
		user = userRepository.save(user);
		Assert.assertTrue(user.getUserId() != 0L);
	}
	
	@Test
	public void whenfetchUser(){
		this.entityManager.persist(user);
		entityManager.flush();
		
		UserEntity found = userRepository.findById(user.getUserId()).orElse(new UserEntity());
		Assert.assertEquals(user.getFirstName(), found.getFirstName());
		Assert.assertEquals(user.getLastName(), found.getLastName());
		Assert.assertEquals(user.getEmployeeId(), found.getEmployeeId());
		Assert.assertEquals(user.getProjectId(), found.getProjectId());
		Assert.assertEquals(user.getTaskId(), found.getTaskId());
		
		System.out.println(user.toString());
	}
	
}
