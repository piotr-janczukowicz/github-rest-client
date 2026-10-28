package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Parameters of the maximum file size rule: the largest file size that can be pushed. */
@CatalogSchema("components/schemas/repository-rule-max-file-size/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMaxFileSizeParameters(int maxFileSize) {
    public GitHubRepositoryRuleMaxFileSizeParameters {
        requireBetween(maxFileSize, 1, 100, "maxFileSize");
    }
}
