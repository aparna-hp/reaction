package com.poc.reaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.reaction.entity.Reaction;
import com.poc.reaction.model.CommentReactionDTO;
import com.poc.reaction.model.ReactionType;
import com.poc.reaction.service.ReactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReactionService reactionService;

    private static final Logger logger =
            LogManager.getLogger(ReactionControllerTest.class);


    @Test
    public void testAddTweetReaction() throws Exception {
        CommentReactionDTO tweetReaction = new CommentReactionDTO();
        tweetReaction.setId(1L);
        tweetReaction.setTweetId(1L);
        tweetReaction.setUserId(1L);
        tweetReaction.setTweetUserId(2L);
        tweetReaction.setComment("My first tweet app reaction");

        Mockito.doReturn(new Reaction()).when(reactionService).addCommentReaction(Mockito.any(CommentReactionDTO.class));

        ObjectMapper mapper = new ObjectMapper();
        String requestJson=mapper.writeValueAsString(tweetReaction);

        logger.info("Input Json = " + requestJson);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tweeter/tweets/1/reactions/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }
}
