package com.fse.s1.projectmanager.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.entity.UserEntity;
import com.fse.s1.projectmanager.repo.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserEntity addUser(UserEntity userEntity) {
		userEntity = userRepo.save(userEntity);
		if(userEntity != null && userEntity.getUserId() != 0L)
			return userEntity; 
		else
			return new UserEntity();
	}

	@Override
	public List<UserEntity> getAllUser() {
		List<UserEntity> userList = new LinkedList<>();
		/*userRepo.findAll().forEach(e -> {
			UserEntity ut = e; 
			userList.add(ut);
		});*/
		Iterator<UserEntity> itr = userRepo.findAll().iterator();
		while(itr.hasNext()){
			userList.add(itr.next());
		}
		return userList;
	}

	@Override
	public UserEntity updateUser(UserEntity userEntity) {
		UserEntity user = this.userRepo.findById(userEntity.getUserId()).orElse(new UserEntity());
		if(user != null && user.getUserId() != 0L){
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setEmployeeId(userEntity.getEmployeeId());
			userEntity = this.userRepo.save(user);
			return userEntity; 
		}
		return new UserEntity();
	}
	
	@Override
	public UserEntity updateUserFromProject(UserEntity userEntity) {
		UserEntity user = this.userRepo.findById(userEntity.getUserId()).orElse(new UserEntity());
		if(user != null && user.getUserId() != 0L){
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setEmployeeId(userEntity.getEmployeeId());
			user.setProjectId(userEntity.getProjectId());
			userEntity = this.userRepo.save(user);
			return userEntity; 
		}
		return new UserEntity();
	}
	
	@Override
	public UserEntity updateUserFromTask(UserEntity userEntity) {
		UserEntity user = this.userRepo.findById(userEntity.getUserId()).orElse(new UserEntity());
		if(user != null && user.getUserId() != 0L){
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setEmployeeId(userEntity.getEmployeeId());
			user.setTaskId(userEntity.getTaskId());
			userEntity = this.userRepo.save(user);
			return userEntity; 
		}
		return new UserEntity();
	}

	@Override
	public String deleteUser(long id) {
		boolean exists = this.userRepo.existsById(id);
		if(exists)
			this.userRepo.deleteById(id);
		else
			return "User already deleted";
		return "success";
	}

	@Override
	public UserEntity getUserByProject(ProjectEntity project) {
		return userRepo.findByProjectId(project); 
	}
	
	@Override
	public UserEntity getUserByTask(TaskEntity project) {
		return userRepo.findByTaskId(project); 
	}

	@Override
	public UserEntity getUserById(long id) {
		return userRepo.findById(id).orElse(new UserEntity());
	}

}
