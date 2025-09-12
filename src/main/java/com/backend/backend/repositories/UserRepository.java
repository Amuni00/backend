package com.backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.backend.backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
