package com.poc.reaction.repository;

import com.poc.reaction.entity.Reaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends CrudRepository<Reaction, Long> {

    List<Reaction> findByTweetId(Long tweetId);

    @Query("SELECT ID, TWEET_ID, COMMENT, CREATE_TIME_STAMP FROM REACTION WHERE TWEET_ID = :tweetId AND TYPE = 'COMMENT' ;")
    List<Reaction> findCommentsByTweetId(Long tweetId);

    @Query("SELECT ID FROM REACTION WHERE TWEET_ID = :tweetId AND TYPE = 'VOTE' and USER_ID = :userId ;")
    Optional<Long> findVoteByUserId(Long tweetId, Long userId);

    @Query("SELECT COUNT(ID) FROM REACTION WHERE TWEET_ID = :tweetId AND TYPE = 'VOTE' and VOTE = TRUE;")
    int findUpVoteByTweetId(Long tweetId);

    @Query("SELECT COUNT(ID) FROM REACTION WHERE TWEET_ID = :tweetId AND TYPE = 'VOTE' and VOTE = FALSE ;")
    int findDownVoteByTweetId(Long tweetId);
}
