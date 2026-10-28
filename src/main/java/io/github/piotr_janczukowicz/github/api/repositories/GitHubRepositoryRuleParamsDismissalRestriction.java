package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** DismissalRestriction. */
@CatalogSchema("components/schemas/repository-rule-params-dismissal-restriction")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsDismissalRestriction(
        @Nullable List<GitHubRepositoryRuleParamsActor> allowedActors, boolean enabled) {

    public GitHubRepositoryRuleParamsDismissalRestriction {
        if (allowedActors != null) {
            allowedActors = List.copyOf(allowedActors);
        }
    }
}
