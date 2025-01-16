package com.tech.rule.parser;

import lombok.extern.slf4j.Slf4j;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.tech.rule.parser.RuleParser.INPUT_KEYWORD;
import static com.tech.rule.parser.RuleParser.OUTPUT_KEYWORD;

@Slf4j
@Service
public class MVELParser {

    public boolean parseMvelExpression(String expression, Map<String, Object> inputObjects) {
        try {
            /*
             * git clone https://github.com/mvel/mvel.git -b mvel2-2.5.2.Final
             */
            return MVEL.evalToBoolean(expression, inputObjects);
        } catch (NullPointerException e) {
            log.error("Can not parse Mvel Expression : \nExpression:{}\nInput: {}\nOutput: {}",
                    expression,
                    inputObjects.get(INPUT_KEYWORD),
                    inputObjects.get(OUTPUT_KEYWORD)
            );
        } catch (Exception e) {
            log.error("Can not parse Mvel Expression : {}, Error: {}", expression, e.getMessage());
        }
        return false;
    }
}
