package fr.kissy.rest.config;

import fr.kissy.rest.resource.ApiListingResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Guillaume Le Biller
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractResourceConfig {
    @Bean
    public ApiListingResource apiListingResource() {
        return new ApiListingResource();
    }
}
