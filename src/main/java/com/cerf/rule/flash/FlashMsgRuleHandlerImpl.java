package com.cerf.rule.flash;

import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;

public class FlashMsgRuleHandlerImpl implements RuleHandler {
    @Override
    public boolean appliedRuleStatus(RequestDTO requestDTO) {
        return true;
    }
}