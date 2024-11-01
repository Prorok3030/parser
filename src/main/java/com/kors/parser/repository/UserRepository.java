package com.kors.parser.repository;

import com.kors.parser.model.Publication;
import com.kors.parser.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);
    Boolean existsByLogin(String username);
}
