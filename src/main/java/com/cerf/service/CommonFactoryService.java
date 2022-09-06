package com.cerf.service;

import com.cerf.constant.ApplicationConstant;
import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;
import com.cerf.rule.email.EmailRuleHandlerImpl;
import com.cerf.rule.flash.FlashMsgRuleHandlerImpl;
import com.cerf.rule.ivr.IVRRuleHandlerImpl;
import com.cerf.rule.sms.impl.SMSRuleHandlerImpl;
import com.cerf.service.filter.vendor.VendorFilterService;
import com.cerf.service.filter.vendor.impl.EmailVendorServiceImpl;
import com.cerf.service.filter.vendor.impl.FlashCallVendorServiceImpl;
import com.cerf.service.filter.vendor.impl.SMSVendorServiceImpl;
import com.cerf.service.filter.vendor.impl.VoiceCallVendorServiceImpl;
import com.cerf.service.notification.MsgNotificationService;
import com.cerf.service.notification.impl.EmailNotificationServiceImpl;
import com.cerf.service.notification.impl.FlashCallNotificationServiceImpl;
import com.cerf.service.notification.impl.SMSNotificationServiceImpl;
import com.cerf.service.notification.impl.VoiceCallNotificationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonFactoryService {
    private final static Logger logger = LoggerFactory.getLogger(CommonFactoryService.class);

    public MsgNotificationService createMsgNotificationService(RequestDTO requestDTO) {
        MsgNotificationService msgNotificationService = null;
        switch (requestDTO.getChannel().toUpperCase()) {
            case ApplicationConstant.SMS_CHANNEL:
                msgNotificationService = new SMSNotificationServiceImpl();
                break;
            case ApplicationConstant.EMAIL_CHANNEL:
                msgNotificationService = new EmailNotificationServiceImpl();
                break;
            case ApplicationConstant.IVR_CHANNEL:
                msgNotificationService = new VoiceCallNotificationServiceImpl();
                break;
            case ApplicationConstant.FLASH_CHANNEL:
                msgNotificationService = new FlashCallNotificationServiceImpl();
                break;
            default:
                logger.info("createMsgNotificationService Invalid channel type received {} ", requestDTO.getChannel());
                break;
        }
        return msgNotificationService;
    }

    public RuleHandler createMsgRule(RequestDTO requestDTO){
        RuleHandler ruleHandler = null;
        switch (requestDTO.getChannel().toUpperCase()) {
            case ApplicationConstant.SMS_CHANNEL :
                ruleHandler = new SMSRuleHandlerImpl();
                break;
            case ApplicationConstant.EMAIL_CHANNEL :
                ruleHandler = new EmailRuleHandlerImpl();
                break;
            case ApplicationConstant.IVR_CHANNEL :
                ruleHandler = new IVRRuleHandlerImpl();
                break;
            case ApplicationConstant.FLASH_CHANNEL :
                ruleHandler = new FlashMsgRuleHandlerImpl();
                break;
            default:
                logger.info("createMsgRule Invalid channel type received {} ",requestDTO.getChannel());
                break;
        }
        return ruleHandler;
    }

    public VendorFilterService getVendorService(RequestDTO requestDTO){
        VendorFilterService vendorFilterService =null ;
        switch (requestDTO.getChannel().toUpperCase()) {
            case ApplicationConstant.SMS_CHANNEL :
                vendorFilterService = new SMSVendorServiceImpl();
                break;
            case ApplicationConstant.EMAIL_CHANNEL :
                vendorFilterService = new EmailVendorServiceImpl();
                break;
            case ApplicationConstant.IVR_CHANNEL :
                vendorFilterService = new VoiceCallVendorServiceImpl();
                break;
            case ApplicationConstant.FLASH_CHANNEL :
                vendorFilterService = new FlashCallVendorServiceImpl();
                break;
            default:
                logger.info("getVendorService Invalid channel type received {} ",requestDTO.getChannel());
                break;
        }
        return vendorFilterService;
    }
}
