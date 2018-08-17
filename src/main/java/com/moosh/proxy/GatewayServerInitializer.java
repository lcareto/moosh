package com.moosh.proxy;

import org.springframework.context.annotation.Import;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Import({ClientRegistryController.class})
public class GatewayServerInitializer {

}
