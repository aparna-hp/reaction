package com.poc.reaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.reaction.entity.Reaction;
import com.poc.reaction.kafka.MessageProducer;
import com.poc.reaction.model.CommentReactionDTO;
import com.poc.reaction.model.ReactionType;
import com.poc.reaction.model.VoteReactionDTO;
import com.poc.reaction.repository.ReactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReactionService {

    @Autowired
    ReactionRepository tweetRepository;

    @Autowired
    MessageProducer messageProducer;

    @Value("${app.tweet.reaction.topic}")
    String topic;

    public Reaction addCommentReaction(CommentReactionDTO reactionDto){
        Reaction reaction = new Reaction();
        BeanUtils.copyProperties(reactionDto, reaction);
        reaction.setType(ReactionType.COMMENT);
        reaction.setCreateTimeStamp(System.currentTimeMillis()+"");
        return tweetRepository.save(reaction);
    }

    public Reaction addVoteReaction(VoteReactionDTO voteReactionDTO) {
        Optional<Long> reactionId = tweetRepository.findVoteByUserId(voteReactionDTO.getTweetId(),
                voteReactionDTO.getUserId());
        reactionId.ifPresent(voteReactionDTO::setId);

        Reaction reaction = new Reaction();
        BeanUtils.copyProperties(voteReactionDTO, reaction);
        reaction.setType(ReactionType.VOTE);
        reaction.setCreateTimeStamp(System.currentTimeMillis()+"");
        return tweetRepository.save(reaction);
    }

    public List<Reaction> getTweetComments(Long tweetId){
        return tweetRepository.findCommentsByTweetId(tweetId);
    }

    public int getTweetUpVote(Long tweetId){
         return tweetRepository.findUpVoteByTweetId(tweetId);
    }

    public int getTweetDownVote(Long tweetId){
         return tweetRepository.findDownVoteByTweetId(tweetId);
    }

    public void deleteComment(Long commentId){
        tweetRepository.deleteById(commentId);
    }

    private void sendTweetMessage(Reaction reaction){
        ObjectMapper objectMapper = new ObjectMapper();
        String tweetMessage = null;
        try {
            tweetMessage = objectMapper.writeValueAsString(reaction);
        } catch (JsonProcessingException e) {
            log.error("Error creating the tweet message.", e);
        }
        messageProducer.sendMessage(topic, tweetMessage);
    }
}
