package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Limit interactions to a specific type of user for a specified duration.
 *
 * @param limit Limit
 * @param expiry Expiry
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/interaction-limit",
    "operations/interactions/set-restrictions-for-org/requestBody",
    "operations/interactions/set-restrictions-for-repo/requestBody",
    "operations/interactions/set-restrictions-for-authenticated-user/requestBody"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubInteractionLimit(
        GitHubInteractionGroup limit, @Nullable GitHubInteractionExpiry expiry) {

    public GitHubInteractionLimit {
        requireNonNull(limit, "limit");
    }
}
