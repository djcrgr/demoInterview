package com.djcrgr.demo.services;

import com.djcrgr.demo.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final Map<Integer, User> userMap = new HashMap<>();
    private final List<InterviewAnswer> answerList = new ArrayList<>();

    @PostConstruct
    private void init() {
        userMap.put(1, new User("admin", true));
        userMap.put(2, new User("user", false));
        answerList.add(new InterviewAnswer(1, 1, Arrays.asList(new Answer("first"), new Answer("second"))));
    }

    public Map<Integer, User> getAllUsers() {
        return userMap;
    }

    public List<InterviewAnswer> getAllAnswers() {
        return answerList;
    }

    public List<InterviewAnswer> createUserAnswer(InterviewAnswer interviewAnswer) {
        answerList.add(interviewAnswer);
        return answerList;
    }
}
