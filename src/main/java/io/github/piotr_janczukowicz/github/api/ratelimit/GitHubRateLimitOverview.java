package io.github.piotr_janczukowicz.github.api.ratelimit;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Rate limit status for the applicable requester and resource category.
 *
 * @param resources Resources
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/rate-limit-overview")
public record GitHubRateLimitOverview(GitHubRateLimitResources resources) {

    public GitHubRateLimitOverview {
        requireNonNull(resources, "resources");
    }
}
