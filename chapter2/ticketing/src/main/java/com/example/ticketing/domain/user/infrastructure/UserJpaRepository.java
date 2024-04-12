package com.example.ticketing.domain.user.infrastructure;

import com.example.ticketing.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {
}
