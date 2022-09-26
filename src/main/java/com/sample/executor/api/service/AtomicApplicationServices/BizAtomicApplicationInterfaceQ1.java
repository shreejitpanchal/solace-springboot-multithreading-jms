package com.sample.executor.api.service.AtomicApplicationServices;

import com.sample.executor.api.model.AtomicAppAPIReqResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class BizAtomicApplicationInterfaceQ1 {
    Logger logger = LoggerFactory.getLogger(BizAtomicApplicationInterfaceQ1.class);

    @Autowired
    private AtomicAppAPIReqResp atomicAppAPIReqResp;
    @Autowired
    RestTemplate restTemplate;


    @Async("asyncExecutor")
    public CompletableFuture<AtomicAppAPIReqResp> sendEvent(String msg)  throws InterruptedException  {
        logger.info("- SENDING MESSAGE APP Q1 - " + Thread.currentThread().getName());

        //Incase want to override global config class
        //*******//
        // write code here to implement scatter/gather and to capture response from the downstream application

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        restTemplate.exchange("http://localhost:8181/helloSimpleHttp", HttpMethod.GET, entity, String.class).getBody();

        // Synchronus REST or Asynchronus Messaging
        //*******//
        atomicAppAPIReqResp.setOutput("Success");
        return CompletableFuture.completedFuture(atomicAppAPIReqResp);
    }

}
