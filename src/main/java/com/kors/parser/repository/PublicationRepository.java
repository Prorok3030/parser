package com.kors.parser.repository;

import com.kors.parser.model.Publication;
import com.kors.parser.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByUsers(List<UserEntity> users);
}
