package com.example.authtry.controller;


import com.example.authtry.exception.ResourceNotFoundException;
import com.example.authtry.model.Question;
import com.example.authtry.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @RequestMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @GetMapping("/")
    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }


    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionRepository.save(question);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }


    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}
