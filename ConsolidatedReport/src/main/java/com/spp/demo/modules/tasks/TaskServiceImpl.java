package com.spp.demo.modules.tasks;

import com.spp.demo.base.exception.CRDataNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  public List<Task> findAllUniqueIds() {
    return taskRepository.findAllUniqueIds();
  }

  @Override
  public List<Task> findAllTasksForSectionList() {
    return taskRepository.findAllTasksForSectionList();
  }

  @Override
  public Task findByUniqueId(String uniqueId) {
    return taskRepository
        .findByUniqueId(uniqueId)
        .orElseThrow(
            () -> new CRDataNotFoundException("No Task found for given uniqueId: " + uniqueId));
  }
}
