package com.fse.s1.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.service.ParentTaskService;

@RestController
@RequestMapping(value="/task/parent")
@CrossOrigin(origins="*")
public class ParentTaskController {

	@Autowired
	private ParentTaskService parentTaskService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ParentTaskEntity> getParentById(@PathVariable(name="id") long id){
		ParentTaskEntity pt = parentTaskService.getParentTask(id);
		if(pt != null){
			return ResponseEntity.ok(pt);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<List<ParentTaskEntity>> getAllParentTask(){
		List<ParentTaskEntity> pt = parentTaskService.getAllParentTask();
		if(pt != null){
			return ResponseEntity.ok(pt);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<ParentTaskEntity> addParentTask(@RequestBody ParentTaskEntity parentTask){
		ParentTaskEntity newParent = this.parentTaskService.addParentTask(parentTask);
		if(newParent != null && newParent.getParentId() != 0L){
			return ResponseEntity.status(HttpStatus.CREATED).body(newParent);
		}else{
			return ResponseEntity.noContent().build();
		}
	}
}
