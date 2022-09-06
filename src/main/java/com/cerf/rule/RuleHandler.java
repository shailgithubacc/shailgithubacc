package com.cerf.rule;

import com.cerf.dto.RequestDTO;

public interface RuleHandler {

    boolean appliedRuleStatus(RequestDTO requestDTO);
}
