package com.tech.rule.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RuleParser<INPUT_DATA, OUTPUT_RESULT> {

    static final String INPUT_KEYWORD = "input";
    static final String OUTPUT_KEYWORD = "output";
    @Autowired
    protected DSLParser dslParser;
    @Autowired
    protected MVELParser mvelParser;

    /**
     * Parsing in given priority/steps.
     * <p>
     * Step 1. Resolve domain specific keywords first: $(rulenamespace.keyword)
     * Step 2. Resolve MVEL expression.
     *
     * @param expression : Rule expression
     * @param inputData  : Input data
     */
    public boolean parseCondition(String expression, INPUT_DATA inputData) {
        String resolvedDslExpression = dslParser.resolveDomainSpecificKeywords(expression);
        Map<String, Object> input = new HashMap<>();
        input.put(INPUT_KEYWORD, inputData);
        return mvelParser.parseMvelExpression(resolvedDslExpression, input);
    }

    /**
     * Parsing in given priority/steps.
     * <p>
     * Step 1. Resolve domain specific keywords: $(rulenamespace.keyword)
     * Step 2. Resolve MVEL expression.
     *
     * @param expression   : Rule expression
     * @param inputData    : Input data
     * @param outputResult : Output result
     * @return : Output result
     */
    public OUTPUT_RESULT parseAction(String expression, INPUT_DATA inputData, OUTPUT_RESULT outputResult) {
        try {
            String resolvedDslExpression = dslParser.resolveDomainSpecificKeywords(expression);
            Map<String, Object> input = new HashMap<>();
            input.put(INPUT_KEYWORD, inputData);
            input.put(OUTPUT_KEYWORD, outputResult);
            mvelParser.parseMvelExpression(resolvedDslExpression, input);
        } catch (Exception e) {
            log.error("Error while parsing action: {}", e.getMessage());
        }
        return outputResult;
    }
}
