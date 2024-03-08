package com.poc.reaction.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CommentReactionDTO {

    @Id
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long tweetId;

    @NotNull
    private Long tweetUserId;

    private String comment;
}
