package com.solace.executor.api.service;

import com.solace.executor.api.model.GenericAPIRequest;
import com.solace.executor.api.model.GenericAPIResponse;
import com.solace.executor.api.service.AtomicApplicationServices.SequentialBizAtomicApplicationJMSInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class zLegacySequenticalSyncDemoService {

    Logger logger = LoggerFactory.getLogger(zLegacySequenticalSyncDemoService.class);
    @Autowired
    private SequentialBizAtomicApplicationJMSInterface sequentialBizAtomicApplicationJMSInterface;
    @Autowired
    private GenericAPIResponse apiResponse;

    public GenericAPIResponse sequentialSyncDemo(GenericAPIRequest apiRequest) {
        logger.info("Engine topicParrellelDemo with Input:" + apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount() +
                " : ThreadCount:" + apiRequest.getThreadCount());
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
                sequentialBizAtomicApplicationJMSInterface.sendEvent(apiRequest.getInput());
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
