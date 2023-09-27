package com.graalvm;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Handler implements RequestHandler<SNSEvent, String> {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        logger.info("!!!START!!!");
        SNSEvent.SNSRecord record = event.getRecords().get(0);
        logger.info("RECORD: " + record);
        logger.info("EVENT SOURCE: " + record.getEventSource());
        logger.info("!!!END!!!");
        return null;
    }
}
