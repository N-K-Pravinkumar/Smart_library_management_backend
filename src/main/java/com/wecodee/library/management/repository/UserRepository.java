package com.wecodee.library.management.repository;

import com.wecodee.library.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
