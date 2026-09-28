package com.spp.demo.modules.tasks;

import java.util.List;

public interface TaskService {
  public List<Task> findAllUniqueIds();

  public List<Task> findAllTasksForSectionList();

  public Task findByUniqueId(String uniqueId);
}
