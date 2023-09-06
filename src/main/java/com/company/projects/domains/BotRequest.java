package com.company.projects.domains;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 04/09/23 17:41
 * some-personal-projects
 */

@Entity(name = "bot_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String request;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @OneToOne
    private BotResponse botResponse;
}
