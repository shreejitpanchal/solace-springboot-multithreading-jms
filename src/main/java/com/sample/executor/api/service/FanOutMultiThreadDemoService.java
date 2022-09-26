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
public class FanOutMultiThreadDemoService {

    Logger logger = LoggerFactory.getLogger(FanOutMultiThreadDemoService.class);
    @Autowired
    private BizAtomicApplicationInterfaceQ1 bizAtomicApplicationInterfaceQ1;
    @Autowired
    private BizAtomicApplicationInterfaceQ2 bizAtomicApplicationInterfaceQ2;
    @Autowired
    private BizAtomicApplicationInterfaceQ3 bizAtomicApplicationInterfaceQ3;
    @Autowired
    private GenericAPIResponse apiResponse;


    public GenericAPIResponse fanoutMultiThreadingDemo(GenericAPIRequest apiRequest) {

        logger.info("Request - fanoutMultiThreadingDemo with Input:" + apiRequest.getInput() +
                " : msgBroadCastCount :" + apiRequest.getMsgBroadCastCount());
        long start = System.currentTimeMillis();
        try {
            // FanOut Pattern start
            for (int i = 0; i < apiRequest.getMsgBroadCastCount(); i++) {
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
            }
            // FanOut Pattern End
            logger.info("Request - fanoutMultiThreadingDemo End:");
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
