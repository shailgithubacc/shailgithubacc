package com.cerf.rule.email;

import com.cerf.dto.RequestDTO;
import com.cerf.rule.RuleHandler;

public class EmailRuleHandlerImpl implements RuleHandler {
    @Override
    public boolean appliedRuleStatus(RequestDTO requestDTO) {
        return true;
    }
}
