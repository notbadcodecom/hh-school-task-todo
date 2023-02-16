package com.notbadcode.todo.task;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

  List<Task> findAll(Specification<Task> spec, Sort sort);

  long count(Specification<Task> spec);

  boolean existsAllByCompletedIsFalse();

}
