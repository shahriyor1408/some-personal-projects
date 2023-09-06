package com.company.projects.repository;

import com.company.projects.domains.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 05/09/23 15:49
 * some-personal-projects
 */

public interface BotRepository extends JpaRepository<BotUser, Long> {

    @Query("from bot_users where username = :username")
    Optional<BotUser> findByUsername(String username);
}
