package com.cerf.consumer;

import com.cerf.dto.RequestDTO;
import com.cerf.service.MsgProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgConsumer {
    @Autowired
    MsgProcessorService msgProcessorService;
    Logger logger = LoggerFactory.getLogger(MsgConsumer.class);
    @RabbitListener(queues = "${QUEUE-NAME}")
    public void getQueueData(RequestDTO requestDTO ){
        logger.info("getQueueData is {} ",requestDTO);
        if(requestDTO!= null) {
           msgProcessorService.processMsgRequest(requestDTO);
        }
    }
}
