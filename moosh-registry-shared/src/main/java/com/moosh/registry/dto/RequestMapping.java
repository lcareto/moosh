package com.moosh.registry.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.HashSet;
import java.util.Set;

@Data
public class RequestMapping {

    protected RequestMethod method;
    protected Set<String> patterns = new HashSet<>();

    public static RequestMapping from(RequestMappingInfo source) {
        RequestMapping target = new RequestMapping();
        target.setMethod(source.getMethodsCondition().getMethods().stream().findFirst().orElse(null));
        target.setPatterns(source.getPatternsCondition().getPatterns());
        return target;
    }
}
