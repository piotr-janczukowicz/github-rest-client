package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repository name patterns selected by a ruleset repository name condition, with the patterns to include and exclude.
 */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-name-target/properties/repository_name")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(
        @Nullable List<String> include,
        @Nullable List<String> exclude,
        @JsonProperty("protected") @Nullable Boolean protectedRepos) {

    public GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName {
        if (include != null) {
            include = List.copyOf(include);
        }
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
