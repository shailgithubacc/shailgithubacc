package com.cerf.rule.ivr;

import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;

public class IVRRuleHandlerImpl implements RuleHandler {
    @Override
    public boolean appliedRuleStatus(RequestDTO requestDTO) {
        return true;
    }
}
