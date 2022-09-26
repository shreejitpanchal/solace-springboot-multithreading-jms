package com.solace.executor.api.service.AtomicApplicationServices;

import com.solace.executor.api.model.AtomicAppAPIReqResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BizAtomicApplicationJMSInterfaceQ2 {
    Logger logger = LoggerFactory.getLogger(BizAtomicApplicationJMSInterfaceQ2.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private AtomicAppAPIReqResp atomicAppAPIReqResp;
    @Value("${multithread.queueName}")
    private String queueName;

    @Async("asyncExecutor")
    public CompletableFuture<AtomicAppAPIReqResp> sendEvent(String msg)  throws InterruptedException  {
        logger.info("- SENDING MESSAGE APP Q2 - " + Thread.currentThread().getName());

        //Incase want to override global config class
        //jmsTemplate.setPubSubDomain(false);  // false = Queue | true = Topic

        jmsTemplate.convertAndSend(queueName, msg);
        atomicAppAPIReqResp.setOutput("Success");
        //*******//
        // write code here to implement scatter/gather and to capture response from the downstream application which can be either
        // Synchronus REST or Asynchronus Messaging
        //*******//
        return CompletableFuture.completedFuture(atomicAppAPIReqResp);
    }

}
