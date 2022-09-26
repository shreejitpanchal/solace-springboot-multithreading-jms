package com.solace.executor.api.service;

import com.solace.executor.api.model.AtomicAppAPIReqResp;
import com.solace.executor.api.model.GenericAPIRequest;
import com.solace.executor.api.model.GenericAPIResponse;
import com.solace.executor.api.service.AtomicApplicationServices.BizAtomicApplicationJMSInterfaceQ1;
import com.solace.executor.api.service.AtomicApplicationServices.BizAtomicApplicationJMSInterfaceQ2;
import com.solace.executor.api.service.AtomicApplicationServices.BizAtomicApplicationJMSInterfaceQ3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ScatterGatherMultiThreadDemoService {

    Logger logger = LoggerFactory.getLogger(ScatterGatherMultiThreadDemoService.class);
    @Autowired
    private BizAtomicApplicationJMSInterfaceQ1 bizAtomicApplicationJMSInterfaceQ1;
    @Autowired
    private BizAtomicApplicationJMSInterfaceQ2 bizAtomicApplicationJMSInterfaceQ2;
    @Autowired
    private BizAtomicApplicationJMSInterfaceQ3 bizAtomicApplicationJMSInterfaceQ3;
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
                        bizAtomicApplicationJMSInterfaceQ1.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
                CompletableFuture<AtomicAppAPIReqResp> bizFunctionAtomicW2 =
                        bizAtomicApplicationJMSInterfaceQ2.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
                CompletableFuture<AtomicAppAPIReqResp> bizFunctionAtomicW3 =
                        bizAtomicApplicationJMSInterfaceQ3.sendEvent(apiRequest.getInput()); // invoke downstream application in dedicated thread
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
