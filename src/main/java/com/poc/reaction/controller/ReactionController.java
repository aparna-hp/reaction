package com.poc.reaction.controller;

import com.poc.reaction.entity.Reaction;
import com.poc.reaction.model.CommentReactionDTO;
import com.poc.reaction.model.VoteReactionDTO;
import com.poc.reaction.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@Validated
@RequestMapping(path = {"/api/v1/tweeter/tweets/{tweetId}/reactions"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReactionController {

    @Autowired
    ReactionService reactionService;

    @Operation(summary = "Add a comment to the tweet.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet comment is added", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "500", description = "Error creating the tweet comment.", content = @Content)})
    @PostMapping(path = "/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addTweetComment(@PathVariable(value = "tweetId") Long tweetId,
                                                  @Valid @RequestBody CommentReactionDTO reaction){
        reaction.setTweetId(tweetId);
        Reaction response = reactionService.addCommentReaction(reaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Add a comment to the tweet.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet vote is added", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "500", description = "Error creating the tweet vote.", content = @Content)})
    @PostMapping(path = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> voteTweet(@PathVariable(value = "tweetId") Long tweetId,
                                            @Valid @RequestBody VoteReactionDTO reaction){
        reaction.setTweetId(tweetId);
        Reaction response = reactionService.addVoteReaction(reaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get tweet up vote by tweet Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet up vote obtained successfully.", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "204", description = "No tweet was found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error getting the tweet.", content = @Content)})
    @GetMapping(path = "/up-vote")
    public ResponseEntity<Object> getTweetUpVote(@PathVariable(value = "tweetId") Long tweetId){
        int response = reactionService.getTweetUpVote(tweetId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get tweet down vote by tweet Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet down vote obtained successfully.", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "204", description = "No tweet was found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error getting the tweet.", content = @Content)})
    @GetMapping(path = "/down-vote")
    public ResponseEntity<Object> getTweetDownVote(@PathVariable(value = "tweetId") Long tweetId){
        int response = reactionService.getTweetDownVote(tweetId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get tweet comments by tweet Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet comments obtained successfully.", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "204", description = "No tweet was found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error getting the tweet.", content = @Content)})
    @GetMapping(path = "/comments")
    public ResponseEntity<Object> getTweetComments(@PathVariable(value = "tweetId") Long tweetId){
        List<Reaction> response = reactionService.getTweetComments(tweetId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete tweet by Id.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tweet comment deleted successfully.", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Reaction.class))}),
            @ApiResponse(responseCode = "204", description = "No tweet was found.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error deleting the tweet comment.", content = @Content)})
    @DeleteMapping
    public ResponseEntity<Object> deleteTweetComment(@RequestParam(value = "id") Long id){
        reactionService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
