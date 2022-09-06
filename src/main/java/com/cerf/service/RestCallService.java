package com.cerf.service;

import com.cerf.configuration.ApplicationConfig;
import com.cerf.dto.NotificationRequestDTO;
import com.cerf.dto.NotificationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestCallService {

    Logger logger = LoggerFactory.getLogger(RestCallService.class);
    @Autowired
    private Environment environment;
    @Autowired
    ApplicationConfig applicationConfig;
    public NotificationResponseDTO submitRequest(NotificationRequestDTO notificationRequestDTO){
        logger.debug("notificationRequestDTO {} ", notificationRequestDTO);
        NotificationResponseDTO notificationResponseDTO = null;
        long startTime = System.currentTimeMillis();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<NotificationRequestDTO> entity = new HttpEntity<>(notificationRequestDTO, headers);
            ResponseEntity<NotificationResponseDTO> responseEntity = restTemplate.exchange(
                                                                    environment.getProperty("NOTIFICATION-SERVICE-URL"),
                                                                    HttpMethod.POST,
                                                                    entity,
                                                                    NotificationResponseDTO.class);
            if (responseEntity.getStatusCode() != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                notificationResponseDTO = responseEntity.getBody();
            } else {
                logger.error("Api response is not 200-OK Request data {} response entity {} ", notificationRequestDTO, responseEntity);
            }
        }catch (Exception e){
            logger.error("Exception found in calling api data {} exception",notificationRequestDTO,e);
        }
        logger.info("Api response - Request data {} response {} total time taken {} ", notificationRequestDTO, notificationResponseDTO, System.currentTimeMillis()-startTime);
        return notificationResponseDTO;
    }


    public String getVendorsData(){
        long startTime = System.currentTimeMillis();
        String vendorString = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("NOTIFICATION-SERVICE-URL",String.class) ;

                if (responseEntity.getStatusCode() != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                    vendorString = responseEntity.getBody();
                } else {
                    logger.error("getVendorsData Api response is not 200-OK response entity {} ", responseEntity);
                }
        }catch (Exception e){
            logger.error("Exception found in calling api data exception",e);
        }
        logger.info("Api response - response {} total time taken {} ",vendorString , System.currentTimeMillis()-startTime);
        return vendorString;
    }
}
