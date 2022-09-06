package com.cerf.rule.sms.impl;

import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;
import com.cerf.rule.sms.SMSRuleConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalTime;

public class SMSRuleHandlerImpl implements RuleHandler {

    @Autowired
    SMSRuleConfig smsRuleConfig;
    @Override
    public boolean appliedRuleStatus(RequestDTO requestDTO) {

        if(requestDTO.isOtp()){
            if(smsRuleConfig.isMsgExpiryCheckEnabled() && Instant.now().toEpochMilli() - requestDTO.getRequestReceivedTime() >= smsRuleConfig.getOtpMsgExpiryTime()){
                // TODO: 25-08-2022 Need to reject this msg
                return false;
            }
        }else{
            if(!requestDTO.isIntFlag() && smsRuleConfig.isLastHourMsgDeliveryCheckEnabled() && LocalTime.now().getHour() >= smsRuleConfig.domesticNonOtpMsgLastHrDeliveryTime()){
                // TODO: 25-08-2022  Need to discuss- have to reject this msg or what
                return false;
            }
        }
        return true;
    }
}
