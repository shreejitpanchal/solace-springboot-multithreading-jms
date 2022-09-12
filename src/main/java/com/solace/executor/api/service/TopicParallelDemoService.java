package com.solace.executor.api.service;

import com.solace.executor.api.model.SolaceJMSModel;
import com.solace.executor.api.model.UserTopicParallelRequest;
import com.solace.executor.api.model.UserTopicParallelResponse;
import com.solace.executor.api.service.AtomicJMSServices.BizFunctionAtomicSolaceJMSInterfaceQ1;
import com.solace.executor.api.service.AtomicJMSServices.BizFunctionAtomicSolaceJMSInterfaceQ2;
import com.solace.executor.api.service.AtomicJMSServices.BizFunctionAtomicSolaceJMSInterfaceQ3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TopicParallelDemoService {

    Logger logger = LoggerFactory.getLogger(TopicParallelDemoService.class);

    @Autowired
    private BizFunctionAtomicSolaceJMSInterfaceQ1 bizFunctionAtomicSolaceJMSInterfaceQ1;

    @Autowired
    private BizFunctionAtomicSolaceJMSInterfaceQ2 bizFunctionAtomicSolaceJMSInterfaceQ2;

    @Autowired
    private BizFunctionAtomicSolaceJMSInterfaceQ3 bizFunctionAtomicSolaceJMSInterfaceQ3;
    @Autowired
    private UserTopicParallelResponse apiResponse;

    public UserTopicParallelResponse topicParrellelDemo(UserTopicParallelRequest apiRequest) {

        logger.info("Engine topicParrellelDemo with Input:" + apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount());
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
                // Business function Atomic service class below using asyncExecutor Thread pool
                CompletableFuture<SolaceJMSModel> bizFunctionAtomicW1 =
                        bizFunctionAtomicSolaceJMSInterfaceQ1.sendEvent(apiRequest.getInput());
                CompletableFuture<SolaceJMSModel> bizFunctionAtomicW2 =
                        bizFunctionAtomicSolaceJMSInterfaceQ2.sendEvent(apiRequest.getInput());
                CompletableFuture<SolaceJMSModel> bizFunctionAtomicW3 =
                        bizFunctionAtomicSolaceJMSInterfaceQ3.sendEvent(apiRequest.getInput());

                // Disable below code to make Async Fan out modelling, else allOf will collect each threads responses
                CompletableFuture.allOf(bizFunctionAtomicW1,bizFunctionAtomicW2,bizFunctionAtomicW3).join();
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
