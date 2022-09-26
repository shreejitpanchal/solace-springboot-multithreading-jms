package com.solace.executor.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GenericAPIRequest {
    private String input;
    private Integer msgBroadCastCount;
    private Integer threadCount;
}
