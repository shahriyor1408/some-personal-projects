package com.company.projects.domains;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 04/09/23 17:35
 * some-personal-projects
 */

@Entity(name = "bot_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "is_duty")
    private Boolean isDuty;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "activity")
    private Boolean activity;
}
