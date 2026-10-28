package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/** Ruleset version. */
@CatalogSchema("components/schemas/ruleset-version")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRulesetVersion(long versionId, GitHubRulesetVersionActor actor, Instant updatedAt) {

    public GitHubRulesetVersion {
        requireNonNull(actor, "actor");
        requireNonNull(updatedAt, "updatedAt");
    }
}
