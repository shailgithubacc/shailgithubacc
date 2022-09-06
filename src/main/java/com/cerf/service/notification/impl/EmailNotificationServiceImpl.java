package com.cerf.service.notification.impl;

import com.cerf.configuration.ApplicationConfig;
import com.cerf.dto.NotificationRequestDTO;
import com.cerf.dto.NotificationResponseDTO;
import com.cerf.dto.RequestDTO;
import com.cerf.dto.VendorDTO;
import com.cerf.publisher.MsgPublisher;
import com.cerf.service.RestCallService;
import com.cerf.service.notification.MsgNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmailNotificationServiceImpl implements MsgNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationServiceImpl.class);
    @Autowired
    RestCallService restCallService;
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    MsgPublisher msgPublisher;
    @Override
    public void sendNotification(RequestDTO requestDTO, List<VendorDTO> vendorList) {
        logger.info("Request data {} and vendor list {} ", requestDTO, vendorList);
        int maxRetryAllowed = Integer.parseInt(Optional.ofNullable(applicationConfig.getProperty("CHANNEL-EMAIL-RETRY-COUNTS")).orElse("1"));
        NotificationResponseDTO notificationResponseDTO = null;
        for (VendorDTO vendorDTO : vendorList) {
            notificationResponseDTO = submitRequest(requestDTO, vendorDTO, 0, maxRetryAllowed);
            if (notificationResponseDTO != null && notificationResponseDTO.isSuccess()) {
                break;
            }
        }
        // TODO: 24-08-2022 request data, vendor details and response need to be persisted.
        //in case of vendor response fail then resubmitting the request to respective queue
        if(notificationResponseDTO == null || ! notificationResponseDTO.isSuccess()){
            msgPublisher.publishMsgToQueue(requestDTO);
        }
    }
    private NotificationResponseDTO submitRequest(RequestDTO requestDTO, VendorDTO vendor, int retryCount, int maxRetryAllowed){
        NotificationResponseDTO notificationResponseDTO;
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(
                requestDTO.getChannel(),requestDTO.getMessage(),requestDTO.getFrom(),
                requestDTO.getTo(),requestDTO.getSubject(), requestDTO.isUnicode(),
                requestDTO.getMessageId(), vendor.getVendorId(), 1);
        //Try with primary url
        notificationResponseDTO = restCallService.submitRequest(notificationRequestDTO);
        logger.debug("message id {} msisdn {} vendor id {} primary vendor response {} ",requestDTO.getMessageId(),requestDTO.getTo(),vendor.getVendorId(),notificationResponseDTO);
        //If fail then retry with secondary url
        if(vendor.isHaveSecondaryURL() && (notificationResponseDTO == null || !notificationResponseDTO.isSuccess())){
            notificationRequestDTO.setRetryWith(2);
            notificationResponseDTO = restCallService.submitRequest(notificationRequestDTO);
            logger.debug("message id {} msisdn {} vendor id {} secondary vendor response {} ",requestDTO.getMessageId(),requestDTO.getTo(),vendor.getVendorId(),notificationResponseDTO);
        }
        retryCount++;
        if(retryCount < maxRetryAllowed && (notificationResponseDTO == null || !notificationResponseDTO.isSuccess())){
            submitRequest(requestDTO, vendor, retryCount, maxRetryAllowed);
        }
        logger.info("message id {} msisdn {} vendor id {} vendor response {} ",requestDTO.getMessageId(),requestDTO.getTo(),vendor.getVendorId(),notificationResponseDTO);
        return notificationResponseDTO;
    }
}
