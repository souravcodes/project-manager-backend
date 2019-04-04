package com.fse.s1.projectmanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fse.s1.projectmanager.entity.ProjectEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

	@Query(value="Select * from T_TASK_DETAILS p WHERE"
			+ " upper(p.TD_TASK) = IFNULL(upper(:taskName),upper(p.TD_TASK)) "
			+ " AND ((p.TD_PARENT_ID > 0 AND p.TD_PARENT_ID = :parentTask) OR (:parentTask=0)) "
			+ " AND ((p.TD_PRIORITY >= :priorityFrom AND p.TD_PRIORITY <= :priorityTo) "
			+ "			OR (:priorityFrom = 0 AND :priorityTo = 0))"
			+ " AND ((p.TD_START_DATE >= :startDate AND p.TD_END_DATE <= :endDate)"
			+ "OR (:startDate IS NULL AND :endDate IS NULL))",nativeQuery=true)
	public List<TaskEntity> findTasksByTaskAndParentTask(@Param("taskName") String taskName,
												   @Param("parentTask") int parentTask,
												   @Param("priorityFrom") int priorityFrom,
												   @Param("priorityTo") int priorityTo,
												   @Param("startDate") String startDate,
												   @Param("endDate") String endDate);
	
	public List<TaskEntity> findByProjectId(ProjectEntity projectId);
}
