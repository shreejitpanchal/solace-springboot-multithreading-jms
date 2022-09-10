package com.solace.executor.api.service.jmsServices;

import com.solace.executor.api.model.SolaceJMSModel;
import com.solace.executor.api.service.TopicParallelDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SolaceJMSInterfaceNonAsyncQ1 {
    Logger logger = LoggerFactory.getLogger(TopicParallelDemoService.class);
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SolaceJMSModel solaceJMSModel;

    @Value("${multithread.queueName}")
    private String queueName;

    @PostConstruct
    private void customizeJmsTemplate() {
        // Update the jmsTemplate's connection factory to cache the connection
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
        jmsTemplate.setConnectionFactory(ccf);

        // By default Spring Integration uses Queues, but if you set this to true you
        // will send to a PubSub+ topic destination
        jmsTemplate.setPubSubDomain(false);
    }
    public SolaceJMSModel sendEvent(String msg)  {
        try {
            logger.info("==========Non Sync SENDING MESSAGE Q1========== " + msg + " - " + Thread.currentThread().getName());
            jmsTemplate.convertAndSend(queueName, msg);
            solaceJMSModel.setOutput("Success");
            return solaceJMSModel;
        }
        catch(Exception e)
        {
            logger.info("Exception non Async e:"+e.getMessage());
        }
        return null;
    }

}
