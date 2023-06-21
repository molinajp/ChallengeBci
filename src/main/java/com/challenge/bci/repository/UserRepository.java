package com.challenge.bci.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.bci.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

}
