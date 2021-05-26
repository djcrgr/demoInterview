package com.djcrgr.demo.services;

import com.djcrgr.demo.models.Interview;
import com.djcrgr.demo.models.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InterviewService {

    private static final Logger LOGGER = LogManager.getLogger(InterviewService.class);
    private Map<Integer, Interview> interviewMap = new HashMap<>();

    @PostConstruct
    private void init() throws ParseException {
        Interview interview = new Interview("interview", "first interview", new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-11"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-01"), Arrays.asList(new Question("first", "text"),
                new Question("second", "text")), true);
        interviewMap.put(1, interview);
        Interview interview2 = new Interview("interview2", "second interview", new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-01"),
                new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-01"), Arrays.asList(new Question("first", "text"),
                new Question("second", "text")), false);
        interviewMap.put(2, interview2);
    }

    public Map<Integer, Interview> loadAllInterview() {
        if (!interviewMap.isEmpty()) {
            return interviewMap;
        } else {
            return null;
        }
    }

    public Interview editInterview (Interview interview, Integer interviewId) {
        Date date = interviewMap.get(interviewId).getStartDate();
        interview.setStartDate(date);
        interviewMap.put(interviewId, interview);
        return interviewMap.get(interviewId);
    }

    public Interview getInterviewById(Integer id) {
        try {
            return interviewMap.get(id);
        } catch (NullPointerException nullPointerException) {
            LOGGER.error("we dont have this interview", nullPointerException);
            return null;
        }
    }

    public Interview createNewInterview(Interview interview, Integer id) {
        interviewMap.put(id, interview);
        return interviewMap.get(id);
    }

    public Interview deleteInterview(Integer id) {
        return interviewMap.remove(id);
    }
}
