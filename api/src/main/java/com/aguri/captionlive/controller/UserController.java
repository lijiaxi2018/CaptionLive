package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.UserRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Resp> getAllUsers() {
        return ResponseEntity.ok(Resp.ok(userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<Resp> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Resp.ok(userService.createUser(userRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(Resp.ok(userService.getUserById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteUser(@PathVariable Long id) {
        userService.getUserById(id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Resp.ok(userService.updateUser(id, userRequest)));
    }

    @GetMapping("/{userId}/accessibleProjects")
    public ResponseEntity<Resp> getAllAccessibleProjects(@PathVariable Long userId) {
        List<ProjectInfo> allAccessibleProjects = userService.getAllAccessibleProjects(userId);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @GetMapping("/{userId}/committedProjects")
    public ResponseEntity<Resp> getAllCommittedProjects(@PathVariable Long userId) {
        List<ProjectInfo> allAccessibleProjects = userService.getAllCommittedProjects(userId);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/{userId}/organizations")
    public ResponseEntity<Resp> getMyOrganizations(@PathVariable Long userId) {
        List<Organization> organizations = userService.getUserById(userId).getOrganizations().stream().sorted(Comparator.comparing(Organization::getOrganizationId)).toList();
        return ResponseEntity.ok(Resp.ok(organizations.stream().map(organization -> organizationService.getResp(organization)).toList()));
    }


    @PutMapping("/{userId}/description")
    @Operation(summary = "Update user description", description = "Update the description of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User description updated successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> updateDescription(
            @RequestBody HashMap<String, String> body,
            @PathVariable("userId") Long userId) {
        String description = body.get("description");
        String nickname = body.get("nickname");
        return ResponseEntity.ok(Resp.ok(userService.updateDescription(userId, description, nickname)));
    }

    @PutMapping("/{userId}/avatar")
    @Operation(summary = "Update user avatar", description = "Update the avatar of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User avatar updated successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> updateAvatar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body containing avatar information",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HashMap.class)))
            @RequestBody HashMap<String, String> body,
            @PathVariable("userId") Long userId) {
        Long avatarId = Long.valueOf(body.get("avatarId"));
        return ResponseEntity.ok(Resp.ok(userService.updateAvatar(userId, avatarId)));
    }

}
