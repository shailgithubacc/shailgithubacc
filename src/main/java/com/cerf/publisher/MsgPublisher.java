package com.cerf.publisher;

import com.cerf.configuration.ApplicationConfig;
import com.cerf.dto.RequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgPublisher {
    private final Logger logger = LoggerFactory.getLogger(MsgPublisher.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ApplicationConfig appConfig;

    public boolean publishMsgToQueue(RequestDTO requestDTO){
        boolean successStatus = false;
        try {
            rabbitTemplate.convertAndSend(appConfig.getProperty("EXCHANGE-NAME"), appConfig.getProperty("ROUTING-KEY"), requestDTO);
            successStatus = true;
        }catch (Exception e){
            logger.error("Issue found in publishing the data in publisher. Data is {} and Exception {} ",requestDTO,e);
        }
        return successStatus;
    }
}
