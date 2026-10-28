package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Set secrets for GitHub Actions.
 *
 * @param name The name of the secret.
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-secret")
public record GitHubActionsSecret(String name, Instant createdAt, Instant updatedAt) {

    public GitHubActionsSecret {
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
