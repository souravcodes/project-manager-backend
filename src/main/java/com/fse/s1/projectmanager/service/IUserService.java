package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.entity.UserEntity;

public interface IUserService {

	public UserEntity addUser(UserEntity userEntity);
	public List<UserEntity> getAllUser();
	public UserEntity updateUser(UserEntity userEntity);
	public String deleteUser(long id);
	public UserEntity getUserByProject(ProjectEntity projectEntity);
	public UserEntity getUserByTask(TaskEntity project);
	public UserEntity updateUserFromProject(UserEntity userEntity);
	public UserEntity updateUserFromTask(UserEntity userEntity);
	public UserEntity getUserById(long id);
}
