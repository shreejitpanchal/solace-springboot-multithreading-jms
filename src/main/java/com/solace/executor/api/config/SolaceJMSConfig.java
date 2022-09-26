package com.solace.executor.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class SolaceJMSConfig {

    @Value("${multithread.setSessionCacheSize}")
    private int setSessionCacheSize;
    @Autowired
    private JmsTemplate jmsTemplate;

    // Enable below code block to apply common jmsTemplate across services.

    @PostConstruct
    private void customizeJmsTemplate() {
        // Update the jmsTemplate's connection factory to cache the connection
        CachingConnectionFactory ccf = new CachingConnectionFactory();
        ccf.setTargetConnectionFactory(jmsTemplate.getConnectionFactory());

        // default = 1, Increase this value will vertically scale the Solace Client Session connection pool
        ccf.setSessionCacheSize(setSessionCacheSize);

        jmsTemplate.setConnectionFactory(ccf);

        // By default Spring Integration uses Queues, but if you set this to true you
        // will send to a PubSub+ topic destination
        jmsTemplate.setPubSubDomain(false);  // false = Queue | true = Topic
    }
}
