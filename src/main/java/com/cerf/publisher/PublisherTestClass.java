package com.cerf.publisher;

import com.cerf.configuration.ApplicationConfig;
import com.cerf.dto.RequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher")
public class PublisherTestClass {
    private static final Logger logger = LoggerFactory.getLogger(PublisherTestClass.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ApplicationConfig appConfig;

    @PostMapping("/submit")
    public String submitToPublisher(@RequestBody RequestDTO requestDTO){
        try {
            rabbitTemplate.convertAndSend(appConfig.getProperty("EXCHANGE-NAME"), appConfig.getProperty("ROUTING-KEY"), requestDTO);
        }catch (Exception e){
            logger.error("Issue found in publishing the data in publisher. Data is {} and Exception {} ",requestDTO,e);
        }
        return "success";
    }
}
