package com.moosh.utils.annotation;

import com.moosh.client.ClientRegistryInitializer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ClientRegistryInitializer.class})
public @interface EnableClientRegistry {
}
