package com.notbadcode.todo.task;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDao {

  private final Session session;

  public TaskDao(EntityManager entityManager) {
    this.session = entityManager.unwrap(Session.class);
  }

  public Task save(Task task){
    session.persist(task);
    return task;
  }

  public Optional<Task> findById(Long id) {
    return Optional.ofNullable(session.find(Task.class, id));
  }

  public List<Task> findAll() {
    return session.createQuery("FROM Task t ORDER BY t.created DESC", Task.class)
        .getResultList();
  }

  public List<Task> findAll(boolean isCompleted) {
    return session.createQuery("FROM Task t " +
            "WHERE t.completed = :completed " +
            "ORDER BY t.created DESC", Task.class)
        .setParameter("completed", isCompleted)
        .getResultList();
  }

  public boolean existActive() {
    return session.createQuery("FROM Task t " +
            "WHERE t.completed = false", Task.class)
        .setMaxResults(1)
        .uniqueResult() != null;
  }

  public long count() {
    return session.createQuery("SELECT count(t) " +
            "FROM Task t ", Long.class)
        .getSingleResult();
  }

  public long count(boolean isCompleted) {
    return session.createQuery("SELECT count(t) " +
            "FROM Task t " +
            "WHERE t.completed = :completed", Long.class)
        .setParameter("completed", isCompleted)
        .getSingleResult();
  }

  public void delete(Task task) {
    session.remove(task);
  }

  public void deleteAll(List<Task> tasks) {
    tasks.forEach(session::remove);
  }

}
