package com.solace.executor.api.service;

import com.solace.executor.api.model.UserTopicParallelRequest;
import com.solace.executor.api.model.UserTopicParallelResponse;
import com.solace.executor.api.service.AtomicJMSServices.SolaceJMSInterfaceNonAsyncQ1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LegacyTopicNonParallelDemoService {

    Logger logger = LoggerFactory.getLogger(TopicParallelDemoService.class);
    @Autowired
    private SolaceJMSInterfaceNonAsyncQ1 solaceJMSInterfaceNonAsyncQ1;
    @Autowired
    private UserTopicParallelResponse apiResponse;

    public UserTopicParallelResponse topicParrellelDemo(UserTopicParallelRequest apiRequest) {
        logger.info("Engine topicParrellelDemo with Input:" + apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount() +
                " : ThreadCount:" + apiRequest.getThreadCount());
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
                solaceJMSInterfaceNonAsyncQ1.sendEvent(apiRequest.getInput());
            }
        } catch (Exception e) {
            logger.info("Error in sendEvent :" + e.getMessage());
        }

        apiResponse.setOutput("Successful");
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        apiResponse.setExecutionTotalTimeInMS(String.valueOf(end - start));
        return apiResponse;
    }
}
