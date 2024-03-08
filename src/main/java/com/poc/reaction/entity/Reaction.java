package com.poc.reaction.entity;

import com.poc.reaction.model.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Reaction {

    @Id
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long tweetId;

    @NotNull
    private Long tweetUserId;

    private ReactionType type;

    private Boolean vote;

    private String comment;

    private String createTimeStamp;
}
