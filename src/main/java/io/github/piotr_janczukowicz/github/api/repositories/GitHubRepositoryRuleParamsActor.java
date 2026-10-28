package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Actor. */
@CatalogSchema("components/schemas/repository-rule-params-actor")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsActor(long id, GitHubRepositoryRuleParamsActorType type) {
    public GitHubRepositoryRuleParamsActor {
        requireNonNull(type, "type");
    }
}
