package com.jpmchase.www.repository;

import com.jpmchase.www.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByUserName(String userName);
}
