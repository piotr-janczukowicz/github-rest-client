package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Parameters of the maximum file path length rule: the longest file path that can be pushed. */
@CatalogSchema("components/schemas/repository-rule-max-file-path-length/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMaxFilePathLengthParameters(int maxFilePathLength) {
    public GitHubRepositoryRuleMaxFilePathLengthParameters {
        requireBetween(maxFilePathLength, 1, 32_767, "maxFilePathLength");
    }
}
