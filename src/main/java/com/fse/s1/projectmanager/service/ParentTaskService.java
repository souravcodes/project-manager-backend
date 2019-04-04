package com.fse.s1.projectmanager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.repo.ParentTaskRepository;

@Service
public class ParentTaskService implements IParentTaskService{

	@Autowired
	private ParentTaskRepository parentTaskRepository;
	
	
	@Override
	public ParentTaskEntity getParentTask(long id){
		return parentTaskRepository.findById(id).orElse(new ParentTaskEntity());
	}

	@Override
	public ParentTaskEntity parentTaskExists(String pt){
		return parentTaskRepository.findByParentTask(pt);
	}

	@Override
	public ParentTaskEntity addParentTask(ParentTaskEntity parentTask){
		ParentTaskEntity pt = this.parentTaskExists(parentTask.getParentTask());
		if(pt != null && pt.getParentId() != 0L)
			return pt;
		return parentTaskRepository.save(parentTask);
	}
	
	@Override
	public ParentTaskEntity getParentByName(String name){
		return parentTaskRepository.findByParentTask(name);
	}

	@Override
	public List<ParentTaskEntity> getAllParentTask() {
		List<ParentTaskEntity> parentList = new LinkedList<>();
		this.parentTaskRepository.findAll().forEach(e -> {
			parentList.add(e);
		});
		return parentList;
	}
}
