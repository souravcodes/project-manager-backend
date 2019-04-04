package com.fse.s1.projectmanager.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.UserEntity;
import com.fse.s1.projectmanager.service.IUserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity UserEntity){
		UserEntity user = userService.addUser(UserEntity);
		if(user != null && user.getUserId() != 0L){
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user);
		}
	}

	@RequestMapping(value="/", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<UserEntity>> getAllUser(){
		List<UserEntity> usersList = userService.getAllUser();
		if(usersList != null && usersList.size() > 0){
			return ResponseEntity.ok(usersList);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<UserEntity> getUserById(@PathVariable(name="id") long id){
		UserEntity usersList = userService.getUserById(id);
			return ResponseEntity.ok(usersList);
	}
	
	@RequestMapping(value="/unassigned", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<UserEntity>> getUnAssignedUsers(){
		List<UserEntity> usersList = userService.getAllUser();
		if(usersList != null && usersList.size() > 0){
			usersList = usersList.parallelStream().filter(data -> (data.taskId == null)).collect(Collectors.toList());
			return ResponseEntity.ok(usersList);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@RequestMapping(value="/update", method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity){
		UserEntity user = this.userService.updateUser(userEntity);
		if(user != null && user.getUserId() != 0L){
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") long id){
		this.userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@RequestMapping(value="/project", method=RequestMethod.PUT, consumes="application/json")
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
	}
}
