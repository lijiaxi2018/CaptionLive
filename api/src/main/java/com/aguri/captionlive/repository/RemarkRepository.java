package com.aguri.captionlive.repository;

import com.aguri.captionlive.model.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
}
