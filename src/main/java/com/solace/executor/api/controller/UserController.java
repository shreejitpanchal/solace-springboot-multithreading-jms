package com.solace.executor.api.controller;

import com.solace.executor.api.model.UserTopicParallelRequest;
import com.solace.executor.api.service.LegacyTopicNonParallelDemoService;
import com.solace.executor.api.service.TopicParallelDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private TopicParallelDemoService topicParallelDemoService;

    @Autowired
    private LegacyTopicNonParallelDemoService legacyTopicNonParallelDemoService;

    @PostMapping(value = "/topicParallelDemo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity topicParallelDemo(
            @Valid
            @RequestBody UserTopicParallelRequest apiRequest) {
        return ResponseEntity.ok(topicParallelDemoService.topicParrellelDemo(apiRequest));
    }

    @PostMapping(value = "/topicNonParallelDemo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity topicNonParallelDemo(
            @Valid
            @RequestBody UserTopicParallelRequest apiRequest) {
        return ResponseEntity.ok(legacyTopicNonParallelDemoService.topicParrellelDemo(apiRequest));
    }

}
