package com.tech.rule.parser;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DSLPatternUtil {
    //$(rulenamespace.keyword)
    private static final Pattern DSL_PATTERN = Pattern.compile("\\$\\((\\w+)(\\.\\w+)\\)");
    private static final String DOT = ".";

    public List<String> getListOfDslKeywords(String expression) {
        Matcher matcher = DSL_PATTERN.matcher(expression);
        List<String> listOfDslKeyword = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            listOfDslKeyword.add(group);
        }
        return listOfDslKeyword;
    }

    public String extractKeyword(String keyword) {
        return keyword.substring(keyword.indexOf('(') + 1, keyword.indexOf(')'));
    }

    public String getKeywordResolver(String dslKeyword) {
        return Splitter.on(DOT).omitEmptyStrings().splitToList(dslKeyword).get(0);
    }

    public String getKeywordValue(String dslKeyword) {
        return Splitter.on(DOT).omitEmptyStrings().splitToList(dslKeyword).get(1);
    }
}
