package com.djcrgr.demo.controllers;

import com.djcrgr.demo.models.Interview;
import com.djcrgr.demo.models.InterviewAnswer;
import com.djcrgr.demo.models.User;
import com.djcrgr.demo.models.params.InterviewRequestParam;
import com.djcrgr.demo.services.InterviewService;
import com.djcrgr.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ApiController {

    private final InterviewService interviewService;
    private final UserService userService;

    @Autowired
    public ApiController(InterviewService interviewService, UserService userService) {
        this.interviewService = interviewService;
        this.userService = userService;
    }

    @Tag(name="Получение списка всех интервью", description="в зависимости от пользователя получаем либо все " +
            "интервью," +
            " либо только активные")
    @GetMapping("/getAll")
    public ResponseEntity<Map<Integer, Interview>> getAllInterview(@RequestParam String user) {
        User currentUser;
        try {
            currentUser = userService.getAllUsers().values().stream().filter(user1 -> user1.getName().equals(user)).collect(Collectors.toList()).get(0);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (currentUser.isAdmin()) {
            return ResponseEntity.ok(interviewService.loadAllInterview());
        } else {
            Map<Integer, Interview> result = new LinkedHashMap<>();
            Stream<Map.Entry<Integer, Interview>> st = interviewService.loadAllInterview().entrySet().stream();
            st.filter(e -> e.getValue().isActive()).forEach(e -> result.put(e.getKey(), e.getValue()));
            return ResponseEntity.ok(result);
        }
    }

    @Tag(name="Получение интервью по Id", description="в зависимости от пользователя получаем либо интервью по Id," +
            " либо только активное по Id")
    @GetMapping("/interview")
    public ResponseEntity<Interview> getInterview(@RequestParam Integer id, @RequestParam String user) {
        User currentUser;
        Interview interview;
        try {
            currentUser = userService.getAllUsers().values().stream().filter(user1 -> user1.getName().equals(user)).collect(Collectors.toList()).get(0);
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (currentUser.isAdmin()) {
            return ResponseEntity.ok(interviewService.getInterviewById(id));
        } else {
            interview = interviewService.getInterviewById(id);
            if (interview != null) {
                if (interview.isActive()) {
                    return ResponseEntity.ok(interview);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Tag(name="Создание нового интервью", description="возможно только администратору")
    @PostMapping("/new")
    public ResponseEntity<Interview> createNewInterview(@RequestBody String json, @RequestParam String user, @RequestParam Integer id) throws ParseException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Interview interview = objectMapper.readValue(json, Interview.class);
            User currentUser = userService.getAllUsers().values().stream().filter(user1 -> user1.getName().equals(user)).collect(Collectors.toList()).get(0);
            if (currentUser.isAdmin()) {
                return ResponseEntity.ok(interviewService.createNewInterview(interview, id));
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (NullPointerException | JsonProcessingException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name="Удаление интервью", description="возможно только администратору")
    @GetMapping("/delete")
    public ResponseEntity<Interview> deleteInterview(@RequestParam String user, @RequestParam Integer id) {
        try {
            User currentUser = userService.getAllUsers().values().stream().filter(user1 -> user1.getName().equals(user)).collect(Collectors.toList()).get(0);
            if (currentUser.isAdmin()) {
                return ResponseEntity.ok(interviewService.deleteInterview(id));
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name="Получение списка ответов пользователей", description="администратор получает ответы для всех " +
            "пользователей, пользователь - только свои ответы")
    @GetMapping("/getUserAnswers")
    public ResponseEntity<List<InterviewAnswer>> getAllUserAnswers(@RequestParam Integer userId) {
        if (!userService.getAllAnswers().isEmpty()) {
            return ResponseEntity.ok(userService.getAllAnswers().stream().filter(interviewAnswer -> interviewAnswer.getUserId().equals(userId)).collect(Collectors.toList()));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name="Изменение интервью", description="возможно только администратору")
    @PostMapping("/editInterview")
    public ResponseEntity<Interview> editInterview(@RequestBody InterviewRequestParam interviewRequestParam, @RequestParam String user, @RequestParam Integer interviewId) throws ParseException {
        try {
            User currentUser = userService.getAllUsers().values().stream().filter(user1 -> user1.getName().equals(user)).collect(Collectors.toList()).get(0);
            if (currentUser.isAdmin()) {
                return ResponseEntity.ok(interviewService.editInterview(new Interview(interviewRequestParam), interviewId));
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (NullPointerException nullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Tag(name="Отправка ответов на интервью", description="пользователь получает в ответе все свои ответы")
    @PostMapping("/sendAnswers")
    public ResponseEntity<List<InterviewAnswer>> sendAnswers(@RequestBody InterviewAnswer interviewAnswer) {
        if (interviewAnswer.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.createUserAnswer(interviewAnswer);
        return ResponseEntity.ok(userService.getAllAnswers().stream().filter(interviewAnswer1 -> interviewAnswer.getUserId().
                equals(interviewAnswer1.getUserId())).collect(Collectors.toList()));
    }
}
