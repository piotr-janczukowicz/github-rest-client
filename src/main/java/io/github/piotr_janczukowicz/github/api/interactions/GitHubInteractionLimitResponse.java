package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Interaction limit settings.
 *
 * @param limit Limit
 * @param origin Origin
 * @param expiresAt Expires at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/interaction-limit-response")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubInteractionLimitResponse(GitHubInteractionGroup limit, String origin, Instant expiresAt) {

    public GitHubInteractionLimitResponse {
        requireNonNull(limit, "limit");
        requireNonNull(origin, "origin");
        requireNonNull(expiresAt, "expiresAt");
    }
}
