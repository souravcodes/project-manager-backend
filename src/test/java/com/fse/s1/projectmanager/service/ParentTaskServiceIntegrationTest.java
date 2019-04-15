package com.fse.s1.projectmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.s1.projectmanager.ProjectManagerApplication;
import com.fse.s1.projectmanager.entity.ParentTaskEntity;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ProjectManagerApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
public class ParentTaskServiceIntegrationTest{

	@Autowired
	private IParentTaskService parentService;
	
	private long parentId = 99999;
	
	private ParentTaskEntity parentTask;
	
	@Before
	public void init(){
		parentTask = new ParentTaskEntity();
		parentTask.setParentTask("Parent-task");
	}

	@Test
	public void addParentTask(){
		this.parentTask.setParentId(parentId++);
		this.parentTask = parentService.addParentTask(parentTask);
		assertTrue(this.parentTask != null && this.parentTask.getParentId() != 0L);
	}

	@Test
	public void getAllParentTask() {
		this.addParentTask();
		List<ParentTaskEntity> parents = this.parentService.getAllParentTask();
		System.out.println();
		assertTrue(parents != null && parents.size() > 0);
	}

	@Test
	public void getParentTask(){
		this.addParentTask();
		ParentTaskEntity parent = parentService.getParentTask(this.parentTask.getParentId());
		assertEquals(parentTask.getParentTask(), parent.getParentTask());
	}

	@Test
	public void parentTaskExists(){
		this.addParentTask();
		boolean pt; 
		List<ParentTaskEntity> parents = parentService.getAllParentTask();
		if(parents != null && parents.size() == 1){
			pt = parentService.parentTaskExists(this.parentTask.getParentId());
		}else{
			this.addParentTask();
			pt = parentService.parentTaskExists(this.parentTask.getParentId());
		}
		assertEquals(true, pt);
	}
}
