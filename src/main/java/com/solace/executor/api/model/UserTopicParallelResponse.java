package com.solace.executor.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserTopicParallelResponse {
    private String output;
    private String executionTotalTimeInMS;
}
