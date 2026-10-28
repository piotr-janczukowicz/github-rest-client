package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Custom property filters selected by a ruleset repository property condition, with the filters to include and exclude.
 */
@CatalogSchema(
        "components/schemas/repository-ruleset-conditions-repository-property-target/properties/repository_property")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(
        @Nullable List<GitHubRepositoryRulesetConditionsRepositoryPropertySpec> include,
        @Nullable List<GitHubRepositoryRulesetConditionsRepositoryPropertySpec> exclude) {

    public GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty {
        if (include != null) {
            include = List.copyOf(include);
        }
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
