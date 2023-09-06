package com.company.projects.repository;

import com.company.projects.domains.BotRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 05/09/23 15:50
 * some-personal-projects
 */

public interface BotRequestRepository extends JpaRepository<BotRequest, Long> {

}
