package com.sample.executor.api.service;

import com.sample.executor.api.model.AtomicAppAPIReqResp;
import com.sample.executor.api.model.GenericAPIRequest;
import com.sample.executor.api.model.GenericAPIResponse;
import com.sample.executor.api.service.AtomicApplicationServices.BizAtomicApplicationInterfaceQ1;
import com.sample.executor.api.service.AtomicApplicationServices.BizAtomicApplicationInterfaceQ2;
import com.sample.executor.api.service.AtomicApplicationServices.BizAtomicApplicationInterfaceQ3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ScatterGatherMultiThreadDemoService {

    Logger logger = LoggerFactory.getLogger(ScatterGatherMultiThreadDemoService.class);
    @Autowired
    private BizAtomicApplicationInterfaceQ1 bizAtomicApplicationInterfaceQ1;
    @Autowired
    private BizAtomicApplicationInterfaceQ2 bizAtomicApplicationInterfaceQ2;
    @Autowired
    private BizAtomicApplicationInterfaceQ3 bizAtomicApplicationInterfaceQ3;
    @Autowired
    private GenericAPIResponse apiResponse;


    public GenericAPIResponse scatterGatherMultiThreadingDemo(GenericAPIRequest apiRequest) {

        logger.info("Request - ScatterGatherMultiThreadingDemo with Input:" + apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount());
        long start = System.currentTimeMillis();
        try {

            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
                // ScatterGather Pattern start
                // Business function Atomic service class below using asyncExecutor Thread pool
                CompletableFuture<AtomicAppAPIReqResp> bizFunctionAtomicW1 =
                        bizAtomicApplicationInterfaceQ1.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
                CompletableFuture<AtomicAppAPIReqResp> bizFunctionAtomicW2 =
                        bizAtomicApplicationInterfaceQ2.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
                CompletableFuture<AtomicAppAPIReqResp> bizFunctionAtomicW3 =
                        bizAtomicApplicationInterfaceQ3.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
                //
                // Add code here to add more than 3 application invocation
                //

                // To collect responses from worker threads
                CompletableFuture.allOf(bizFunctionAtomicW1,bizFunctionAtomicW2,bizFunctionAtomicW3).join();
                // ScatterGather Pattern End
            }
            logger.info("Request - ScatterGatherMultiThreadingDemo End:");
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
