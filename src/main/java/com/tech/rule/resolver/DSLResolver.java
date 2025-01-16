package com.tech.rule.resolver;

public interface DSLResolver {
    String getResolverKeyword();
    Object resolveValue(String keyword);
}
