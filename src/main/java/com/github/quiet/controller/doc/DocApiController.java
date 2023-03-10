/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.controller.doc;

import com.github.quiet.annotation.ExistId;
import com.github.quiet.convert.doc.DocApiConvert;
import com.github.quiet.convert.doc.DocApiGroupConvert;
import com.github.quiet.convert.doc.DocApiInfoConvert;
import com.github.quiet.dto.doc.DocApiDTO;
import com.github.quiet.entity.doc.DocApi;
import com.github.quiet.entity.doc.DocApiGroup;
import com.github.quiet.entity.doc.DocApiInfo;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.manager.doc.DocApiManager;
import com.github.quiet.repository.doc.DocProjectRepository;
import com.github.quiet.result.BatchResult;
import com.github.quiet.result.Result;
import com.github.quiet.service.doc.DocApiGroupService;
import com.github.quiet.service.doc.DocApiInfoService;
import com.github.quiet.service.doc.DocApiService;
import com.github.quiet.service.system.QuietUserService;
import com.github.quiet.utils.ObjectUtils;
import com.github.quiet.utils.UserUtil;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.doc.DocApiVO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ????????????.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DocApiController {

  private final DocApiService apiService;
  private final DocApiManager apiManager;
  private final DocApiGroupService apiGroupService;
  private final DocApiInfoService apiInfoService;
  private final DocApiConvert apiConvert;
  private final DocApiInfoConvert apiInfoConvert;
  private final DocApiGroupConvert apiGroupConvert;
  private final QuietUserService userService;

  /**
   * ????????????ID???????????????????????????????????????
   *
   * @param projectId ??????ID
   * @param name ????????????
   * @param limit ?????????????????????????????????0???????????????????????????
   * @return ????????????
   */
  @GetMapping("/list")
  public Result<List<DocApiVO>> list(
      @RequestParam Long projectId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long limit) {
    List<DocApi> docApis = apiService.listByProjectIdAndName(projectId, name, limit);
    return Result.success(apiConvert.entities2vos(docApis));
  }

  /**
   * ?????????????????????api_group_id ???0???????????? api_group_id is null ????????????
   *
   * @param dto ????????????
   * @return ????????????
   */
  @GetMapping("/page")
  public Result<Page<DocApiVO>> page(DocApiDTO dto) {
    Page<DocApi> page = apiService.page(apiConvert.dto2entity(dto), dto.page());
    return Result.success(apiConvert.page2page(page));
  }

  /**
   * ????????????????????????
   *
   * @param id ??????ID
   * @return ??????????????????
   */
  @GetMapping("/detail/{id}")
  public Result<DocApiVO> getDetail(@PathVariable Long id) {
    DocApiVO docApiVO = apiConvert.entity2vo(apiService.getById(id));
    if (docApiVO.getApiGroupId() != null) {
      DocApiGroup apiGroup = apiGroupService.findById(docApiVO.getApiGroupId());
      if (apiGroup != null) {
        docApiVO.setApiGroup(apiGroupConvert.entity2vo(apiGroup));
      }
    }
    Set<Long> userIds = new HashSet<>();
    userIds.add(docApiVO.getAuthorId());
    userIds.add(docApiVO.getCreator());
    userIds.add(docApiVO.getUpdater());
    Map<Long, QuietUser> userId2Info =
        userService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, user -> user));
    if (userId2Info.get(docApiVO.getCreator()) != null) {
      docApiVO.setCreatorFullName(userId2Info.get(docApiVO.getCreator()).getFullName());
    }
    if (userId2Info.get(docApiVO.getUpdater()) != null) {
      docApiVO.setUpdaterFullName(userId2Info.get(docApiVO.getUpdater()).getFullName());
    }
    if (userId2Info.get(docApiVO.getAuthorId()) != null) {
      docApiVO.setAuthorFullName(userId2Info.get(docApiVO.getAuthorId()).getFullName());
    }
    DocApiInfo apiInfo = apiInfoService.getByApiId(id);
    if (apiInfo != null) {
      docApiVO.setApiInfo(apiInfoConvert.entity2vo(apiInfo));
    }
    return Result.success(docApiVO);
  }

  /**
   * ??????????????????????????????
   *
   * @param projectId ??????ID
   * @param apis ????????????
   * @return ????????????
   */
  @Valid
  @PostMapping("/batch/{projectId}")
  public Result<BatchResult> batch(
      @PathVariable
          @ExistId(repository = DocProjectRepository.class, message = "{project.id.not.exist}")
          Long projectId,
      @RequestBody List<DocApiDTO> apis) {
    BatchResult result = new BatchResult();
    if (CollectionUtils.isEmpty(apis)) {
      return Result.success(result);
    }
    List<DocApi> allApi = apiService.listAllByProjectId(projectId);
    Map<String, Long> groupName2Id =
        apiGroupService.listByProjectId(projectId).stream()
            .collect(Collectors.toMap(DocApiGroup::getName, DocApiGroup::getId));
    // ?????? ${????????????}:${????????????} ????????????????????? api
    String keyPattern = "%s:%s";
    Map<String, DocApiDTO> key2newInfo = new HashMap<>(apis.size());
    for (DocApiDTO api : apis) {
      api.setProjectId(projectId);
      String groupName = api.getGroupName();
      Long groupId = groupName2Id.get(groupName);
      if (StringUtils.isNotBlank(groupName) && groupId == null) {
        DocApiGroup apiGroup = new DocApiGroup();
        apiGroup.setProjectId(projectId);
        String newGroupName = StringUtils.substring(groupName, 0, 30);
        apiGroup.setName(newGroupName);
        DocApiGroup save = apiGroupService.save(apiGroup);
        groupId = save.getId();
        groupName2Id.put(newGroupName, groupId);
      }
      api.setApiGroupId(groupId);
      key2newInfo.put(String.format(keyPattern, api.getPath(), api.getMethod()), api);
    }
    Set<Long> apiIds = new HashSet<>(allApi.size());
    Map<String, DocApi> key2oldInfo = new HashMap<>(allApi.size());
    allApi.forEach(
        api -> {
          apiIds.add(api.getId());
          key2oldInfo.put(String.format(keyPattern, api.getPath(), api.getMethod()), api);
        });
    Map<Long, DocApiInfo> apiId2Info = new HashMap<>();
    if (CollectionUtils.isNotEmpty(apiIds)) {
      apiId2Info =
          apiInfoService.listByApiIds(apiIds).stream()
              .collect(Collectors.toMap(DocApiInfo::getApiId, info -> info));
    }
    Iterator<Map.Entry<String, DocApiDTO>> iterator = key2newInfo.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, DocApiDTO> entry = iterator.next();
      DocApiDTO newDocApi = entry.getValue();
      DocApi docApi = key2oldInfo.get(entry.getKey());
      if (null == docApi) {
        continue;
      }
      ObjectUtils.copyPropertiesIgnoreNull(newDocApi, docApi);
      DocApiInfo apiInfo = apiId2Info.get(docApi.getId());
      if (apiInfo == null) {
        continue;
      }
      ObjectUtils.copyPropertiesIgnoreNull(newDocApi.getApiInfo(), apiInfo);
      apiInfo.setReqFile(newDocApi.getApiInfo().getReqFile());
      apiInfo.setReqForm(newDocApi.getApiInfo().getReqForm());
      apiInfo.setReqJsonBody(newDocApi.getApiInfo().getReqJsonBody());
      apiInfo.setReqQuery(newDocApi.getApiInfo().getReqQuery());
      apiInfo.setRespRaw(newDocApi.getApiInfo().getRespRaw());
      apiInfo.setRespJsonBody(newDocApi.getApiInfo().getRespJsonBody());
      newDocApi.setApiInfo(null);
      iterator.remove();
    }
    if (MapUtils.isNotEmpty(key2newInfo)) {
      Set<String> authors =
          key2newInfo.values().stream().map(DocApiDTO::getAuthor).collect(Collectors.toSet());
      List<QuietUser> users = userService.findByUsernames(authors);
      Map<String, Long> username2Id =
          users.stream().collect(Collectors.toMap(QuietUser::getUsername, QuietUser::getId));
      key2newInfo.forEach(
          (key, newApi) -> {
            newApi.setAuthorId(username2Id.getOrDefault(newApi.getAuthor(), 0L));
            DocApi save = apiService.save(apiConvert.dto2entity(newApi));
            DocApiInfo docApiInfo = apiInfoConvert.dto2entity(newApi.getApiInfo());
            docApiInfo.setApiId(save.getId());
            apiInfoService.saveOrUpdate(docApiInfo);
          });
      result.setAddNum(key2newInfo.size());
    }
    apiService.saveAll(key2oldInfo.values());
    result.setUpdateNum(key2oldInfo.size());
    apiInfoService.saveAll(apiId2Info.values());
    return Result.success(result);
  }

  /**
   * ????????????
   *
   * @param dto ?????????????????????
   * @return ?????????????????????
   */
  @PostMapping
  public Result<DocApiVO> save(@RequestBody @Validated(Create.class) DocApiDTO dto) {
    dto.setAuthorId(UserUtil.getId());
    DocApi save = apiService.save(apiConvert.dto2entity(dto));
    return Result.createSuccess(apiConvert.entity2vo(save));
  }

  /**
   * ??????????????????
   *
   * @param dto ?????????????????????
   * @return ????????????????????????
   */
  @PutMapping
  public Result<DocApiVO> update(@RequestBody @Validated(Update.class) DocApiDTO dto) {
    DocApi update = apiService.update(apiConvert.dto2entity(dto));
    return Result.updateSuccess(apiConvert.entity2vo(update));
  }

  /**
   * ????????????ID??????????????????
   *
   * @param id ??????????????????ID
   * @return ????????????
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    apiManager.deleteById(id);
    return Result.deleteSuccess();
  }
}
