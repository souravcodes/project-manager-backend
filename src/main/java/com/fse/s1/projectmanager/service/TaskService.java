package com.fse.s1.projectmanager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.entity.UserEntity;
import com.fse.s1.projectmanager.repo.TaskRepository;
import com.fse.s1.projectmanager.to.SearchCriteria;
import com.fse.s1.projectmanager.util.Util;

@Service
public class TaskService implements ITaskService{

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private IParentTaskService parentTaskService;
	
	@Autowired
	private IUserService userService;

	@Override
	public TaskEntity getTask(long id){
		TaskEntity task = taskRepository.findById(id).orElse(new TaskEntity());
		return task;
	}

	@Override
	public List<TaskEntity> getFilteredTask(SearchCriteria criteria){
		criteria = Util.convertObjectFieldsFromEmptyToNull(criteria);
		List<TaskEntity> taskList = new LinkedList<>();
		boolean isNumber = true;
		boolean parentIsNumber = true;
		try{
			Integer.parseInt(criteria.getTask());
		}catch(NumberFormatException e){
			isNumber = false;
		}
		int parentId = 0;
		try{
			parentId = Integer.parseInt(criteria.getParentTask());
		}catch(NumberFormatException e){
			parentIsNumber = false;
		}
		if(!parentIsNumber){
			ParentTaskEntity pt = parentTaskService.getParentByName(criteria.getParentTask());
			if(pt != null){
				parentId = new Long(pt.getParentId()).intValue();
			}else if(isNumber){
				parentId = -1;
			}
		}
		
		if(!isNumber){
		taskRepository
		.findTasksByTaskAndParentTask(criteria.getTask(), 
									  parentId, 
									  criteria.getPriorityFrom(),
									  criteria.getPriorityTo(),
									  criteria.getStartDate(),
									  criteria.getEndDate())
		.forEach(e -> {
						  UserEntity user = userService.getUserByTask(e);
						  e.setUserId(user);	
						  taskList.add(e);
					  });
		}else{
			TaskEntity task = this.getTask(Long.parseLong(criteria.getTask()));
			if(task != null && task.getTaskId() != 0){
				UserEntity user = userService.getUserByTask(task);
				task.setUserId(user);
				taskList.add(task);
			}
		}
		return taskList;
	}

	@Override
	public TaskEntity saveTask(TaskEntity taskDetails){
		if(parentExists(taskDetails)){
			TaskEntity td = taskRepository.save(taskDetails);
			if(td != null && td.getTaskId() != 0L){
				if(td.getUserId() != null){
					td.getUserId().setTaskId(td);
					userService.updateUserFromTask(td.getUserId());
				}
				return td;
			}
		}
		return null;
	}

	@Override
	public TaskEntity updateTask(TaskEntity taskDetails){
		if(taskDetails.getTaskId() == 0L)
			return null;
		if(parentExists(taskDetails)){
			if(taskDetails.getUserId() != null){
				UserEntity existinguser = userService.getUserByTask(taskDetails);
				if(existinguser != null){
					existinguser.setTaskId(null);
					userService.updateUserFromTask(existinguser);
				}
				taskDetails.getUserId().setTaskId(taskDetails);
				userService.updateUserFromTask(taskDetails.getUserId());
			}
			TaskEntity td = taskRepository.save(taskDetails);
			return td;
		}
		return null;
	}
	
	@Override
	public TaskEntity detachTaskFromProject(TaskEntity task){
		task.setProjectId(null);
		TaskEntity updatedTask = taskRepository.save(task);
		return updatedTask;
	}

	@Override
	public void deleteTask(long id){
		boolean exists = this.taskRepository.existsById(id);
		if(exists){
			TaskEntity t = new TaskEntity();
			t.setTaskId(id);
			UserEntity user = userService.getUserByTask(t);
			if(user != null){
				user.setTaskId(null);
				userService.updateUserFromTask(user);
			}
			
			this.taskRepository.deleteById(id);
		}
	}
	
	private boolean parentExists(TaskEntity taskDetails){
		if(taskDetails != null && taskDetails.getParent() != null){
			boolean existingParent = parentTaskService.parentTaskExists(taskDetails.getParent().getParentId());
			if(!existingParent){
				ParentTaskEntity pTask = parentTaskService.addParentTask(taskDetails.getParent());
				taskDetails.setParent(pTask);
			}
		}
		return true;
	}

	@Override
	public List<TaskEntity> getAllTasks() {
		List<TaskEntity> taskList = new LinkedList<>();
		this.taskRepository.findAll().forEach(data -> {
			taskList.add(data);
		});;
		return taskList;
	}

	@Override
	public List<TaskEntity> getAllTasksByProjectId(ProjectEntity projectId) {
		List<TaskEntity> tasks = this.taskRepository.findByProjectId(projectId);
		if(tasks != null && tasks.size() > 0){
			return tasks;
		}
		return new LinkedList<>();
	}

	@Override
	public TaskEntity endTask(TaskEntity taskDetails) {
		if(taskDetails.getTaskId() == 0L)
			return null;
		if(taskDetails.getUserId() != null){
			UserEntity existinguser = userService.getUserByTask(taskDetails);
			if(existinguser != null){
				existinguser.setTaskId(null);
				userService.updateUserFromTask(existinguser);
			}
		}
		TaskEntity td = taskRepository.save(taskDetails);
		return td;
	}
}
