package com.solace.executor.api.controller;

import com.solace.executor.api.model.GenericAPIRequest;
import com.solace.executor.api.service.ScatterGatherMultiThreadDemoService;
import com.solace.executor.api.service.zLegacySequenticalSyncDemoService;
import com.solace.executor.api.service.FanOutMultiThreadDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GenericController {
    @Autowired
    private FanOutMultiThreadDemoService fanOutMultiThreadDemoService;
    @Autowired
    private ScatterGatherMultiThreadDemoService scatterGatherMultiThreadDemoService;
    @Autowired
    private zLegacySequenticalSyncDemoService zLegacySequenticalSyncDemoService;

    @PostMapping(value = "/fanOutMultiThreadDemo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity fanOutMultiThreadDemo(
            @Valid
            @RequestBody GenericAPIRequest apiRequest) {
        return ResponseEntity.ok(fanOutMultiThreadDemoService.fanoutMultiThreadingDemo(apiRequest));
    }

    @PostMapping(value = "/scatterGatherMultiThreadDemo", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity scatterGatherMultiThreadDemo(
            @Valid
            @RequestBody GenericAPIRequest apiRequest) {
        return ResponseEntity.ok(scatterGatherMultiThreadDemoService.scatterGatherMultiThreadingDemo(apiRequest));
    }

    @PostMapping(value = "/legacySequenticalSyncDemoService", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity legacySequenticalSyncDemoService(
            @Valid
            @RequestBody GenericAPIRequest apiRequest) {
        return ResponseEntity.ok(zLegacySequenticalSyncDemoService.sequentialSyncDemo(apiRequest));
    }

}
