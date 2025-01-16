package com.tech.api;

import com.google.common.base.Enums;
import com.tech.rule.engine.RuleEngine;
import com.tech.repository.models.Rule;
import com.tech.repository.KnowledgeBaseService;
import com.tech.rule.engine.InsuranceInferenceEngine;
import com.tech.rule.pojo.InsuranceDetails;
import com.tech.rule.pojo.PolicyHolderDetails;
import com.tech.rule.pojo.LoanDetails;
import com.tech.rule.engine.LoanInferenceEngine;
import com.tech.rule.pojo.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RuleEngineRestController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private RuleEngine<UserDetails,LoanDetails> ruleEngine;
    private RuleEngine<PolicyHolderDetails,InsuranceDetails> ruleEngine2;
    @Autowired
    private LoanInferenceEngine loanInferenceEngine;
    @Autowired
    private InsuranceInferenceEngine insuranceInferenceEngine;

    @GetMapping(value = "/get-all-rules/{ruleNamespace}")
    public ResponseEntity<?> getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = knowledgeBaseService.getAllRuleByNamespace(namespace.toString());
        return ResponseEntity.ok(allRules);
    }

    @GetMapping(value = "/get-all-rules")
    public ResponseEntity<?> getAllRules() {
        List<Rule> allRules = knowledgeBaseService.getAllRules();
        return ResponseEntity.ok(allRules);
    }

    @PostMapping(value = "/loan")
    public ResponseEntity<?> postUserLoanDetails(@RequestBody UserDetails userDetails) {
        LoanDetails result =  ruleEngine.run(loanInferenceEngine, userDetails);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insurance")
    public ResponseEntity<?> postCarLoanDetails(@RequestBody PolicyHolderDetails policyHolderDetails) {
        InsuranceDetails result =  ruleEngine2.run(insuranceInferenceEngine, policyHolderDetails);
        return ResponseEntity.ok(result);
    }
}
