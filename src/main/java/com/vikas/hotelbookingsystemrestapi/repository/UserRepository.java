package com.vikas.hotelbookingsystemrestapi.repository;

import com.vikas.hotelbookingsystemrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
