package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.UserRequest;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(UserRequest userRequest);

    User updateUser(Long id, UserRequest user);

    void deleteUser(Long id);

    User getUserByUsername(String username);

    List<User> getUsersByOrganizationId(Long organizationId);

    List<ProjectInfo> getAllAccessibleProjects(Long userId);

    List<ProjectInfo> getAllCommittedProjects(Long id);

    Object updateDescription(Long userId, String description, String nickname);

    Object updateAvatar(Long userId, Long avatarId);
}
