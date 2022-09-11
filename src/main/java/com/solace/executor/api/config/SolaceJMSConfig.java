package com.solace.executor.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class SolaceJMSConfig {

    @Autowired
    private JmsTemplate jmsTemplate;

    // Enable below code block to apply common jmsTemplate across services.
//    @PostConstruct
//    private void customizeJmsTemplate() {
//        // Update the jmsTemplate's connection factory to cache the connection
//        CachingConnectionFactory ccf = new CachingConnectionFactory();
//        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());
//        jmsTemplate.setConnectionFactory(ccf);
//
//        // By default Spring Integration uses Queues, but if you set this to true you
//        // will send to a PubSub+ topic destination
//        jmsTemplate.setPubSubDomain(false);
//    }
}
