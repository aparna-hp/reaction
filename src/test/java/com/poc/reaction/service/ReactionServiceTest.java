package com.poc.reaction.service;

import com.poc.reaction.model.CommentReactionDTO;
import com.poc.reaction.model.VoteReactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReactionServiceTest {

    @Autowired
    ReactionService reactionService;

    @Test
    public void testAddGetTweetReaction(){
        CommentReactionDTO tweetReaction = new CommentReactionDTO();
        tweetReaction.setTweetId(1L);
        tweetReaction.setUserId(1L);
        tweetReaction.setTweetUserId(2L);
        tweetReaction.setComment("My first tweet reaction.");

        reactionService.addCommentReaction(tweetReaction);

        tweetReaction = new CommentReactionDTO();
        tweetReaction.setTweetId(1L);
        tweetReaction.setUserId(1L);
        tweetReaction.setTweetUserId(2L);
        tweetReaction.setComment("My second tweet reaction.");

        reactionService.addCommentReaction(tweetReaction);

        Assertions.assertEquals(2, reactionService.getTweetComments(1L).size());

        VoteReactionDTO voteReactionDTO = new VoteReactionDTO();
        voteReactionDTO.setTweetId(1L);
        voteReactionDTO.setUserId(1L);
        voteReactionDTO.setTweetUserId(2L);
        voteReactionDTO.setVote(Boolean.TRUE);
        reactionService.addVoteReaction(voteReactionDTO);

        Assertions.assertEquals(1, reactionService.getTweetUpVote(1L));
        Assertions.assertEquals(0, reactionService.getTweetDownVote(1L));


        voteReactionDTO = new VoteReactionDTO();
        voteReactionDTO.setTweetId(1L);
        voteReactionDTO.setUserId(1L);
        voteReactionDTO.setTweetUserId(2L);
        voteReactionDTO.setVote(Boolean.FALSE);
        reactionService.addVoteReaction(voteReactionDTO);

        Assertions.assertEquals(0, reactionService.getTweetUpVote(1L));
        Assertions.assertEquals(1, reactionService.getTweetDownVote(1L));
    }
}
