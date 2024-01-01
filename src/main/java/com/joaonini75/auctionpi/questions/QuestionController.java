package com.joaonini75.auctionpi.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(path = "{id}")
    public Question getQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestion(id);
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @PutMapping
    public Question answerQuestion(@RequestBody Question question) {
        return questionService.answerQuestion(question);
    }

}
