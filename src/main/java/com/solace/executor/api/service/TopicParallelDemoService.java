package com.solace.executor.api.service;

import com.solace.executor.api.model.SolaceJMSModel;
import com.solace.executor.api.model.UserTopicParallelRequest;
import com.solace.executor.api.model.UserTopicParallelResponse;
import com.solace.executor.api.service.common.SolaceJMSInterfaceQ1;
import com.solace.executor.api.service.common.SolaceJMSInterfaceQ2;
import com.solace.executor.api.service.common.SolaceJMSInterfaceQ3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TopicParallelDemoService {

    Logger logger = LoggerFactory.getLogger(TopicParallelDemoService.class);

    @Autowired
    private SolaceJMSInterfaceQ1 solaceJMSInterfaceQ1;

    @Autowired
    private SolaceJMSInterfaceQ2 solaceJMSInterfaceQ2;

    @Autowired
    private SolaceJMSInterfaceQ3 solaceJMSInterfaceQ3;
    @Autowired
    private UserTopicParallelResponse apiResponse;

    public UserTopicParallelResponse topicParrellelDemo(UserTopicParallelRequest apiRequest){

        logger.info("Engine topicParrellelDemo with Input:"+apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount());
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
                CompletableFuture<SolaceJMSModel> solaceJMSModelQ1 = solaceJMSInterfaceQ1.sendEvent(apiRequest.getInput());
                CompletableFuture<SolaceJMSModel> solaceJMSModelQ2 = solaceJMSInterfaceQ2.sendEvent(apiRequest.getInput());
                CompletableFuture<SolaceJMSModel> solaceJMSModelQ3 = solaceJMSInterfaceQ3.sendEvent(apiRequest.getInput());
                CompletableFuture.allOf(solaceJMSModelQ1,solaceJMSModelQ2,solaceJMSModelQ3).join();
            }
        }
        catch(Exception e)
        {
            logger.info("Error in sendEvent :"+e.getMessage());
        }

        apiResponse.setOutput("Successful");
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        apiResponse.setExecutionTotalTimeInMS(String.valueOf(end - start));
        return apiResponse;
    }

}
