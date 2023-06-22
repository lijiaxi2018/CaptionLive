package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.model.Membership;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.MembershipRepository;
import com.aguri.captionlive.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {
    @Autowired
    MembershipRepository membershipRepository;

    @Override
    public List<Long> getOrganizationLeaders(Long id) {
        return membershipRepository.findAllByOrganizationOrganizationIdAndPermissionIn(id, List.of(Membership.Permission.LEADER)).stream().map(Membership::getUser).map(User::getUserId).toList();
    }
}
