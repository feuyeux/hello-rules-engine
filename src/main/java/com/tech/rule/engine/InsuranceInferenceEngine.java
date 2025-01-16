package com.tech.rule.engine;

import com.tech.api.RuleNamespace;
import com.tech.rule.pojo.InsuranceDetails;
import com.tech.rule.pojo.PolicyHolderDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InsuranceInferenceEngine extends InferenceEngine<PolicyHolderDetails, InsuranceDetails> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.INSURANCE;
    }

    @Override
    protected InsuranceDetails initializeOutputResult() {
        return InsuranceDetails.builder().build();
    }
}
