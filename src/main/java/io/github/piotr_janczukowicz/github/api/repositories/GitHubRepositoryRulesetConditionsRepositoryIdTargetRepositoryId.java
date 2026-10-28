package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Repository identifiers selected by a ruleset repository ID condition. */
@CatalogSchema("components/schemas/repository-ruleset-conditions-repository-id-target/properties/repository_id")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(
        @Nullable List<Long> repositoryIds) {

    public GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId {
        if (repositoryIds != null) {
            repositoryIds = List.copyOf(repositoryIds);
        }
    }
}
