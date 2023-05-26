package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Access;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Membership;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.AccessRepository;
import com.aguri.captionlive.repository.MembershipRepository;
import com.aguri.captionlive.repository.UserRepository;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private FileRecordService fileRecordService;

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
        List<Membership> memberships = membershipRepository.findAllByOrganizationId(organizationId);
        List<Long> userIds = memberships.stream().map(Membership::getUserId).toList();
        return userRepository.findAllById(userIds);
    }


    @Override
    public List<User> getUsersByProjectId(Long projectId) {
        List<Access> accesses = accessRepository.findAllByProjectId(projectId);
        List<Long> userIds = accesses.stream().map(Access::getUserId).toList();
        return userRepository.findAllById(userIds);
    }



    @Override
    public User saveAvatarToStorage(Long id, MultipartFile file) {
        System.out.println(file);
        User user = getUserById(id);
        if (!file.isEmpty()) {
            try {
                FileRecord fileRecord = fileRecordService.saveFile(file, id.toString());
                user.setAvatar(fileRecord.getFileRecordId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateUser(id, user);
    }
}
