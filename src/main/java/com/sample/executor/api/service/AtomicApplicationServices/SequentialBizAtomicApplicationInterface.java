package com.sample.executor.api.service.AtomicApplicationServices;

import com.sample.executor.api.model.AtomicAppAPIReqResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class SequentialBizAtomicApplicationInterface {
    Logger logger = LoggerFactory.getLogger(SequentialBizAtomicApplicationInterface.class);
    @Autowired
    private AtomicAppAPIReqResp atomicAppAPIReqResp;
    @Autowired
    RestTemplate restTemplate;
    @Value("${multithread.queueName}")
    private String queueName;


    public AtomicAppAPIReqResp sendEvent(String msg)   {
        for(int i=0;i<3;i++) {  // Fork out to 3 Task in a group call.
            logger.info("- SENDING SYNC APP Q1 - " + Thread.currentThread().getName());

            //Incase want to override global config class

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            restTemplate.exchange("http://localhost:8181/helloSimpleHttp", HttpMethod.GET, entity, String.class).getBody();

            // Synchronus REST or Asynchronus Messaging
            //*******//
            atomicAppAPIReqResp.setOutput("Success");
        }
        return atomicAppAPIReqResp;
    }

}
