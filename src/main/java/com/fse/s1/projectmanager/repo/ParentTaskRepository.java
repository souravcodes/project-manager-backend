package com.fse.s1.projectmanager.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.s1.projectmanager.entity.ParentTaskEntity;

@Repository
public interface ParentTaskRepository extends CrudRepository<ParentTaskEntity, Long> {

	public ParentTaskEntity findByParentTask(String parentTask);
}
