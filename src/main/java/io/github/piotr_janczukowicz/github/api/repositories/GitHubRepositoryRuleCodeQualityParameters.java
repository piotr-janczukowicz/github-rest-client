package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Parameters of the code quality rule: the lowest severity level at which results block merges.
 *
 * @param severity The lowest severity level at which code quality reviews need to be resolved before commits can be
 *     merged
 */
@CatalogSchema("components/schemas/repository-rule-code-quality/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryRuleCodeQualityParameters(GitHubRepositoryRuleCodeQualitySeverity severity) {

    public GitHubRepositoryRuleCodeQualityParameters {
        requireNonNull(severity, "severity");
    }
}
