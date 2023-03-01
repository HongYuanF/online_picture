package com.hongyuan.onlinepic.repository;

import com.hongyuan.onlinepic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}