package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MembershipRepository extends JpaRepository<Membership,Long> {
}
