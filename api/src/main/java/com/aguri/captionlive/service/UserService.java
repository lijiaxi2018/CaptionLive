package com.aguri.captionlive.service;

import com.aguri.captionlive.DTO.UserCreateRequest;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(UserCreateRequest userCreateRequest);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByUsername(String username);

    List<User> getUsersByOrganizationId(Long organizationId);

    List<Project> getAllAccessibleProjects(Long userId);

    List<Project> getAllCommittedProjects(Long id);
}
