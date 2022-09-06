package com.cerf.service;

import com.cerf.dto.RequestDTO;
import com.cerf.dto.VendorDTO;
import com.cerf.rule.RuleHandler;
import com.cerf.service.filter.vendor.VendorFilterService;
import com.cerf.service.notification.MsgNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(MsgProcessorService.class);
    @Autowired
    CommonFactoryService commonFactoryService;
    public void processMsgRequest(RequestDTO requestDTO) {
        boolean status = getAppliedRuleStatus(requestDTO);
        if (status) {
            List<VendorDTO> vendorList = getVendorList(requestDTO);
            if (vendorList != null && !vendorList.isEmpty()) {
                processNotificationRequest(requestDTO, vendorList);
            } else {
                logger.warn("No vendor found for {} ", requestDTO);
            }
        }else{
            logger.warn("Rule failed for {} ", requestDTO);
        }
    }

    private boolean getAppliedRuleStatus(RequestDTO requestDTO){
        RuleHandler ruleHandler = commonFactoryService.createMsgRule(requestDTO);
        if (ruleHandler != null) {
            return ruleHandler.appliedRuleStatus(requestDTO);
        }
        return false;
    }
    private void processNotificationRequest(RequestDTO requestDTO, List<VendorDTO> vendorList) {
        MsgNotificationService msgNotificationService = commonFactoryService.createMsgNotificationService(requestDTO);
        if(msgNotificationService != null){
            msgNotificationService.sendNotification(requestDTO, vendorList);
        }
    }
    private List<VendorDTO> getVendorList(RequestDTO requestDTO){
        VendorFilterService vendorFilterService = commonFactoryService.getVendorService(requestDTO);
        if(vendorFilterService !=null){
            return vendorFilterService.getVendors(requestDTO);
        }
        return null;
    }
}
