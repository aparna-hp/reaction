package com.poc.reaction.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteReactionDTO {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long tweetId;

    @NotNull
    private Long tweetUserId;

    private Boolean vote;
}
