package com.solace.executor.api.service.AtomicJMSServices;

import com.solace.executor.api.model.SolaceJMSModel;
import com.solace.executor.api.service.TopicParallelDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Service
public class BizFunctionAtomicSolaceJMSInterfaceQ1 {
    Logger logger = LoggerFactory.getLogger(TopicParallelDemoService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SolaceJMSModel solaceJMSModel;

    @Value("${multithread.queueName}")
    private String queueName;

    @Async("asyncExecutor")
    public CompletableFuture<SolaceJMSModel> sendEvent(String msg)  throws InterruptedException  {
        logger.info("=== SENDING MESSAGE Q1 : " + msg + " - " + Thread.currentThread().getName());
        jmsTemplate.convertAndSend(queueName, msg);
        solaceJMSModel.setOutput("Success");
        return CompletableFuture.completedFuture(solaceJMSModel);
    }

}
