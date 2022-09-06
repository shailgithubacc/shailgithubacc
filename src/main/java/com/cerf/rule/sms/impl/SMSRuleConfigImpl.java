package com.cerf.rule.sms.impl;

import com.cerf.configuration.ApplicationConfig;
import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;
import com.cerf.rule.sms.SMSRuleConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Optional;

public class SMSRuleConfigImpl implements SMSRuleConfig {

    @Autowired
    ApplicationConfig applicationConfig;
    @Override
    public boolean isMsgExpiryCheckEnabled() {
        return Boolean.parseBoolean(applicationConfig.getProperty("MSG-EXPIRY-CHECK-ENABLE"));
    }

    @Override
    public int getOtpMsgExpiryTime() {
        return Integer.parseInt(
                Optional.ofNullable(applicationConfig.getProperty("OTP-MSG-EXPIRY-TIME")).orElse("300")
        );
    }

    @Override
    public boolean isLastHourMsgDeliveryCheckEnabled() {
        return Boolean.parseBoolean(applicationConfig.getProperty("LAST-HOUR-MSG-DELIVERY-CHECK-ENABLE"));
    }

    @Override
    public int domesticNonOtpMsgLastHrDeliveryTime() {
        return Integer.parseInt(
                Optional.ofNullable(applicationConfig.getProperty("DOMESTIC-NON-OTP-MSG-LAST-HOUR-DELIVERY-ALLOW")).orElse("23")
        );
    }
}
