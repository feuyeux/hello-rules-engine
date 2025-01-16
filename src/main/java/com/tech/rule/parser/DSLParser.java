package com.tech.rule.parser;

import com.tech.rule.resolver.DSLKeywordResolver;
import com.tech.rule.resolver.DSLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DSLParser {

    @Autowired
    private DSLKeywordResolver keywordResolver;
    @Autowired
    private DSLPatternUtil dslPatternUtil;

    public String resolveDomainSpecificKeywords(String expression) {
        Map<String, Object> dslKeywordToResolverValueMap = executeDSLResolver(expression);
        return replaceKeywordsWithValue(expression, dslKeywordToResolverValueMap);
    }

    private Map<String, Object> executeDSLResolver(String expression) {
        List<String> listOfDslKeyword = dslPatternUtil.getListOfDslKeywords(expression);
        return listOfDslKeyword.stream().collect(Collectors.toMap(
                dslKeyword -> dslKeyword,
                dslKeyword -> {
                    String extractedDslKeyword = dslPatternUtil.extractKeyword(dslKeyword);
                    String keyResolver = dslPatternUtil.getKeywordResolver(extractedDslKeyword);
                    String keywordValue = dslPatternUtil.getKeywordValue(extractedDslKeyword);
                    Optional<DSLResolver> resolverOptional = keywordResolver.getResolver(keyResolver);
                    if (resolverOptional.isPresent()) {
                        DSLResolver resolver = resolverOptional.get();
                        return resolver.resolveValue(keywordValue);
                    } else {
                        log.error("No resolver found for keyword: {}", keyResolver);
                        return new HashMap<>();
                    }
                }
        ));
    }

    private String replaceKeywordsWithValue(String expression, Map<String, Object> dslKeywordToResolverValueMap) {
        List<String> keyList = new ArrayList<>(dslKeywordToResolverValueMap.keySet());
        for (String key : keyList) {
            String dslResolveValue = dslKeywordToResolverValueMap.get(key).toString();
            expression = expression.replace(key, dslResolveValue);
        }
        return expression;
    }
}
