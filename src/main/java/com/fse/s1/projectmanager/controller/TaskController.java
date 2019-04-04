package com.fse.s1.projectmanager.controller;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.service.ITaskService;
import com.fse.s1.projectmanager.to.SearchCriteria;

@RestController
@RequestMapping(value="/task")
@CrossOrigin(origins="http://localhost:4200")
public class TaskController {

	@Autowired
	private ITaskService taskService;
	/**
	 * Get a specific task on the basis of task id.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<TaskEntity> getTaskDetailsById(@PathVariable(value="id") long id){
		
		TaskEntity task = taskService.getTask(id);
		if(task != null)
			return ResponseEntity.status(HttpStatus.OK).body(task);
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	/**
	 * To fetch all tasks.
	 * @return
	 */
	@RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<TaskEntity>> getAllTasks(){
		
		List<TaskEntity> task = taskService.getAllTasks();
		if(task != null)
			return ResponseEntity.status(HttpStatus.OK).body(task);
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	/**
	 * Get tasks on the basis of condition or filter.
	 * @param criteria
	 * @return
	 */
	@RequestMapping(value="/filtered", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<List<TaskEntity>> getTaskDetails(@RequestBody SearchCriteria criteria){
		
		List<TaskEntity> taskList = taskService.getFilteredTask(criteria);
		if(taskList != null && taskList.size() > 0)
			return ResponseEntity.status(HttpStatus.OK).body(taskList);
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LinkedList<>());
	}
	
	/**
	 * Add a task in database.
	 * @param task
	 * @return
	 * @throws URISyntaxException
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<TaskEntity> addTaskDetails(@RequestBody TaskEntity task){
		
		TaskEntity td = taskService.saveTask(task);
		if(td != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(task);
		else
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
	}
	
	/**
	 * Update a Task.
	 * @param task
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<TaskEntity> updateTaskDetails(@RequestBody TaskEntity task){
		
		TaskEntity td = taskService.updateTask(task);
		if(td == null)
			ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		return ResponseEntity.ok(td);
	}
	
	/**
	 * End a task or delete a task.
	 * @param task
	 * @return
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTaskDetails(@PathVariable(name="id") long id){
		taskService.deleteTask(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@RequestMapping(value="/project", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<List<TaskEntity>> taskByProject(@RequestBody ProjectEntity project){
		List<TaskEntity> tasks = taskService.getAllTasksByProjectId(project);
		if(tasks != null && tasks.size() > 0)
			return ResponseEntity.ok(tasks);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LinkedList<>());
	}
	
	/**
	 * Handle any SQLException during db operation.
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value=SQLException.class)
	public ResponseEntity<String> handleException(SQLException ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB exception occured: " + ex.getMessage());
	}
}
