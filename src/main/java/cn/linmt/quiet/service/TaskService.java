package cn.linmt.quiet.service;

import cn.linmt.quiet.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public void deleteByRequirementId(Long id) {
    repository.deleteByRequirementId(id);
  }
}
