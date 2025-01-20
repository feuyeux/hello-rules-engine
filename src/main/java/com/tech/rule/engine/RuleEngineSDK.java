package com.tech.rule.engine;

import com.tech.repository.KnowledgeBaseService;
import com.tech.repository.models.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleEngineSDK<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    public OUTPUT_RESULT run(InferenceEngine<INPUT_DATA, OUTPUT_RESULT> inferenceEngine, INPUT_DATA inputData) {
        String ruleNamespace = inferenceEngine.getRuleNamespace().toString();
        //TODO: Here for each call, we are fetching all rules from db. It should be cache.
        List<Rule> allRulesByNamespace = knowledgeBaseService.getAllRuleByNamespace(ruleNamespace);
        return inferenceEngine.run(allRulesByNamespace, inputData);
    }
}
