package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.Re4Orgs;
import com.aguri.captionlive.DTO.Re4Users;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.AccessRepository;
import com.aguri.captionlive.repository.OwnershipRepository;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.repository.TaskRepository;
import com.aguri.captionlive.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public ResponseEntity<Resp> getAllProjects() {
//        List<Project> projects = projectService.getAllProjects();
//        return ResponseEntity.ok(Resp.ok(projects));
//    }

    @GetMapping("/public")
    public ResponseEntity<Resp> getAllPublicProjects() {
        List<ProjectInfo> projects = projectService.getAllPublicProjects();
        return ResponseEntity.ok(Resp.ok(projects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getProjectById(@PathVariable Long id) {
        ProjectInfo projectInfo = ProjectInfo.generateProjectInfo(projectService.getProjectById(id));
        return ResponseEntity.ok(Resp.ok(projectInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(Resp.ok());
    }

    @GetMapping("/{projectIid}/users")
    public ResponseEntity<Resp> getAllAccessibleUsers(@PathVariable Long projectIid) {
        List<User> users = projectService.getAllAccessibleUsers(projectIid);
        return ResponseEntity.ok(Resp.ok(users));
    }

    @GetMapping("/{projectId}/organizations")
    public ResponseEntity<Resp> getAllAccessibleOrganizations(@PathVariable Long projectId) {
        List<Organization> organizations = projectService.getAllAccessibleOrganizations(projectId);
        return ResponseEntity.ok(Resp.ok(organizations));
    }

    @PutMapping("/{projectId}/organizations/{organizationId}")
    public ResponseEntity<Resp> shareProject2Organization(@PathVariable Long organizationId, @PathVariable Long projectId) {
        projectService.shareProject2Organization(projectId, organizationId);
        return ResponseEntity.ok(Resp.ok());
    }

    @PutMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Resp> shareProject2User(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.shareProject2User(projectId, userId);
        return ResponseEntity.ok(Resp.ok());
    }

//    @GetMapping("/{projectId}/segments")
//    public ResponseEntity<Resp> getAllSegments(@PathVariable Long projectId) {
//        List<Segment> segments = segmentService.getAllSegments(projectId);
//        return ResponseEntity.ok(Resp.ok(segments));
//    }
//

    @PutMapping("/{projectId}/cover/{coverId}")
    public ResponseEntity<Resp> updateCover(@PathVariable Long projectId, @PathVariable Long coverId) {
        Project updatedProject = projectService.updateCover(projectId, coverId);
        return ResponseEntity.ok(Resp.ok(updatedProject));
    }

    @Autowired
    UserService userService;

    /**
     * 创建项目和全局片段
     *
     * @param body 包含请求参数的Map，包括name、type和workflows
     * @return Resp对象表示响应结果
     */
    @PostMapping
    @Operation(summary = "Create project and global segment", description = "Create a new project and its global segment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project and segment created successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body containing name, type, and workflows",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class, requiredProperties = {"name", "type", "workflows"}),
                    examples = @ExampleObject(value = "{\"name\": \"TEST_PROJECT\", \"type\": \"AUDIO_AND_VIDEO\", \"workflows\":[\"TIMELINE\", \"SOURCE\"]}")
            )
    )
    public Resp createProject(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        // 从请求参数中获取name、type和workflows
        String name = (String) body.get("name");
        String type = (String) body.get("type");
        List<String> workflows = (List<String>) body.get("workflows");
        // 创建一个新的Project对象
        Project project = new Project();
        project.setName(name);
        project.setIsPublic(false);
        project.setType(Project.Type.valueOf(type));
        project = projectService.createProject(project);

        // 创建全局片段对象
        Segment globalSegment = new Segment();
        globalSegment.setIsGlobal(true);
        globalSegment.setProject(project);
        segmentService.saveSegment(globalSegment);

        // 根据workflows创建相应的Task对象并保存
        taskService.saveTasks(workflows.stream().map(workflow -> {
            Task task = new Task();
            task.setSegment(globalSegment);
            task.setStatus(Task.Status.NOT_ASSIGNED);
            task.setType(Task.Workflow.valueOf(workflow));
            return task;
        }).toList());
        String username = (String) request.getAttribute("username");
        Long userId = userService.getUserByUsername(username).getUserId();
        projectService.shareProject2UserWithPermission(project.getProjectId(), userId, Access.Permission.Creator);
        // 返回响应
        return Resp.ok(project);
    }

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;


    /**
     * 更新项目和全局片段
     *
     * @param projectId 项目ID
     * @param body      包含请求参数的Map，包括name、type和workflows
     * @return Resp对象表示响应结果
     */
    @PutMapping("/{projectId}")
    @Operation(summary = "Update project and global segment", description = "Update an existing project and its global segment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project and segment updated successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class))),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body containing name, type, and workflows",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class, requiredProperties = {"name", "type", "workflows"}),
                    examples = @ExampleObject(value = "{\"name\": \"UPDATED_PROJECT\", \"type\": \"AUDIO_AND_VIDEO\", \"workflows\":[\"TIMELINE\", \"SOURCE\"]}")
            )
    )
    public Resp updateProject(@PathVariable("projectId") Long projectId, @RequestBody Map<String, Object> body, HttpServletRequest request) {
        // 检查项目是否存在
        Project existingProject = projectRepository.getReferenceById(projectId);

        // 更新项目属性
        String name = (String) body.get("name");
        String type = (String) body.get("type");
        List<String> workflows = (List<String>) body.get("workflows");

        existingProject.setName(name);
        existingProject.setType(Project.Type.valueOf(type));
        existingProject = projectRepository.save(existingProject);

        // 全局片段
        Segment globalSegment = existingProject.getSegments().get(0);
        List<Task> tasks = globalSegment.getTasks();
        Set<Task.Workflow> newWorkFlowSet = workflows.stream().map(Task.Workflow::valueOf).collect(Collectors.toSet());
        // 更新任务
        // 删除任务
        List<Task> delTaskList = tasks.stream().filter(task -> !newWorkFlowSet.contains(task.getType())).toList();
        taskService.deleteAllInBatch(delTaskList);

        //新增任务
        Set<Task.Workflow> existingWorkflowSet = tasks.stream().map(Task::getType).collect(Collectors.toSet());
        List<Task> updatedTasks = workflows.stream().map(Task.Workflow::valueOf)
                .filter(workflow -> !existingWorkflowSet.contains(workflow))
                .map(workflow -> {
            Task task = new Task();
            task.setSegment(globalSegment);
            task.setStatus(Task.Status.NOT_ASSIGNED);
            task.setType(workflow);
            return task;
        }).toList();
        taskService.saveTasks(updatedTasks); // 保存更新后的任务

        // 返回响应
        return Resp.ok(existingProject);
    }

    @Autowired
    AccessRepository accessRepository;

    @GetMapping("/{projectId}/shareInfo/users")
    public Resp getAllUserPermissionByProject(@PathVariable Long projectId) {
        List<User> allUsers = userService.getAllUsers();
        List<Access> accessList = accessRepository.findAllByProjectProjectId(projectId);
        List<User> hasPermissionUserList = accessList.stream().map(Access::getUser).toList();
        Set<Long> hasPermissionSet = hasPermissionUserList.stream().map(User::getUserId).collect(Collectors.toSet());
        List<User> hasNoPermissionUserList = allUsers.stream().filter(user -> !hasPermissionSet.contains(user.getUserId())).toList();

        Re4Users re = new Re4Users();
        re.noSharedUserList = hasNoPermissionUserList;
        re.sharedUserList = hasPermissionUserList;

        return Resp.ok(re);
    }


    @Autowired
    OrganizationService organizationService;

    @Autowired
    OwnershipRepository ownershipRepository;

    @GetMapping("/{projectId}/shareInfo/organizations")
    public Resp getAllOrgPermissionByProject(@PathVariable Long projectId) {

        List<Organization> allOrganizations = organizationService.getAllOrganizations();
        List<Ownership> ownershipList = ownershipRepository.findAllByProjectProjectId(projectId);
        List<Organization> sharedOrganizationList = ownershipList.stream().map(Ownership::getOrganization).toList();
        Set<Long> sharedOrgSet = sharedOrganizationList.stream().map(Organization::getOrganizationId).collect(Collectors.toSet());
        List<Organization> noSharedOrganizationList = allOrganizations.stream().filter(organization -> !sharedOrgSet.contains(organization.getOrganizationId())).toList();

        Re4Orgs re = new Re4Orgs();
        re.noSharedOrganizationList = noSharedOrganizationList;
        re.sharedOrganizationList = sharedOrganizationList;

        return Resp.ok(re);
    }

}

