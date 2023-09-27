package com.vanilla.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class HandlerSNS implements RequestHandler<SNSEvent, String> {
    @Override
    public String handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        SNSEvent.SNSRecord record = event.getRecords().get(0);
        logger.log("RECORD: " + record);
        logger.log("EVENT SOURCE: " + record.getEventSource());
        return null;
    }
}
