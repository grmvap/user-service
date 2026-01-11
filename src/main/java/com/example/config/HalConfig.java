package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.hateoas.mediatype.hal.HalLinkRelation;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalConfig implements HypermediaWebMvcConfigurer {

    @Bean
    public HalConfiguration halConfiguration() {
        return new HalConfiguration()
                .withMediaType("application/hal+json")
                .withLinkRelation(HalLinkRelation.SELF, config -> config.withArbitraryArguments(false))
                .withLinkRelation(HalLinkRelation.ITEM, config -> config.withArbitraryArguments(false));
    }
}
