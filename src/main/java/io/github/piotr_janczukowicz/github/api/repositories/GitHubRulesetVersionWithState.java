package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Ruleset version including the ruleset snapshot at that version.
 *
 * @param versionId Version id
 * @param actor Actor who updated the ruleset
 * @param updatedAt Updated at
 * @param state Ruleset state (OpenAPI types {@code state} as empty object; wire shape is a repository ruleset)
 */
@CatalogSchema("components/schemas/ruleset-version-with-state")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRulesetVersionWithState(
        long versionId, GitHubRulesetVersionActor actor, Instant updatedAt, GitHubRepositoryRuleset state) {

    public GitHubRulesetVersionWithState {
        requireNonNull(actor, "actor");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(state, "state");
    }
}
