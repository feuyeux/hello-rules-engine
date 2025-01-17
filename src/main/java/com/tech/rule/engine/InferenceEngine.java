package com.tech.rule.engine;

import com.tech.api.RuleNamespace;
import com.tech.repository.models.Rule;
import com.tech.rule.parser.RuleParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class InferenceEngine<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    private RuleParser<INPUT_DATA, OUTPUT_RESULT> ruleParser;

    /**
     * Run inference engine on set of rules for given data.
     *
     * @param listOfRules : List of rules
     * @param inputData   : Input data
     * @return : Output result
     */
    public OUTPUT_RESULT run(List<Rule> listOfRules, INPUT_DATA inputData) {
        if (null == listOfRules || listOfRules.isEmpty()) {
            return null;
        }

        //STEP 1 (MATCH) : Match the facts and data against the set of rules.
        List<Rule> conflictSet = match(listOfRules, inputData);

        //STEP 2 (RESOLVE) : Resolve the conflict and give the selected one rule.
        Rule resolvedRule = resolve(conflictSet);
        if (null == resolvedRule) {
            return null;
        }

        //STEP 3 (EXECUTE) : Run the action of the selected rule on given data and return the output.
        return executeRule(resolvedRule, inputData);
    }

    /**
     * We can use here any pattern matching algo:
     * 1. Rete
     * 2. Linear
     * 3. Treat
     * 4. Leaps
     * <p>
     * Here we are using Linear matching algorithm for pattern matching.
     *
     * @param listOfRules : List of rules
     * @param inputData   : Input data
     * @return : List of matched rules
     */
    protected List<Rule> match(List<Rule> listOfRules, INPUT_DATA inputData) {
        return listOfRules.stream()
                .filter(
                        rule -> {
                            String condition = rule.getCondition();
                            return ruleParser.parseCondition(condition, inputData);
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     * We can use here any resolving techniques:
     * 1. Lex
     * 2. Recency
     * 3. MEA
     * 4. Refactor
     * 5. Priority wise
     * <p>
     * Here we are using find first rule logic.
     *
     * @param conflictSet : List of matched rules
     * @return : Selected rule
     */
    protected Rule resolve(List<Rule> conflictSet) {
        Optional<Rule> rule = conflictSet.stream().findFirst();
        return rule.orElse(null);
    }

    /**
     * Execute selected rule on input data.
     *
     * @param rule      : Selected rule
     * @param inputData : Input data
     * @return : Output result
     */
    protected OUTPUT_RESULT executeRule(Rule rule, INPUT_DATA inputData) {
        OUTPUT_RESULT outputResult = initializeOutputResult();
        String action = rule.getAction();
        log.info("action: {}", action);
        return ruleParser.parseAction(action, inputData, outputResult);
    }

    protected abstract OUTPUT_RESULT initializeOutputResult();

    protected abstract RuleNamespace getRuleNamespace();
}
