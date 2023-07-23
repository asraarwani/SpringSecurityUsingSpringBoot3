package com.jpmchase.www.repository;

import com.jpmchase.www.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
}
