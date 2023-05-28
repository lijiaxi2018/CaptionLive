package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.UserInfoResponse;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private FileRecordService fileRecordService;

    @Autowired
    private ProjectRepository projectRepository;

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

    @Override
    public User createUser(User user) {
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
    public List<User> getUsersByProjectId(Long projectId) {
        List<Access> accesses = accessRepository.findAllByProjectId(projectId);
        List<Long> userIds = accesses.stream().map(Access::getUserId).toList();
        return userRepository.findAllById(userIds);
    }


    @Override
    public User uploadAvatar(Long id, MultipartFile file) {
        User user = getUserById(id);
        if (!file.isEmpty()) {
            try {
                FileRecord fileRecord = fileRecordService.saveSmallSizeFile(file, "avatar" + File.separator + "user" + File.separator + id.toString());
                user.setAvatar(fileRecord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateUser(id, user);
    }

    @Override
    public List<Project> getAllAccessibleProjects(Long userId) {
        List<Access> accesses = accessRepository.findAllByUserId(userId);
        List<Long> userIds = accesses.stream().map(Access::getUserId).toList();
        return projectRepository.findAllById(userIds);
    }

    @Override
    public List<Project> getAllCommittedProjects(Long id) {
        List<Access> accesses = accessRepository.findAllByUserIdAndCommitment(id, Access.Commitment.COMMITTED);
        List<Long> userIds = accesses.stream().map(Access::getUserId).toList();
        return projectRepository.findAllById(userIds);
    }

    @Override
    public UserInfoResponse getUserInfoById(Long userId) {
        User user = getUserById(userId);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(user, userInfoResponse);
        return userInfoResponse;
    }
}
