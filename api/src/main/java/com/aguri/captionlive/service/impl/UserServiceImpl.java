package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.UserCreateRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Autowired
    private FileRecordService fileRecordService;

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        BeanUtils.copyProperties(userCreateRequest, user);
        Long avatarId = userCreateRequest.getAvatarId();
        if (avatarId != null) {
            FileRecord avatar = fileRecordService.getFileRecordById(avatarId);
            user.setAvatar(avatar);
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Override
    public List<User> getUsersByOrganizationId(Long organizationId) {
        return organizationService.getOrganizationById(organizationId).getUsers();
    }

    @Override
    public List<Project> getAllAccessibleProjects(Long userId) {
        return getUserById(userId).getAccessibleProjects();
    }

    @Override
    public List<Project> getAllCommittedProjects(Long id) {
        return accessRepository.findAllByUserUserIdAndCommitment(id, Access.Commitment.COMMITTED).stream().map(Access::getProject).toList();
    }
}
