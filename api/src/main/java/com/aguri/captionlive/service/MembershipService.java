package com.aguri.captionlive.service;

import java.util.List;

public interface MembershipService {
    List<Long> getOrganizationLeaders(Long id);
}
