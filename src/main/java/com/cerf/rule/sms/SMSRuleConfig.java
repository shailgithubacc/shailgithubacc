package com.cerf.rule.sms;

public interface SMSRuleConfig {
    boolean isMsgExpiryCheckEnabled();
    int getOtpMsgExpiryTime();
    boolean isLastHourMsgDeliveryCheckEnabled();
    int domesticNonOtpMsgLastHrDeliveryTime();
}
