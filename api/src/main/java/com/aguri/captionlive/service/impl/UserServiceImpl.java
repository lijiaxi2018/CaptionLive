package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.DTO.UserRequest;
import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.repository.*;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

//    @Autowired
//    private FileRecordService fileRecordService;

    @Override
    public User createUser(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
//        Long avatarId = userRequest.getAvatarId();
//        if (avatarId != null) {
//            FileRecord avatar = fileRecordService.getFileRecordById(avatarId);
//            user.setAvatar(avatar);
//        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        User user = getUserById(id);
        if (!Objects.equals(userRequest.getUsername(), user.getUsername())) {
            Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());
            if (existingUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }
        }
        FileRecord fileRecord = null;
        BeanUtils.copyProperties(userRequest, user);
        if (userRequest.getAvatarId() != 0L) {
            fileRecord = new FileRecord(userRequest.getAvatarId());
        }
        user.setAvatar(fileRecord);
        return userRepository.save(user);
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
