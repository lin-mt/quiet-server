package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.project.vo.Member;
import cn.linmt.quiet.controller.project.vo.ProjectDetail;
import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.service.ProjectService;
import cn.linmt.quiet.service.ProjectUserService;
import cn.linmt.quiet.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectManager {

  private final ProjectService projectService;
  private final ProjectUserService projectUserService;
  private final UserService userService;

  public ProjectDetail detail(Long id) {
    Project project = projectService.getById(id);
    ProjectDetail detail = new ProjectDetail();
    BeanUtils.copyProperties(project, detail);
    Set<Long> memberIds = projectUserService.listUserIds(id);
    if (CollectionUtils.isNotEmpty(memberIds)) {
      List<User> users = userService.listById(memberIds);
      List<Member> members = new ArrayList<>();
      for (User user : users) {
        Member member = new Member();
        member.setId(user.getId());
        member.setUsername(user.getUsername());
        members.add(member);
      }
      detail.setMembers(members);
    }
    return detail;
  }

  public void delete(Long id) {
    projectService.deleteById(id);
    projectUserService.deleteByProjectId(id);
  }
}
