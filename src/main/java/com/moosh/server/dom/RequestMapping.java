package com.moosh.server.dom;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;

@Data
@Embeddable
public class RequestMapping {

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    @ElementCollection(targetClass = String.class)
    private Set<String> patterns = new HashSet<>();

    public static RequestMapping from(RequestMappingInfo source) {
        RequestMapping target = new RequestMapping();
        target.setMethod(source.getMethodsCondition().getMethods().stream().findFirst().orElse(null));
        target.setPatterns(source.getPatternsCondition().getPatterns());
        return target;
    }
}
